/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ub.easymoney.managers;

import com.ub.easymoney.daos.DaoConfig;
import com.ub.easymoney.daos.DaoUsuario;
import com.ub.easymoney.daos.DaoAbono;
import com.ub.easymoney.daos.DaoCliente;
import com.ub.easymoney.daos.DaoPrestamo;
import com.ub.easymoney.entities.Abono;
import com.ub.easymoney.entities.Cobro;
import com.ub.easymoney.entities.Config;
import com.ub.easymoney.entities.DistribucionCobro;
import com.ub.easymoney.entities.Prestamo;
import com.ub.easymoney.entities.Usuario;
import com.ub.easymoney.models.ModelAbonar;
import com.ub.easymoney.utils.commons.ManagerSQL;
import com.ub.easymoney.models.ModelCargarPrestamos;
import com.ub.easymoney.models.ModelDistribucionAbono;
import com.ub.easymoney.models.ModelPrestamoAbonado;
import com.ub.easymoney.models.ModelPrestamoTotales;
import com.ub.easymoney.models.ModelPrestamoTotalesGenerales;
import com.ub.easymoney.models.ModelSaldoPrestamos;
import com.ub.easymoney.models.filtros.FiltroPrestamo;
import com.ub.easymoney.utils.UtilsDB;
import com.ub.easymoney.utils.UtilsDate;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import static java.util.Collections.sort;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import org.jinq.tuples.Pair;

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
        validarMultiplesPrestamos(entity.getCliente().getId());
        Config conf = new DaoConfig().findFirst();
        int cantImpuesto = (int) (entity.getCantidad() * ((float) conf.getPorcentajeInteresPrestamo() / 100f));
        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        entity.setFecha(UtilsDate.dateWithoutTime());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(entity.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, conf.getDiasPrestamo()); //apartir del dia siguiente        
        entity.setFechaLimite(cal.getTime());

        this.dao.persist(entity);
        return entity;//To change body of generated methods, choose Tools | Templates.
    }

    public void persistPrueba(Prestamo entity) throws Exception {
        Config conf = new DaoConfig().findFirst();
        int cantImpuesto = (int) (entity.getCantidad() * ((float) conf.getPorcentajeInteresPrestamo() / 100f));
        entity.setCantidadPagar(entity.getCantidad() + cantImpuesto);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(entity.getFecha());
        cal.add(Calendar.DAY_OF_YEAR, conf.getDiasPrestamo()); //apartir del dia siguiente        
        entity.setFechaLimite(cal.getTime());

        this.dao.persist(entity);
    }

    /**
     * Obtiene los totales generales de un prestamo en particular
     *
     * @param prestamoId identificador del prestamo
     * @return modelo con los totales generales de un prestamo
     * @throws Exception si existe un error de I/O
     */
    public ModelPrestamoTotales totalesPrestamo(int prestamoId) throws Exception {
        Prestamo prestamo = this.findOne(prestamoId);
        return this.totalesPrestamo(prestamo);
    }

    public ModelPrestamoTotales totalesPrestamo(Prestamo prestamo) throws Exception {
        ModelPrestamoTotales mPrestamoTotales = new ModelPrestamoTotales();
        List<Abono> abonos = prestamo.getAbonoList();
        mPrestamoTotales.setTotalAbonado(abonos.stream().filter(t -> t.getAbonado()).mapToInt(t -> t.getCantidad()).sum());
        mPrestamoTotales.setTotalMultado(abonos.stream().filter(t -> t.getAbonado()).mapToInt(t -> t.getMulta()).sum());
        mPrestamoTotales.setTotalRecuperado(mPrestamoTotales.getTotalAbonado() + mPrestamoTotales.getTotalMultado());
        mPrestamoTotales.setPorcentajePagado((int) ((float) mPrestamoTotales.getTotalAbonado() / (float) prestamo.getCantidadPagar() * 100f));
        if (mPrestamoTotales.getPorcentajePagado() != 100) {
            this.calcularTotalesPagar(prestamo, mPrestamoTotales);
        }
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
        List<Prestamo> prestamos = new DaoPrestamo().findAll(filtro);
        Date now = UtilsDate.dateWithoutTime();
        for (Prestamo p : prestamos) {
            boolean saldado = p.saldado();
            if (!filtro.isAcreditados() && saldado) {
                continue;
            }
            ModelCargarPrestamos model = new ModelCargarPrestamos(
                    p.getId(),
                    p.getCliente().getNombre(),
                    p.getCobrador().getNombre(),
                    p.getCantidad(),
                    p.getCantidadPagar(),
                    p.getFecha(),
                    p.getFechaLimite()
            );

            if (saldado) {
                model.setEstado(ModelCargarPrestamos.EstadoPrestamo.ACREDITADO);
            } else {
                if (now.after(p.getFechaLimite())) {
                    model.setEstado(ModelCargarPrestamos.EstadoPrestamo.VENCIDO);
                }
            }
            models.add(model);
        }
        return models;
    }

    public List<Prestamo> prestamosPorCobrar(final int cobradorId) {
        return new DaoPrestamo().prestamosPorCobrar(cobradorId);
    }

    public List<Prestamo> prestamosDelCobrador(final int cobradorId) {
        return new DaoPrestamo().prestamosDelCobrador(cobradorId);
    }

    public List<Prestamo> prestamosDelCliente(final int clienteId) {
        return new DaoPrestamo().prestamosDelCliente(clienteId);
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
        ModelPrestamoTotales model = this.totalesPrestamo(prestamoId);
        if (model.getPorPagarLiquidar() > cantNuevoPrestamo) {
            throw new InvalidParameterException("No puede renovar el prestamo por una cantidad menor a la deuda");
        }
        Prestamo prestamoRenovar = this.findOne(prestamoId);

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setCliente(prestamoRenovar.getCliente());
        nuevoPrestamo.setCobrador(prestamoRenovar.getCobrador());
        nuevoPrestamo.setCantidad(cantNuevoPrestamo);

        Config conf = new DaoConfig().findFirst();

        int cantImpuesto = nuevoPrestamo.getCantidad();
        cantImpuesto *= ((float) conf.getPorcentajeInteresPrestamo() / 100f);

        nuevoPrestamo.setCantidadPagar(nuevoPrestamo.getCantidad() + cantImpuesto);
        nuevoPrestamo.setFecha(UtilsDate.dateWithoutTime());

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, conf.getDiasPrestamo());
        nuevoPrestamo.setFechaLimite(cal.getTime());

        DaoPrestamo daoPrestamo = new DaoPrestamo();
        return daoPrestamo.renovarPrestamo(prestamoRenovar, nuevoPrestamo, model.getPorPagarLiquidar());
    }

    /**
     * Reasigna un cobrador al prestamo
     *
     * @param prestamoId prestamoa cambiar
     * @param cobradorId cobrador nuevo a asignar
     * @throws Exception
     */
    public void cambiarCobrador(int prestamoId, int cobradorId) throws Exception {
        Prestamo p = this.dao.findOne(prestamoId);
        Usuario u = new DaoUsuario().findOne(cobradorId);
        p.setCobrador(u);
        EntityManager em = this.dao.getEMInstance();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    /**
     * accion de abonar una cantidad al prestamo
     *
     * @param model
     * @return
     * @throws Exception
     */
    public ModelPrestamoAbonado abonar(ModelAbonar model) throws InvalidParameterException, Exception {
        ModelPrestamoAbonado mpa = new ModelPrestamoAbonado();
        ModelPrestamoTotales mpt = this.totalesPrestamo(model.getPrestamoId());
        if (mpt.getPorPagarLiquidar() < model.getCantidad()) {
            throw new InvalidParameterException("No puede abonar más de lo que debe el préstamo");
        }
        Prestamo p = this.findOne(model.getPrestamoId());
        int cantidadAbono = model.getCantidad();
        if (model.getDescripcion() == null) {
            model.setDescripcion("");
        }
        List<Abono> abonos = p.getAbonoList();
        sort(abonos, (a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha()));

        Config conf = new DaoConfig().findFirst();
        int cantidadMultaDiaria = conf.getCantidadMultaDiaria();
        int cantidadMultaPostPlazo = conf.getCantidadMultaMes();

        boolean ignorarMulta = this.ignorarMulta(p);

        ModelDistribucionAbono distribucion = new ModelDistribucionAbono();
        cantidadAbono = distribuirMultaPostPlazo(p.getId(), abonos,
                p.getFechaLimite(), cantidadAbono, cantidadMultaPostPlazo, distribucion);

        if (cantidadAbono < mpt.getPorPagarIrAlCorriente()) {
            ignorarMulta = false;
        }

        distribuirAbono(abonos, p.getFechaLimite(), p.getCobroDiario(),
                cantidadAbono, cantidadMultaDiaria, ignorarMulta,
                model.getDescripcion(), distribucion, p.getMontoRedondeo());

        Cobro cobro = new Cobro(model.getCantidad(), new Date(), p, p.getCobrador());
        ModelPrestamoTotales nuevosTotales = this.totalesPrestamo(p);

        EntityManager em = UtilsDB.getEMFactoryCG().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cobro);
            cobro = em.merge(cobro);
            em.flush();

            DistribucionCobro dc = this.generarDistribucionCobro(cobro, nuevosTotales, distribucion);
            em.persist(dc);

            p.getCobroList().add(cobro);
            em.merge(p);
            em.getTransaction().commit();
            mpa.setPrestamo(p);
            mpa.setDistribucionCobro(dc);
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }

        return mpa;
    }

    /**
     * Calcula los totales a pagar del prestamo
     *
     * @param prestamo prestamo a calcular
     * @param model modelo donde guardar los totales
     * @throws Exception
     */
    private void calcularTotalesPagar(Prestamo prestamo, ModelPrestamoTotales model) throws Exception {
        int abonoAPagar = 0;
        int multaAPagar = 0;
        int multaAPagarMes = 0;
        int abonosFuturos = 0;

        final Date hoy = UtilsDate.dateWithoutTime();

        final ManagerConfig managerConfig = new ManagerConfig();
        int multaDiaria = managerConfig.findFirst().getCantidadMultaDiaria();
        int multaMes = managerConfig.findFirst().getCantidadMultaMes();
        boolean ignorarMulta = this.ignorarMulta(prestamo);

        List<Abono> abonos = prestamo.getAbonoList();
        sort(abonos, (a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha()));
        for (Abono abono : abonos) {
            if (abono.getAbonoPK().getFecha().after(prestamo.getFechaLimite())) {
                break;
            }
            if (abono.getAbonoPK().getFecha().after(hoy)) {
                int faltaPorAbonar = prestamo.getCobroDiario();
                if (abono.getAbonado()) {
                    faltaPorAbonar -= abono.getCantidad();
                }
                abonosFuturos += faltaPorAbonar;
            } else if (abono.getAbonoPK().getFecha().before(hoy)) { //anteriores al dia de hoy
                if (!abono.getAbonado()) {
                    abonoAPagar += prestamo.getCobroDiario();
                    if (!ignorarMulta) {
                        multaAPagar += multaDiaria;
                    }
                } else {
                    //si esta abonado y no abono mas o igual que el cobro diario es multa
                    if (abono.getCantidad() < prestamo.getCobroDiario()) {
                        abonoAPagar += (prestamo.getCobroDiario() - abono.getCantidad());
                        if (!ignorarMulta) {
                            multaAPagar += multaDiaria - (abono.getMulta());
                        }
                    }
                }
            } else { // es el dia actual y hay que sumar lo que corresponde del dia
                if (!abono.getAbonado()) {
                    abonoAPagar += prestamo.getCobroDiario();
                } else {
                    abonoAPagar += prestamo.getCobroDiario() - abono.getCantidad();
                }
            }
            //check for amount per round
            if (abono.getAbonoPK().getFecha().equals(prestamo.getFechaLimite())) {
                if (abono.getAbonado() && abono.getCantidad() > prestamo.getCobroDiario()) {
                    abonosFuturos += (prestamo.getCobroDiario() + prestamo.getMontoRedondeo()) - abono.getCantidad();
                } else {
                    abonosFuturos += prestamo.getMontoRedondeo();
                }
            }
        }
        //buscar si tiene dias sin pago despues de la fecha limite de pago y tiene algo por abonar
        if (prestamo.getFechaLimite().before(hoy)) { //ya pasó la fecha limite
            //para cada dia pasado la fecha limite del prestamo, contar lo que falta para la multa post-plazo
            Calendar calMultaPost = new GregorianCalendar();
            calMultaPost.setTime(prestamo.getFechaLimite());
            calMultaPost.add(Calendar.DAY_OF_YEAR, 1);
            while (!calMultaPost.getTime().after(hoy)) {
                Abono abonoAMultar = null;
                //buscar si ya existe el abono
                for (Abono abono : prestamo.getAbonoList()) {
                    if (abono.getAbonoPK().getFecha().equals(calMultaPost.getTime())) {
                        abonoAMultar = abono;
                        break;
                    }
                }
                if (abonoAMultar != null) {
                    int dif = multaMes - abonoAMultar.getMulta();
                    multaAPagarMes += dif;
                } else {
                    multaAPagarMes += multaMes;
                }
                calMultaPost.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        model.setTotalAbonar(abonoAPagar);
        model.setTotalMultar(multaAPagar);
        model.setTotalMultarMes(multaAPagarMes);
        model.setPorPagarIrAlCorriente(abonoAPagar + multaAPagar + multaAPagarMes);
        model.setPorPagarLiquidar(model.getPorPagarIrAlCorriente() + abonosFuturos);
    }

    /**
     * Decide si una lista de abonos del prestamo deberia de ignorarse la multa o no
     *
     * @param abonos lista de abonos de un prestamo
     * @return true si deberia de ignorarce la multa, false si deberria aplicarse la multa
     */
    private boolean ignorarMulta(Prestamo prestamo) {
        Date hoy = UtilsDate.dateWithoutTime();
        List<Abono> abonos = prestamo.getAbonoList().stream()
                .filter(a -> a.getAbonoPK().getFecha().before(hoy))
                .sorted((a1, a2) -> a1.getAbonoPK().getFecha().compareTo(a2.getAbonoPK().getFecha()))
                .collect(toList());
        Calendar cal = new GregorianCalendar();
        List<String> numDias = Arrays.asList(prestamo.getCliente().getDiasSinMulta().split(","));
        for (Abono abono : abonos) {
            cal.setTime(abono.getAbonoPK().getFecha());
            int diaSemanaAbono = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (abono.getAbonado()) {
                if (abono.getCantidad() < prestamo.getCobroDiario()) {
                    return false;
                }
            } else {
                if (!numDias.contains(String.valueOf(diaSemanaAbono))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Genera la distribucion del abono para los dias dentro del mes del prestamo
     *
     * @param abonos lista de abonos del prestamo, ya ordenados por fecha ascendente
     * @param fechaLimite fecha limite del prestamo
     * @param cobroDiario cantidad de dinero de cobro diario
     * @param cantidadAbono cantidad del abono actual
     * @param cantidadMulta cantidad de multa a cobrar por dia
     * @param ignorarMulta si se ignora multa para el prestamo
     * @param descripcion descripcion del abono
     */
    private void distribuirAbono(List<Abono> abonos, Date fechaLimite, int cobroDiario, int cantidadAbono,
            int cantidadMulta, boolean ignorarMulta, String descripcion, ModelDistribucionAbono distribucion, int montoRedondeo) {
        Date hoy = UtilsDate.dateWithoutTime();
        for (Abono abono : abonos) {
            if (cantidadAbono == 0 || abono.getAbonoPK().getFecha().after(fechaLimite)) {
                break;
            }
            if (!abono.getAbonoPK().getFecha().before(hoy)) {
                if (abono.getAbonado()) {
                    int faltaPorAbonar = cobroDiario - abono.getCantidad();
                    if (cantidadAbono > faltaPorAbonar) {
                        abono.setCantidad(cobroDiario);
                        cantidadAbono -= faltaPorAbonar;
                        distribucion.addAbono(faltaPorAbonar);
                    } else {
                        abono.setCantidad(abono.getCantidad() + cantidadAbono);
                        distribucion.addAbono(cantidadAbono);
                        cantidadAbono = 0;
                    }
                } else {
                    if (cantidadAbono > cobroDiario) {
                        cantidadAbono -= cobroDiario;
                        distribucion.addAbono(cobroDiario);
                    } else {
                        abono.setCantidad(cantidadAbono);
                        distribucion.addAbono(cantidadAbono);
                        cantidadAbono = 0;
                    }
                    abono.setAbonado(true);
                }
                continue;
            }
            if (abono.getAbonado() && abono.getCantidad() < cobroDiario) {
                if (!ignorarMulta) {
                    int faltaPorMultar = cantidadMulta - abono.getMulta();
                    if (cantidadAbono > faltaPorMultar) {
                        abono.setMulta(cantidadMulta);
                        abono.setMultaDes(descripcion);
                        cantidadAbono -= faltaPorMultar;
                        distribucion.addMulta(faltaPorMultar);
                    } else {
                        abono.setMulta(abono.getMulta() + cantidadAbono);
                        abono.setMultaDes(descripcion);
                        distribucion.addMulta(cantidadAbono);
                        cantidadAbono = 0;
                    }
                }
                int faltaPorAbonar = cobroDiario - abono.getCantidad();
                if (cantidadAbono > faltaPorAbonar) {
                    abono.setCantidad(cobroDiario);
                    cantidadAbono -= faltaPorAbonar;
                    distribucion.addAbono(faltaPorAbonar);
                } else {
                    abono.setCantidad(abono.getCantidad() + cantidadAbono);
                    distribucion.addAbono(cantidadAbono);
                    cantidadAbono = 0;
                }
            } else {
                if (!ignorarMulta) {
                    if (cantidadAbono > cantidadMulta) {
                        abono.setMulta(cantidadMulta);
                        abono.setMultaDes(descripcion);
                        cantidadAbono -= cantidadMulta;
                        distribucion.addMulta(cantidadMulta);
                    } else {
                        abono.setMulta(cantidadAbono);
                        abono.setMultaDes(descripcion);
                        distribucion.addMulta(cantidadAbono);
                        cantidadAbono = 0;
                    }
                }
                if (cantidadAbono > cobroDiario) {
                    cantidadAbono -= cobroDiario;
                    distribucion.addAbono(cobroDiario);
                } else {
                    abono.setCantidad(cantidadAbono);
                    distribucion.addAbono(cantidadAbono);
                    cantidadAbono = 0;
                }
                abono.setAbonado(true);
            }
        }
        if (cantidadAbono > 0) {
            Abono abonoLimite = abonos.stream().filter(a -> a.getAbonoPK().getFecha().equals(fechaLimite)).findFirst().get();
            //check to apply the amount per round
            if (abonoLimite.getAbonado() && abonoLimite.getCantidad() >= cobroDiario) {
                int faltaAbonar = (cobroDiario + montoRedondeo) - abonoLimite.getCantidad();
                if (cantidadAbono > faltaAbonar) {
                    abonoLimite.setCantidad(cobroDiario + montoRedondeo);
                    cantidadAbono -= faltaAbonar;
                } else {
                    abonoLimite.setCantidad(abonoLimite.getCantidad() + cantidadAbono);
                    cantidadAbono = 0;
                }
            }
        }
    }

    /**
     * Genera los abonos de multa post plazo
     *
     * @param abonos lista de abonos del prestamo ya ordenados ascendentemente
     * @param fechaLimite fecha limite del prestamo
     * @param cantidadAbono cantidad a abonar
     * @param multaPostPlazo cantidad de multa por dia despues del plazo
     */
    private int distribuirMultaPostPlazo(int prestamoId, List<Abono> abonos, Date fechaLimite,
            int cantidadAbono, int multaPostPlazo, ModelDistribucionAbono distribucion) {
        Date hoy = UtilsDate.dateWithoutTime();
        Calendar cal = new GregorianCalendar();
        cal.setTime(fechaLimite);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        while (cal.getTime().compareTo(hoy) <= 0 && cantidadAbono > 0) {
            Abono abono = null;
            for (Abono a : abonos) {
                if (a.getAbonoPK().getFecha().equals(cal.getTime())) {
                    abono = a;
                    break;
                }
            }
            if (abono != null) { //ya existe el abono
                abono.setAbonado(true); //forzar
                int faltaMultar = multaPostPlazo - abono.getMulta();
                if (cantidadAbono > faltaMultar) {
                    abono.setMulta(multaPostPlazo);
                    cantidadAbono -= faltaMultar;
                    distribucion.addMultaPostPlazo(faltaMultar);
                } else {
                    abono.setMulta(abono.getMulta() + cantidadAbono);
                    distribucion.addMultaPostPlazo(cantidadAbono);
                    cantidadAbono = 0;
                }
            } else { //crear abono nuevo
                int cantidadMulta = multaPostPlazo;
                if (cantidadAbono < multaPostPlazo) {
                    cantidadMulta = cantidadAbono;
                }
                cantidadAbono -= cantidadMulta;
                distribucion.addMultaPostPlazo(cantidadMulta);
                abono = new Abono(prestamoId, cal.getTime(), 0, true, cantidadMulta, "MultaPostPlazo");
                abonos.add(abono);
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        return cantidadAbono;
    }

    private DistribucionCobro generarDistribucionCobro(Cobro cobro,
            ModelPrestamoTotales t, ModelDistribucionAbono d) {
        DistribucionCobro dc = new DistribucionCobro(cobro.getId(),
                d.getAbonado(),
                d.getMultado(),
                d.getMultadoPostPlazo(),
                t.getTotalAbonado(),
                t.getTotalMultado(),
                t.getTotalRecuperado(),
                t.getPorcentajePagado(),
                t.getPorPagarIrAlCorriente(),
                t.getPorPagarLiquidar(),
                t.getTotalAbonar(),
                t.getTotalMultar(),
                t.getTotalMultarMes());
        cobro.setDistribucionCobro(dc);
        dc.setCobro(cobro);
        return dc;
    }

    /**
     * Verifica que el cliente no tenga 1 prestamo que no este 100 abonado
     *
     * @param clienteId
     */
    private void validarMultiplesPrestamos(int clienteId) throws InvalidParameterException {
        DaoCliente daoCliente = new DaoCliente();
        List<ModelSaldoPrestamos> saldos = daoCliente.saldoPrestamosCliente(clienteId);
        for (ModelSaldoPrestamos saldo : saldos) {
            if (saldo.getAbonado() < saldo.getCantidadAPagar()) {
                throw new InvalidParameterException("El cliente tiene prestamos no saldados");
            }
        }
    }

    @Override
    public void update(Prestamo entity) throws Exception {
        int cantidad = entity.getAbonoList().stream().mapToInt(a -> a.getCantidad()).sum();
        if (cantidad > entity.getCantidadPagar()) {
            throw new InvalidParameterException("No puede tener más abono que el monto a pagar del prestamo");
        }
        Config config = new DaoConfig().findFirst();
        int multaDiaria = config.getCantidadMultaDiaria();
        int multaMes = config.getCantidadMultaMes();

        boolean multaDiariaMayorAlPermitido = entity.getAbonoList().stream()
                .filter(a -> !a.getAbonoPK().getFecha().after(entity.getFechaLimite()))
                .anyMatch(a -> a.getMulta() > multaDiaria);
        if (multaDiariaMayorAlPermitido) {
            throw new InvalidParameterException(
                    "No puede tener más cantidad de multa que la configurada "
                    + "en los días DENTRO del plazo, el monto configurado es: " + multaDiaria);
        }

        boolean multaMesMayorAlPermitido = entity.getAbonoList().stream()
                .filter(a -> a.getAbonoPK().getFecha().after(entity.getFechaLimite()))
                .anyMatch(a -> a.getMulta() > multaMes);

        if (multaMesMayorAlPermitido) {
            throw new InvalidParameterException(
                    "No puede tener más cantidad de multa que la configurada "
                    + "en los días FUERA del plazo, el monto configurado es: " + multaMes);
        }

        super.update(entity); //To change body of generated methods, choose Tools | Templates.
    }

}
