package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.easymoney.entities.DistribucionCobro;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;


@SuppressLint("ValidFragment")
public class FragmentController extends DetallePrestamoContract.Fragment {

    private AbonoFragment abonoFragment;
    private ConsultaFragment consultaFragment;
    private CobroFragment cobroFragment;
    private Activity activity;

    public FragmentController(Activity activity,
                              AbonoFragment abonoFragment,
                              ConsultaFragment consultaFragment,
                              CobroFragment cobroFragment) {
        this.activity = activity;
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
    void llenarTotales(DistribucionCobro distribucion) {
        consultaFragment.setTotales(distribucion);
    }

    @Override
    void salir() {
        activity.finish();
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
        consultaFragment.showWARNING(message);
    }

    @Override
    public void showERROR(String message) {
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
