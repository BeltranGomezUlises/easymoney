package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;

import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;


@SuppressLint("ValidFragment")
public class FragmentController extends DetallePrestamoContract.Fragment {

    private AbonoFragment abonoFragment;
    private ConsultaFragment consultaFragment;
    private CobroFragment cobroFragment;

    public FragmentController(AbonoFragment abonoFragment,
                              ConsultaFragment consultaFragment,
                              CobroFragment cobroFragment) {
        this.abonoFragment = abonoFragment;
        this.consultaFragment = consultaFragment;
        this.cobroFragment = cobroFragment;
    }


    @Override
    void llenarDatosGenerales(Prestamo prestamo) {
        consultaFragment.llenarDatosGenerales(prestamo);
        abonoFragment.replaceData(prestamo.getAbonoList());
        cobroFragment.replaceData(prestamo.getCobroList());
    }

    @Override
    void llenarTotales(ModelPrestamoTotales modelPrestamoTotales) {
        consultaFragment.setTotales(modelPrestamoTotales);
    }

    @Override
    public void showMessage(String message) {
        abonoFragment.showMessage(message);
        consultaFragment.showMessage(message);
        cobroFragment.showMessage(message);
    }

    @Override
    public void showOK(String message) {
        abonoFragment.showOK(message);
        consultaFragment.showOK(message);
        cobroFragment.showOK(message);
    }

    @Override
    public void showWARNING(String message) {
        abonoFragment.showWARNING(message);
        consultaFragment.showWARNING(message);
        cobroFragment.showWARNING(message);
    }

    @Override
    public void showERROR(String message) {
        abonoFragment.showERROR(message);
        consultaFragment.showERROR(message);
        cobroFragment.showERROR(message);
    }

    @Override
    public void showMessage(String message, boolean duration) {
        abonoFragment.showMessage(message, duration);
        consultaFragment.showMessage(message, duration);
        cobroFragment.showMessage(message, duration);
    }

    @Override
    public void showLoading() {
        abonoFragment.showLoading();
        consultaFragment.showLoading();
        cobroFragment.showLoading();
    }

    @Override
    public void showLoading(String title, String message) {
        abonoFragment.showLoading(title, message);
        consultaFragment.showLoading(title, message);
        cobroFragment.showLoading(title, message);
    }

    @Override
    public void stopShowLoading() {
        abonoFragment.stopShowLoading();
        consultaFragment.stopShowLoading();
        cobroFragment.stopShowLoading();
    }

    @Override
    public void hideKeyBoard() {
        abonoFragment.hideKeyBoard();
        consultaFragment.hideKeyBoard();
        cobroFragment.hideKeyBoard();
    }

    @Override
    public DetallePrestamoContract.Presenter getPresenter() {
        return super.getPresenter();
    }

    @Override
    public void setPresenter(DetallePrestamoContract.Presenter presenter) {
        abonoFragment.setPresenter(presenter);
        consultaFragment.setPresenter(presenter);
        cobroFragment.setPresenter(presenter);
    }

}
