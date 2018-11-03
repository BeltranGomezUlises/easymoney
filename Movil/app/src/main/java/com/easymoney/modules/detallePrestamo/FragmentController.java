package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;

import com.easymoney.entities.Abono;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;

import java.util.List;


@SuppressLint("ValidFragment")
public class FragmentController extends DetallePrestamoContract.Fragment {

    private AbonoFragment abonoFragment;
    private ConsultaFragment consultaFragment;

    public FragmentController(AbonoFragment abonoFragment, ConsultaFragment consultaFragment) {
        this.abonoFragment = abonoFragment;
        this.consultaFragment = consultaFragment;
    }

    @Override
    void setTotales(ModelPrestamoTotales totales) {
        consultaFragment.setTotales(totales);
    }

    @Override
    void llenarDatosGenerales(Prestamo prestamo) {
        consultaFragment.llenarDatosGenerales(prestamo);
    }

    @Override
    void replaceData(List<Abono> abonos) {
        abonoFragment.replaceData(abonos);
    }




    @Override
    public void showMessage(String message) {
        abonoFragment.showMessage(message);
        consultaFragment.showMessage(message);
    }

    @Override
    public void showOK(String message) {
        abonoFragment.showOK(message);
        consultaFragment.showOK(message);
    }

    @Override
    public void showWARNING(String message) {
        abonoFragment.showWARNING(message);
        consultaFragment.showWARNING(message);
    }

    @Override
    public void showERROR(String message) {
        abonoFragment.showERROR(message);
        consultaFragment.showERROR(message);
    }

    @Override
    public void showMessage(String message, boolean duration) {
        abonoFragment.showMessage(message, duration);
        consultaFragment.showMessage(message, duration);
    }

    @Override
    public void showLoading() {
        abonoFragment.showLoading();
        consultaFragment.showLoading();
    }

    @Override
    public void showLoading(String title, String message) {
        abonoFragment.showLoading(title, message);
        consultaFragment.showLoading(title, message);
    }

    @Override
    public void stopShowLoading() {
        abonoFragment.stopShowLoading();
        consultaFragment.stopShowLoading();
    }

    @Override
    public void hideKeyBoard() {
        abonoFragment.hideKeyBoard();
        consultaFragment.hideKeyBoard();
    }

    @Override
    public DetallePrestamoContract.Presenter getPresenter() {
        return super.getPresenter();
    }

    @Override
    public void setPresenter(DetallePrestamoContract.Presenter presenter) {
        abonoFragment.setPresenter(presenter);
        consultaFragment.setPresenter(presenter);
    }

    @Override
    public void preventDoubleClick(Runnable runable) {
        super.preventDoubleClick(runable);
    }
}
