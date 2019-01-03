package com.easymoney.modules.renovacion;

import android.app.Activity;
import android.content.Intent;

import com.easymoney.data.repositories.ClienteRepository;
import com.easymoney.data.repositories.PrestamoRepository;
import com.easymoney.entities.Cliente;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelFiltroPrestamos;
import com.easymoney.models.services.Response;
import com.easymoney.modules.detallePrestamo.DetallePrestamoActivity;
import com.easymoney.modules.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.easymoney.models.services.Errors.ERROR_COMUNICACION;


public class RenovacionPresenter extends RenovacionContract.Presenter {

    private PrestamoRepository repository;
    private CompositeDisposable mCompositeDisposable;
    private List<Cliente> clientes;
    private Activity activity;

    public static final int RESULT_RENOVACION = 10;

    RenovacionPresenter(PrestamoRepository repository, Activity activity) {
        this.repository = repository;
        mCompositeDisposable = new CompositeDisposable();
        this.activity = activity;
    }

    @Override
    public void subscribe() {
        getFragment().showLoading("Cargando clientes", "espere un momento por favor...");
        ClienteRepository.getInstance().findAll(new Consumer<Response<List<Cliente>, Object>>() {
            @Override
            public void accept(final Response<List<Cliente>, Object> r) throws Exception {
                evalResponse(r, new Runnable() {
                    @Override
                    public void run() {
                        clientes = r.getData();
                        getFragment().setClientes(clientes);
                        getFragment().stopShowLoading();
                    }
                });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getFragment().stopShowLoading();
                getFragment().showERROR(ERROR_COMUNICACION);
                throwable.printStackTrace();

            }
        });
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.dispose();
    }

    @Override
    void filtrar(String texto) {
        List<Cliente> filtrados = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(texto)
                    || cliente.getApodo().toLowerCase().contains(texto)) {
                filtrados.add(cliente);
            }
        }
        getFragment().setClientes(filtrados);
    }

    @Override
    void cargarPrestamos(Cliente c) {
        getFragment().showLoading("Cargando prestamos", "Por favor espere...");
        repository.prestamosDelCliente(c.getId(), new Consumer<Response<List<Prestamo>, Object>>() {
            @Override
            public void accept(final Response<List<Prestamo>, Object> r) {
                evalResponse(r, new Runnable() {
                    @Override
                    public void run() {
                        getFragment().stopShowLoading();
                        List<Prestamo> prestamosDelCliente = r.getData();
                        if (prestamosDelCliente.isEmpty()){
                            getFragment().showMessage("Cliente sín prestamos activos");
                            return;
                        }
                        if (prestamosDelCliente.size() == 1){
                            detallePrestamo(prestamosDelCliente.get(0));
                            return;
                        }
                        getFragment().mostrarPrestamos(prestamosDelCliente);
                    }
                });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable){
                getFragment().stopShowLoading();
                getFragment().showERROR(ERROR_COMUNICACION);
                throwable.printStackTrace();
            }
        });
    }

    @Override
    void detallePrestamo(Prestamo p) {
        Intent intent = new Intent(activity, DetallePrestamoActivity.class);
        intent.putExtra("Prestamo", p);
        intent.putExtra("renovacion", true);
        activity.startActivityForResult(intent, RESULT_RENOVACION);
    }



}
