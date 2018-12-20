/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers.negocio;

import com.ub.easymoney.daos.admin.DaoUsuario;
import com.ub.easymoney.daos.negocio.DaoAbono;
import com.ub.easymoney.daos.negocio.DaoPrestamo;
import com.ub.easymoney.entities.negocio.Abono;
import com.ub.easymoney.entities.negocio.Prestamo;
import com.ub.easymoney.entities.negocio.Usuario;
import com.ub.easymoney.managers.commons.ManagerSQL;
import com.ub.easymoney.models.ModelCargarPrestamos;
import com.ub.easymoney.models.ModelPrestamoTotales;
import com.ub.easymoney.models.ModelPrestamoTotalesGenerales;
import com.ub.easymoney.models.filtros.FiltroPrestamo;
import com.ub.easymoney.utils.UtilsConfig;
import com.ub.easymoney.utils.UtilsDB;
import com.ub.easymoney.utils.UtilsDate;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import org.jinq.tuples.Pair;
import sun.util.resources.LocaleData;

/**
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ManagerPrestamo extends ManagerSQL<Prestamo, Integer> {

    public ManagerPrestamo() {
        super(new DaoPrestamo());
    }

    /**
     * Persiste el prestamo asegurando la generacion de los abonos del prestamo
     *
     * @param entity entidad prestamo a persistir
     * @return retorna el prestamo persistido con sus abonos asegurados
     * @throws Exception si existe un error de I/O
     */
    @Override
    public Prestamo persist(Prestamo entity) throws Exception {
        int cantImpuesto = (int) (entity.getCantidad() * ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f));
        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        entity.setFecha(new Date());

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo()); //apartir del dia siguiente        

        //UtilsDate.setTimeToCero(cal);        
        entity.setFechaLimite(cal.getTime());

        this.dao.persist(entity);

        return entity;//To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Obtiene los totales generales de un prestamo en particular
     *
     * @param prestamoId identificador del prestamo
     * @return modelo con los totales generales de un prestamo
     * @throws Exception si existe un error de I/O
     */
    public ModelPrestamoTotales totalesPrestamo(int prestamoId) throws Exception {
        ModelPrestamoTotales mPrestamoTotales = new ModelPrestamoTotales();

        Prestamo prestamo = this.findOne(prestamoId);
        List<Abono> abonos = prestamo.getAbonoList();
        mPrestamoTotales.setTotalAbonado(abonos.stream().filter(t -> t.getAbonado()).mapToInt(t -> t.getCantidad()).sum());
        mPrestamoTotales.setTotalMultado(abonos.stream().filter(t -> t.getAbonado()).mapToInt(t -> t.getMulta()).sum());
        mPrestamoTotales.setTotalRecuperado(mPrestamoTotales.getTotalAbonado() + mPrestamoTotales.getTotalMultado());
        mPrestamoTotales.setPorcentajePagado((int) ((float) mPrestamoTotales.getTotalAbonado() / (float) prestamo.getCantidadPagar() * 100f));
        mPrestamoTotales.setPorPagar(prestamo.getCantidadPagar() - mPrestamoTotales.getTotalAbonado());
        return mPrestamoTotales;
    }

    /**
     * Obtiene los totales generales de todos los prestamos
     *
     * @return modelo con los totales generales
     * @throws Exception si existe un error de I/O
     */
    public ModelPrestamoTotalesGenerales totalesPrestamosGenerales() throws Exception {

        List<Pair<Integer, Integer>> abonosMultas = new DaoAbono().stream()
                .filter(a -> a.getAbonado())
                .map(e -> new Pair<>(e.getCantidad(), e.getMulta()))
                .collect(toList());

        final int totalAbonado = abonosMultas.stream().mapToInt(e -> e.getOne()).sum();
        final int totalMultado = abonosMultas.stream().mapToInt(e -> e.getTwo()).sum();
        final int totalPrestamo = this.stream().mapToInt(p -> p.getCantidad()).sum();
        final int totalAPagar = this.stream().mapToInt(p -> p.getCantidadPagar()).sum();
        final int totalRecuperado = totalAbonado + totalMultado;
        final int capital = totalRecuperado - totalPrestamo;
        final float porcentajeAbonado = (((float) totalAbonado / (float) totalAPagar) * 100f);

        return new ModelPrestamoTotalesGenerales(totalPrestamo, totalAbonado, totalMultado, totalRecuperado, capital, porcentajeAbonado);
    }

    /**
     * consulta todos los prestamos que cumplen con las propiedades del objeto filtro
     *
     * @param filtro objecto con las propiedaes a filtrar
     * @return lista de prestamos filtrados
     */
    public List<ModelCargarPrestamos> cargarPrestamos(FiltroPrestamo filtro) {
        List<ModelCargarPrestamos> models = new ArrayList<>();
        List<Prestamo> prestamosFiltrados = new DaoPrestamo().findAll(filtro);
        Date now = UtilsDate.dateWithoutTime();
        //filtrar los 100% acreditados
        if (!filtro.isAcreditados()) {
            prestamosFiltrados = prestamosFiltrados.stream()
                    .filter(t -> {
                        float totalAbonado = t.getAbonoList().stream().filter(a -> a.getAbonado()).mapToInt(a -> a.getCantidad()).sum();
                        float porcentajeAbonado = (totalAbonado / (float) t.getCantidadPagar() * 100f);
                        return porcentajeAbonado < 100;
                    }).collect(toList());
            //no hay prestamos acreditados
            prestamosFiltrados.forEach(p -> {
                ModelCargarPrestamos model = new ModelCargarPrestamos(p.getId(), p.getCliente().getNombre(), p.getCobrador().getNombre(), p.getCantidad(), p.getCantidadPagar(), p.getFecha(), p.getFechaLimite());                
                if (now.after(p.getFechaLimite())) {
                    model.setEstado(ModelCargarPrestamos.EstadoPrestamo.VENCIDO);
                }
                models.add(model);
            });
        } else {
            prestamosFiltrados.forEach(p -> {
                ModelCargarPrestamos model = new ModelCargarPrestamos(p.getId(), p.getCliente().getNombre(), p.getCobrador().getNombre(), p.getCantidad(), p.getCantidadPagar(), p.getFecha(), p.getFechaLimite());
                float totalAbonado = p.getAbonoList().stream().filter(a -> a.getAbonado()).mapToInt(a -> a.getCantidad()).sum();
                float porcentajeAbonado = (totalAbonado / (float) p.getCantidadPagar() * 100f);
                if (porcentajeAbonado == 100) {
                    model.setEstado(ModelCargarPrestamos.EstadoPrestamo.ACREDITADO);
                } else {
                    if (now.after(p.getFechaLimite())) {
                        if (porcentajeAbonado < 100) {
                            model.setEstado(ModelCargarPrestamos.EstadoPrestamo.VENCIDO);
                        }
                    }
                }
                models.add(model);
            });
        }

        return models;
    }

    public List<Prestamo> prestamosPorCobrar(final int cobradorId) {
        return new DaoPrestamo().prestamosPorCobrar(cobradorId);
    }

    public List<Prestamo> prestamosDelCobrador(final int cobradorId) {
        return new DaoPrestamo().prestamosDelCobrador(cobradorId);
    }

    /**
     * Genera el resultado de los totales generales de los prestamos segun un filtrado
     *
     * @param filtro objeto con las propiedades a filtrar en los prestamos
     * @return modelo del resultado de los totales generales del prestamo
     */
    public ModelPrestamoTotalesGenerales totalesPrestamosGenerales(FiltroPrestamo filtro) throws Exception {
        List<Prestamo> prestamos = new DaoPrestamo().findAll(filtro);
        if (!filtro.isAcreditados()) {
            prestamos = prestamos.stream()
                    .filter(t -> {
                        float totalAbonado = t.getAbonoList().stream().filter(a -> a.getAbonado()).mapToInt(a -> a.getCantidad()).sum();
                        float porcentajeAbonado = (totalAbonado / (float) t.getCantidadPagar() * 100f);
                        return porcentajeAbonado < 100;
                    }).collect(toList());
        }

        List<Abono> abonos = new ArrayList<>();
        prestamos.forEach(p -> abonos.addAll(p.getAbonoList()));

        List<Pair<Integer, Integer>> abonosMultas = abonos.stream()
                .filter(a -> a.getAbonado())
                .map(e -> new Pair<>(e.getCantidad(), e.getMulta()))
                .collect(toList());

        final int totalAbonado = abonosMultas.stream().mapToInt(e -> e.getOne()).sum();
        final int totalMultado = abonosMultas.stream().mapToInt(e -> e.getTwo()).sum();
        final int totalPrestamo = prestamos.stream().mapToInt(p -> p.getCantidad()).sum();
        final int totalAPagar = prestamos.stream().mapToInt(p -> p.getCantidadPagar()).sum();
        final int totalRecuperado = totalAbonado + totalMultado;
        final int capital = totalRecuperado - totalPrestamo;
        final float porcentajeAbonado = (((float) totalAbonado / (float) totalAPagar) * 100f);

        return new ModelPrestamoTotalesGenerales(totalPrestamo, totalAbonado, totalMultado, totalRecuperado, capital, porcentajeAbonado);
    }

    /**
     * Renueva un prestamo, generando un nuevo prestamos con la cantidad anteriormente prestada, salda el prestamoa anterior y genera un de lo prestado menos la cantidad saldada del prestamo anterior
     *
     * @param prestamoId identificador del prestamos a renovar
     * @param cantNuevoPrestamo cantidad a prestar en el nuevo prestamo
     * @return cantidad a entregar de dinero fisico al cliente
     * @throws Exception
     */
    public int renovarPrestamo(final int prestamoId, final int cantNuevoPrestamo) throws InvalidParameterException, Exception {
        Prestamo prestamoRenovar = this.findOne(prestamoId);

        Prestamo nuevoPrestamo = new Prestamo();

        nuevoPrestamo.setCliente(prestamoRenovar.getCliente());
        nuevoPrestamo.setCobrador(prestamoRenovar.getCobrador());
        nuevoPrestamo.setCantidad(cantNuevoPrestamo);

        int cantImpuesto = nuevoPrestamo.getCantidad();
        cantImpuesto *= ((float) UtilsConfig.getPorcentajeComisionPrestamo() / 100f);

        nuevoPrestamo.setCantidadPagar(nuevoPrestamo.getCantidad() + cantImpuesto);
        nuevoPrestamo.setFecha(new Date());

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, UtilsConfig.getDiasPlazoPrestamo());

        nuevoPrestamo.setFechaLimite(cal.getTime());

        DaoPrestamo daoPrestamo = new DaoPrestamo();
        return daoPrestamo.renovarPrestamo(prestamoRenovar, nuevoPrestamo);
    }

    public void cambiarCobrador(int prestamoId, int cobradorId) throws Exception {
        Prestamo p = this.dao.findOne(prestamoId);
        Usuario u = new DaoUsuario().findOne(cobradorId);
        p.setCobrador(u);
        EntityManager em = this.dao.getEMInstance();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

}
