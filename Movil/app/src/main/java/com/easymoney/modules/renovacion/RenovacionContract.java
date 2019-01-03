package com.easymoney.modules.renovacion;

import com.easymoney.entities.Cliente;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.baseClases.BasePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface RenovacionContract {

    abstract class Fragment extends BaseFragment<Presenter> {
        abstract void setClientes(List<Cliente> clientes);
        public abstract void mostrarPrestamos(List<Prestamo> prestamos);
    }

    abstract class Presenter extends BasePresenter<Fragment> {
        abstract void filtrar(String texto);
        abstract void cargarPrestamos(Cliente c);
        abstract void detallePrestamo(Prestamo p);
    }

}
