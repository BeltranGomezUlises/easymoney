package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.models.ModelTotalAPagar;

/**
 * Created by ulises on 21/01/2018.
 */
@SuppressLint("ValidFragment")
public class CobroDialogFragment extends DialogFragment {

    DetallePrestamoPresenter presenter;
    private TextView tvAbono;
    private TextView tvMulta;
    private EditText txtAbonar;
    private EditText txtDescripcion;

    @SuppressLint("ValidFragment")
    public CobroDialogFragment(DetallePrestamoPresenter presenter) {
        this.presenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View rootView = inflater.inflate(R.layout.dialog_cobro, null);
        tvAbono = rootView.findViewById(R.id.tvAbono);
        tvMulta = rootView.findViewById(R.id.tvMulta);
        txtAbonar = rootView.findViewById(R.id.txtAbonar);
        txtDescripcion = rootView.findViewById(R.id.txtDes);

        ModelTotalAPagar model = presenter.calcularTotalesPagar();

        tvAbono.setText("$" + String.valueOf(model.getTotalAbonar()));
        tvMulta.setText("$" + String.valueOf(model.getTotalMultar()));
        txtAbonar.setText(String.valueOf(model.getTotalPagar()));

        builder.setView(rootView)
                .setTitle("Abonar")
                .setPositiveButton("Abonar", null)
                .setNegativeButton("Cancelar", (dialog, id) -> CobroDialogFragment.this.getDialog().cancel());

        Dialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (validateIns()) {
                    abonar();
                    dialog.dismiss();
                }
            });
        });


        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void abonar() {
        final int abono = Integer.parseInt(txtAbonar.getText().toString());
        final String multaDes = txtDescripcion.getText().toString();
        presenter.abonarAlPrestamo(abono, multaDes);
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
