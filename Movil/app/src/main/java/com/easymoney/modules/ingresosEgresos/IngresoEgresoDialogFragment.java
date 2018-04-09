package com.easymoney.modules.ingresosEgresos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.easymoney.R;
import com.easymoney.entities.Movimiento;
import com.easymoney.utils.UtilsPreferences;

import java.util.Date;

/**
 * Created by ulises on 04/03/2018.
 */

@SuppressLint("ValidFragment")
public class IngresoEgresoDialogFragment extends DialogFragment {

    private IngresosEgresosPresenter presenter;
    private EditText etDescripciom;
    private EditText etCantidad;
    private CheckBox chkIngresoEgreso;

    @SuppressLint("ValidFragment")
    public IngresoEgresoDialogFragment(IngresosEgresosPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View rootView = inflater.inflate(R.layout.dialog_movimiento, null);

        etCantidad = rootView.findViewById(R.id.etCantidad);
        etDescripciom = rootView.findViewById(R.id.etDes);
        chkIngresoEgreso = rootView.findViewById(R.id.chkIngresoEgreso);

        builder.setView(rootView)
                .setTitle("Agregar")
                .setPositiveButton("Agregar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IngresoEgresoDialogFragment.this.getDialog().cancel();
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
                        if (validateIns()) {
                            agregarMovmiento();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    private boolean validateIns() {
        etCantidad.setError(null);
        etDescripciom.setError(null);
        // Store values at the time of the login attempt.
        String cantidadMovimiento = etCantidad.getText().toString();
        String descripcionMovimiento = etDescripciom.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid descripcionMovimiento
        if (TextUtils.isEmpty(descripcionMovimiento)) {
            etDescripciom.setError(getString(R.string.error_field_required));
            focusView = etDescripciom;
            cancel = true;
        }

        // Check for a valid cantidadMovimiento address.
        if (TextUtils.isEmpty(cantidadMovimiento)) {
            etCantidad.setError(getString(R.string.error_field_required));
            focusView = etCantidad;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }
        return true;
    }

    private void agregarMovmiento() {
        int cantidad = Integer.parseInt(etCantidad.getText().toString());
        if (!chkIngresoEgreso.isChecked()) {
            cantidad *= -1;
        }
        String descripcion = etDescripciom.getText().toString();
        Date fecha = new Date();
        Movimiento movimiento = new Movimiento(cantidad, fecha, descripcion, UtilsPreferences.loadLogedUser());
        presenter.agregarIngresoEgreso(movimiento);
    }

}
