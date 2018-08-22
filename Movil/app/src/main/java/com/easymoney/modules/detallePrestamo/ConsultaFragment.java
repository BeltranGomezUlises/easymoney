package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.utils.UtilsDate;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by ulises on 15/01/2018.
 */
public class ConsultaFragment extends Fragment implements DetallePrestamoContract.View {

    private DetallePrestamoPresenter presenter;
    private TextView tvNombreCliente;
    private TextView tvApodoCliente;
    private TextView tvNumPrestamo;
    private TextView tvAbonoDiario;
    private TextView tvCantidadPrestamo;
    private TextView tvCantidadPagar;
    private TextView tvFechaHoraPrestamo;
    private TextView tvFechaLimite;
    private TextView tvTotalAbonado;
    private TextView tvTotalRecuperado;
    private TextView tvTotalMultado;
    private TextView tvTotalPorcentaje;
    private TextView tvTotalPorPagar;
    private ProgressDialog dialog;

    @SuppressLint("ValidFragment")
    private ConsultaFragment() {

    }

    public static ConsultaFragment getInstance(DetallePrestamoPresenter prestamoPresenter) {
        ConsultaFragment instance = new ConsultaFragment();
        prestamoPresenter.setConsultaFragment(instance);
        instance.setPresenter(prestamoPresenter);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_prestamo_consulta, container, false);

        tvNombreCliente = rootView.findViewById(R.id.tvNombreCliente);
        tvApodoCliente = rootView.findViewById(R.id.tvApodoCliente);
        tvCantidadPrestamo = rootView.findViewById(R.id.tvCantidad);
        tvCantidadPagar = rootView.findViewById(R.id.tvCantidadPagar);
        tvFechaHoraPrestamo = rootView.findViewById(R.id.tvFechaPrestamo);
        tvFechaLimite = rootView.findViewById(R.id.tvFechaLimite);
        tvTotalAbonado = rootView.findViewById(R.id.tvTotalAbonado);
        tvTotalMultado = rootView.findViewById(R.id.tvTotalMultado);
        tvTotalRecuperado = rootView.findViewById(R.id.tvTotalRecuperado);
        tvTotalPorcentaje = rootView.findViewById(R.id.tvTotalPorcentaje);
        tvTotalPorPagar = rootView.findViewById(R.id.tvTotalporPagar);
        tvNumPrestamo = rootView.findViewById(R.id.tvNumPrestamo);
        tvAbonoDiario = rootView.findViewById(R.id.tvAbonoDiario);

        this.presenter.subscribe();

        return rootView;
    }

    @Override
    public void showLoading(boolean active) {
        if (active){
            dialog = ProgressDialog.show(getActivity(), "Cargando","Por favor espere...", true);
        }else{
            if (dialog != null){
                dialog.cancel();
            }
        }
    }

    public void showMessage(String message) {
        Snackbar.make(this.getView(), message, LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(DetallePrestamoContract.Presenter presenter) {
        this.presenter = (DetallePrestamoPresenter) presenter;
    }

    public void llenarDatosGenerales(Prestamo prestamo) {
        tvNombreCliente.setText(prestamo.getCliente().getNombre());
        tvApodoCliente.setText(prestamo.getCliente().getApodo());
        tvNumPrestamo.setText(String.valueOf(prestamo.getId()));
        tvCantidadPrestamo.setText("$" + prestamo.getCantidad());
        tvCantidadPagar.setText("$" + prestamo.getCantidadPagar());
        tvAbonoDiario.setText("$" + prestamo.getCobroDiario());
        tvFechaHoraPrestamo.setText(UtilsDate.format_D_MM_YYYY_HH_MM(prestamo.getFecha()));
        tvFechaLimite.setText(UtilsDate.format_D_MM_YYYY(prestamo.getFechaLimite()));
    }

    public void setTotales(ModelPrestamoTotales data) {
        tvTotalAbonado.setText("$" + data.getTotalAbonado());
        tvTotalMultado.setText("$" + data.getTotalMultado());
        tvTotalRecuperado.setText("$" + data.getTotalRecuperado());
        tvTotalPorcentaje.setText(data.getPorcentajePagado() + "%");
        tvTotalPorPagar.setText("$" + data.getPorPagar());
    }
}
