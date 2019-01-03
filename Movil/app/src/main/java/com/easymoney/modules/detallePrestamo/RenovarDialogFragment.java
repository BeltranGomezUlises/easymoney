package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Prestamo;

@SuppressLint("ValidFragment")
public class RenovarDialogFragment extends DialogFragment {

    private DetallePrestamoContract.Presenter presenter;
    private EditText edtRenovacion;
    private TextView tvTotalEntregar;
    private Prestamo prestamo;
    private int porPagarLiquidar;

    @SuppressLint("ValidFragment")
    public RenovarDialogFragment(DetallePrestamoContract.Presenter presenter, Prestamo prestamo, int porPagarLiquidar) {
        this.presenter = presenter;
        this.prestamo = prestamo;
        this.porPagarLiquidar = porPagarLiquidar;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_renovacion, null);
        tvTotalEntregar = rootView.findViewById(R.id.tvTotalEntregar);

        int cantidadEntregar = prestamo.getCantidad() - porPagarLiquidar;
        tvTotalEntregar.setText("$" + cantidadEntregar);

        edtRenovacion = rootView.findViewById(R.id.edtRenovacion);
        edtRenovacion.setText(String.valueOf(prestamo.getCantidad()));
        edtRenovacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    int cantidad = Integer.parseInt(charSequence.toString());
                    int cantidadEntregar = cantidad - porPagarLiquidar;
                    tvTotalEntregar.setText("$" + cantidadEntregar);
                } else {
                    tvTotalEntregar.setText("$0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        builder.setView(rootView)
                .setTitle("Renovar")
                .setPositiveButton("Renovar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RenovarDialogFragment.this.getDialog().cancel();
                    }
                });

        final Dialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validateIns()) {
                            renovar();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });

        return dialog;
    }

    private void renovar() {
        final int renovacion = Integer.parseInt(edtRenovacion.getText().toString());
        presenter.renovar(prestamo.getId(), renovacion);
    }

    private boolean validateIns() {
        edtRenovacion.setError(null);
        // Store values at the time of the login attempt.
        final String renovacion = edtRenovacion.getText().toString();
        final int renovacionInt = Integer.parseInt(renovacion);
        boolean valid = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(renovacion)) {
            edtRenovacion.setError("No puede ser vacío");
            focusView = edtRenovacion;
            valid = false;
        }
        if (renovacionInt <= 0) {
            edtRenovacion.setError("Renovación debe ser mayor a 0");
            edtRenovacion.requestFocus();
            return false;
        }
        if (renovacionInt - porPagarLiquidar < 0) {
            edtRenovacion.setError("No puede renovar el prestamo por una cantidad menor a la deuda");
            edtRenovacion.requestFocus();
            return false;
        }
        if (focusView != null) {
            focusView.requestFocus();
        }
        return valid;
    }
}
