package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.easymoney.R;

/**
 * Created by ulises on 21/01/2018.
 */
@SuppressLint("ValidFragment")
public class CobroDialogFragment extends DialogFragment {

    private DetallePrestamoContract.Presenter presenter;
    private EditText txtAbonar;
    private EditText txtDescripcion;
    private final int cantidadParaIrAlCorriente;

    @SuppressLint("ValidFragment")
    public CobroDialogFragment(DetallePrestamoContract.Presenter presenter, int cantidadParaIrAlCorriente) {
        this.presenter = presenter;
        this.cantidadParaIrAlCorriente = cantidadParaIrAlCorriente;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_cobro, null);

        txtAbonar = rootView.findViewById(R.id.txtAbonar);
        txtDescripcion = rootView.findViewById(R.id.txtDes);

        txtAbonar.setText(String.valueOf(cantidadParaIrAlCorriente));

        builder.setView(rootView)
                .setTitle("Abonar")
                .setPositiveButton("Abonar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CobroDialogFragment.this.getDialog().cancel();
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
                        if (validateIns() && abonar()) {
                            dialog.dismiss();
                        }
                    }

                });
            }
        });


        return dialog;
    }

    private boolean abonar() {
        final int abono = Integer.parseInt(txtAbonar.getText().toString());
        if (abono <= 0) {
            txtAbonar.setError("Abono debe ser mayor a 0");
            txtAbonar.requestFocus();
            return false;
        }
        final String multaDes = txtDescripcion.getText().toString();
        presenter.abonar(abono, multaDes);
        return true;
    }

    private boolean validateIns() {
        txtAbonar.setError(null);
        // Store values at the time of the login attempt.
        final String abono = txtAbonar.getText().toString();

        boolean valid = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(abono)) {
            txtAbonar.setError("No puede ser vacío");
            focusView = txtAbonar;
            valid = false;
        }

        if (focusView != null) {
            focusView.requestFocus();
        }
        return valid;
    }
}
