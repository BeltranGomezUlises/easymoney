package com.easymoney.modules.ingresosEgresos;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.easymoney.R;
import com.easymoney.models.EnumRangoFecha;
import com.easymoney.models.EnumTipoMovimiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulises on 27/03/2018.
 */

@SuppressLint("ValidFragment")
public class IngresoEgresoFiltroDialogFragment extends DialogFragment {

    private IngresosEgresosPresenter presenter;
    private Spinner spTipoMov;
    private Spinner spRangoFecha;
    private Context context;

    @SuppressLint("ValidFragment")
    public IngresoEgresoFiltroDialogFragment(IngresosEgresosPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filtro_movimiento, null);

        spTipoMov = rootView.findViewById(R.id.spTipoMov);
        spRangoFecha = rootView.findViewById(R.id.spRangoFecha);

        List<String> listaRangoFechas = new ArrayList<String>();
        listaRangoFechas.add("7 días");
        listaRangoFechas.add("15 días");
        listaRangoFechas.add("30 días");
        listaRangoFechas.add("Todos");

        List<String> listaTipoMov = new ArrayList<String>();
        listaTipoMov.add("Ingresos");
        listaTipoMov.add("Egresos");
        listaTipoMov.add("Todos");

        spTipoMov.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, listaTipoMov));
        spRangoFecha.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, listaRangoFechas));

        builder.setView(rootView)
                .setTitle("filtro")
                .setPositiveButton("Buscar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IngresoEgresoFiltroDialogFragment.this.getDialog().cancel();
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
                        EnumTipoMovimiento enumTipoMovimiento = EnumTipoMovimiento.INGRESOS;
                        EnumRangoFecha enumRangoFecha = EnumRangoFecha.DIAS7;
                        switch (spTipoMov.getSelectedItemPosition()) {
                            case 0:
                                enumTipoMovimiento = EnumTipoMovimiento.INGRESOS;
                                break;
                            case 1:
                                enumTipoMovimiento = EnumTipoMovimiento.EGRESOS;
                                break;
                            case 2:
                                enumTipoMovimiento = EnumTipoMovimiento.TODOS;
                                break;
                        }
                        switch (spRangoFecha.getSelectedItemPosition()) {
                            case 0:
                                enumRangoFecha = EnumRangoFecha.DIAS7;
                                break;
                            case 1:
                                enumRangoFecha = EnumRangoFecha.DIAS15;
                                break;
                            case 2:
                                enumRangoFecha = EnumRangoFecha.DIAS30;
                                break;
                            case 3:
                                enumRangoFecha = EnumRangoFecha.TODOS;
                                break;
                        }
                        presenter.cargarMovimientos(enumTipoMovimiento, enumRangoFecha);
                        dialog.dismiss();
                    }
                });
            }
        });

        return dialog;
    }
}
