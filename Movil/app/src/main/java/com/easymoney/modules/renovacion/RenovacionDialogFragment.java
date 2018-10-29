package com.easymoney.modules.renovacion;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.ModelPrestamoTotales;
import com.easymoney.models.services.Response;
import com.easymoney.utils.UtilsDate;

import io.reactivex.functions.Consumer;

@SuppressLint("ValidFragment")
public class RenovacionDialogFragment extends android.support.v4.app.DialogFragment {

    private RenovacionPresenter presenter;
    private ModelPrestamoTotales modelTotales;
    private Prestamo prestamo;

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

    private TextView tvTotalEntregar;
    private EditText edtRenovacion;

    private LinearLayout layoutTotales;
    private LinearLayout layoutLoading;
    private LinearLayout layoutRenovacion;

    public RenovacionDialogFragment(RenovacionPresenter presenter, Prestamo prestamo) {
        this.presenter = presenter;
        this.prestamo = prestamo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_renovacion, null);

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
        tvTotalEntregar = rootView.findViewById(R.id.tvTotalEntregar);

        edtRenovacion = rootView.findViewById(R.id.edtRenovacion);
        edtRenovacion.setText(String.valueOf(prestamo.getCantidad()));
        edtRenovacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcularTotalEntregar();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        layoutLoading = rootView.findViewById(R.id.layoutLoading);
        layoutTotales = rootView.findViewById(R.id.layoutTotales);
        layoutRenovacion = rootView.findViewById(R.id.layoutRenovacion);

        builder.setView(rootView)
                .setTitle("Renovación")
                .setPositiveButton("Renovar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RenovacionDialogFragment.this.getDialog().cancel();
                    }
                });

        final Dialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((android.support.v7.app.AlertDialog) dialog).getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = edtRenovacion.getText().toString();
                        if (value.isEmpty()){
                            edtRenovacion.setError("Valor requerido");
                            edtRenovacion.requestFocus();
                            return;
                        }
                        Integer renovacion = Integer.parseInt(value);
                        presenter.renovar(prestamo.getId(), renovacion);
                        dialog.dismiss();
                    }
                });
            }
        });

        llenarDatosGenerales(this.prestamo);
        cargarTotales(prestamo.getId());
        return dialog;
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
        calcularTotalEntregar();
    }

    void calcularTotalEntregar() {
        String edtValue = edtRenovacion.getText().toString();
        if (!edtValue.isEmpty()) {
            int renovacion = Integer.parseInt(edtValue);
            tvTotalEntregar.setText("$ " + (renovacion - modelTotales.getPorPagar()));
        }
    }

    private void cargarTotales(int id) {
        presenter.totalesPrestamo(id, new Consumer<Response<ModelPrestamoTotales, Object>>() {
            @Override
            public void accept(Response<ModelPrestamoTotales, Object> res) {
                modelTotales = res.getData();
                setTotales(res.getData());
                layoutLoading.setVisibility(View.GONE);
                layoutTotales.setVisibility(View.VISIBLE);
                layoutRenovacion.setVisibility(View.VISIBLE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                presenter.showError(throwable.getMessage());
            }
        });
    }
}
