package com.easymoney.modules.detallePrestamo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.ModelTotalAPagar;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.baseClases.BaseFragment;

/**
 * Created by ulises on 15/01/2018.
 */
public class ConsultaFragment extends BaseFragment {

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
    private TextView tvTotalParaSaldar;
    private Button btnAbonar;

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
        tvTotalParaSaldar = rootView.findViewById(R.id.tvTotalParaSaldar);
        btnAbonar = rootView.findViewById(R.id.btnAbonar);

        btnAbonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preventDoubleClick(new Runnable() {
                    @Override
                    public void run() {
                        lanzarModalCobro();
                    }
                });
            }
        });

        getPresenter().subscribe();

        return rootView;
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

    public void setTotalParaSaldar(ModelTotalAPagar modelTotalAPagar) {
        tvTotalParaSaldar.setText(String.valueOf(modelTotalAPagar.getTotalPagar()));
    }

    private void lanzarModalCobro() {
        CobroDialogFragment newFragment = new CobroDialogFragment((DetallePrestamoContract.Presenter) getPresenter());
        newFragment.show(getActivity().getFragmentManager(), "cobro");
    }

    public void setBtnVisible(int visible) {
        btnAbonar.setVisibility(visible);
    }
}
