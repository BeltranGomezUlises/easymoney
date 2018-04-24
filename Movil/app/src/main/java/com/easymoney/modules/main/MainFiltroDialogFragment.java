package com.easymoney.modules.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_filtro_prestamos, null);

        spTipoPrestamo = rootView.findViewById(R.id.spTipoPrestamo);

        List<String> listaTiposPrestamos = new ArrayList<String>();
        listaTiposPrestamos.add("Por cobrar hoy");
        listaTiposPrestamos.add("Por cobrar");
        listaTiposPrestamos.add("Todos");

        spTipoPrestamo.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(), R.layout.item_spinner, listaTiposPrestamos));

        builder.setView(rootView)
                .setTitle("filtro")
                .setPositiveButton("Buscar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainFiltroDialogFragment.this.getDialog().cancel();
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
                        EnumPrestamos enumPrestamos = EnumPrestamos.POR_COBRAR;
                        switch (spTipoPrestamo.getSelectedItemPosition()) {
                            case 0:
                                enumPrestamos = EnumPrestamos.POR_COBRAR_HOY;
                                break;
                            case 1:
                                enumPrestamos = EnumPrestamos.POR_COBRAR;
                                break;
                            case 2:
                                enumPrestamos = EnumPrestamos.TODOS;
                                break;
                        }
                        activity.cargarPrestamos(enumPrestamos);
                        dialog.dismiss();
                    }
                });
            }
        });
        return dialog;
    }
}
