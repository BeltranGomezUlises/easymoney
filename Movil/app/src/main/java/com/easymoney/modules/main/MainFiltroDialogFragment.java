package com.easymoney.modules.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.easymoney.R;
import com.easymoney.models.EnumPrestamos;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;
import com.easymoney.modules.ingresosEgresos.IngresoEgresoFiltroDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulises on 29/03/2018.
 */

@SuppressLint("ValidFragment")
public class MainFiltroDialogFragment extends DialogFragment {

    private MainActivity activity;
    private Spinner spTipoPrestamo;

    @SuppressLint("ValidFragment")
    public MainFiltroDialogFragment(MainActivity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filtro_prestamos, null);

        spTipoPrestamo = rootView.findViewById(R.id.spTipoPrestamo);

        List<String> listaTiposPrestamos = new ArrayList<String>();
        listaTiposPrestamos.add("Por cobrar");
        listaTiposPrestamos.add("Todos");

        spTipoPrestamo.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listaTiposPrestamos));

        builder.setView(rootView)
                .setTitle("menu_ingresos_egresos")
                .setPositiveButton("Buscar", null)
                .setNegativeButton("Cancelar", (dialog, id) -> MainFiltroDialogFragment.this.getDialog().cancel());

        Dialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = ((android.support.v7.app.AlertDialog) dialog).getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                EnumPrestamos enumPrestamos = EnumPrestamos.POR_COBRAR;
                switch (spTipoPrestamo.getSelectedItemPosition()) {
                    case 0:
                        enumPrestamos = EnumPrestamos.POR_COBRAR;
                        break;
                    case 1:
                        enumPrestamos = EnumPrestamos.TODOS;
                        break;
                }
                activity.cargarPrestamos(enumPrestamos);
                dialog.dismiss();
            });
        });
        return dialog;
    }
}
