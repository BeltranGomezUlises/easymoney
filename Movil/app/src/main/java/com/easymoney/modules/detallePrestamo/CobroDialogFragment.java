package com.easymoney.modules.detallePrestamo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

    DetallePrestamoPresenter presenter;
    private EditText txtAbono;
    private EditText txtMulta;

    @SuppressLint("ValidFragment")
    public CobroDialogFragment(DetallePrestamoPresenter presenter) {
        this.presenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("oncreate del dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View rootView = inflater.inflate(R.layout.dialog_cobro, null);
        txtAbono = rootView.findViewById(R.id.tvAbono);
        txtMulta= rootView.findViewById(R.id.tvMulta);

        txtAbono.setText(String.valueOf(presenter.cantidadAAbonar()));
        txtMulta.setText(String.valueOf(presenter.multaAPagar()));

        builder.setView(rootView)
                .setTitle("Abonar")
                .setPositiveButton("Abonar",null)
                .setNegativeButton("Cancelar", (dialog, id) -> CobroDialogFragment.this.getDialog().cancel());

        Dialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (validateIns()){
                    abonar();
                    dialog.dismiss();
                }
            });
        });


        return dialog;
    }

    private void abonar(){
        System.out.println("abonando");
    }

    private boolean validateIns(){
        txtAbono.setError(null);
        txtMulta.setError(null);
        // Store values at the time of the login attempt.
        String abono = txtAbono.getText().toString();
        String multa = txtMulta.getText().toString();

        boolean valid = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(multa)) {
            txtMulta.setError("No puede ser vacío");
            focusView = txtMulta;
            valid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(abono)) {
            txtAbono.setError("No puede ser vacío");
            focusView = txtAbono;
            valid = false;
        }
        if (focusView != null){
            focusView.requestFocus();
        }
        return valid;
    }
}
