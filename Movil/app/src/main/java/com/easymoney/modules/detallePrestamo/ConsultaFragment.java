package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Cliente;
import com.easymoney.entities.DistribucionCobro;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.utils.DownloadImageTask;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.baseClases.BaseFragment;
import com.easymoney.utils.services.IService;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by ulises on 15/01/2018.
 */
@SuppressLint("ValidFragment")
public class ConsultaFragment extends BaseFragment {

    private ImageView imgCliente;
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
    private TextView tvAAbonar;
    private TextView tvAMultar;
    private TextView tvAMultarPostPlazo;
    private TextView tvParaIrAlCorriente;
    private TextView tvParaLiquidar;

    private LinearLayout layoutTotales;
    private LinearLayout layoutLoading;

    private Button btnAbonar;
    private Button btnRenovar;
    private int cantidadParaIrAlCorriente;
    private int porPagarLiquidar;

    private boolean enRenovacion;

    @SuppressLint("ValidFragment")
    public ConsultaFragment(boolean enRenovacion) {
        this.enRenovacion = enRenovacion;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_prestamo_consulta, container, false);

        imgCliente = rootView.findViewById(R.id.imgCliente);
        imgCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_image, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                photoView.setImageBitmap(((BitmapDrawable)imgCliente.getDrawable()).getBitmap());
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


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
        tvNumPrestamo = rootView.findViewById(R.id.tvNumPrestamo);
        tvAbonoDiario = rootView.findViewById(R.id.tvAbonoDiario);

        tvAAbonar = rootView.findViewById(R.id.tvAAbonar);
        tvAMultar = rootView.findViewById(R.id.tvAMultar);
        tvAMultarPostPlazo = rootView.findViewById(R.id.tvAMultarPostPlazo);
        tvParaIrAlCorriente = rootView.findViewById(R.id.tvParaIrAlCorriente);
        tvParaLiquidar = rootView.findViewById(R.id.tvParaLiquidar);

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

        btnRenovar = rootView.findViewById(R.id.btnRenovar);
        btnRenovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preventDoubleClick(new Runnable() {
                    @Override
                    public void run() {
                        lanzarModalRenovar();
                    }
                });
            }
        });

        //botones invisibles hasta tener totales
        btnAbonar.setVisibility(View.GONE);
        btnRenovar.setVisibility(View.GONE);

        layoutTotales = rootView.findViewById(R.id.layoutTotales);
        layoutTotales.setVisibility(View.GONE);

        layoutLoading = rootView.findViewById(R.id.layoutLoading);
        layoutLoading.setVisibility(View.VISIBLE);

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
        tvFechaHoraPrestamo.setText(UtilsDate.format_D_MM_YYYY(prestamo.getFecha()));
        tvFechaLimite.setText(UtilsDate.format_D_MM_YYYY(prestamo.getFechaLimite()));

        Cliente cliente = prestamo.getCliente();
        if (cliente.getImagen() != null) {
            new DownloadImageTask(imgCliente)
                    .execute(IService.END_POINT + "utilerias/getFile/" + cliente.getImagen());
        }

    }

    public void setTotales(ModelPrestamoTotales model) {
        tvTotalAbonado.setText("$" + model.getTotalAbonado());
        tvTotalMultado.setText("$" + model.getTotalMultado());
        tvTotalRecuperado.setText("$" + model.getTotalRecuperado());
        tvTotalPorcentaje.setText(model.getPorcentajePagado() + "%");
        tvAAbonar.setText("$" + model.getTotalAbonar());
        tvAMultar.setText("$" + model.getTotalMultar());
        tvAMultarPostPlazo.setText("$" + model.getTotalMultarMes());
        tvParaIrAlCorriente.setText("$" + model.getPorPagarIrAlCorriente());
        tvParaLiquidar.setText("$" + model.getPorPagarLiquidar());

        layoutLoading.setVisibility(View.GONE);
        layoutTotales.setVisibility(View.VISIBLE);

        if (model.getPorcentajePagado() < 100) {
            if (!enRenovacion) {
                this.cantidadParaIrAlCorriente = model.getPorPagarIrAlCorriente();
                btnAbonar.setVisibility(View.VISIBLE);
            } else {
                btnRenovar.setVisibility(View.VISIBLE);
                this.porPagarLiquidar = model.getPorPagarLiquidar();
            }
        }

    }

    private void lanzarModalCobro() {
        CobroDialogFragment newFragment = new CobroDialogFragment(
                (DetallePrestamoContract.Presenter) getPresenter(),
                this.cantidadParaIrAlCorriente);
        newFragment.show(getActivity().getFragmentManager(), "cobro");
    }

    private void lanzarModalRenovar() {
        DetallePrestamoContract.Presenter presenter = (DetallePrestamoContract.Presenter) getPresenter();
        RenovarDialogFragment newFragment = new RenovarDialogFragment(presenter, presenter.getPrestamo(), this.porPagarLiquidar);
        newFragment.show(getActivity().getFragmentManager(), "Renovación");
    }

    public void setTotales(final DistribucionCobro model) {
        tvTotalAbonado.setText("$" + model.getTotalAbonado());
        tvTotalMultado.setText("$" + model.getTotalMultado());
        tvTotalRecuperado.setText("$" + model.getTotalRecuperado());
        tvTotalPorcentaje.setText(model.getPorcentajePagado() + "%");
        tvAAbonar.setText("$" + model.getTotalAbonar());
        tvAMultar.setText("$" + model.getTotalMultar());
        tvAMultarPostPlazo.setText("$" + model.getTotalMultarMes());
        tvParaIrAlCorriente.setText("$" + model.getPorPagarIrAlCorriente());
        tvParaLiquidar.setText("$" + model.getPorPagarLiquidar());

        layoutLoading.setVisibility(View.GONE);
        layoutTotales.setVisibility(View.VISIBLE);

        if (model.getPorcentajePagado() == 100) {
            btnAbonar.setVisibility(View.GONE);
        } else {
            cantidadParaIrAlCorriente = model.getPorPagarIrAlCorriente();
            btnAbonar.setVisibility(View.VISIBLE);
        }
    }
}
