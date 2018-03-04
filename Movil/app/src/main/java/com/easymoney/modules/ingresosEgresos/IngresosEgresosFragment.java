package com.easymoney.modules.ingresosEgresos;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Movimiento;
import com.easymoney.utils.UtilsDate;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by ulises on 03/03/2018.
 */

@SuppressLint("ValidFragment")
public class IngresosEgresosFragment extends Fragment implements IngresosEgresosContract.View {

    private static IngresosEgresosFragment instance;

    private ProgressDialog dialog;
    private ListView listView;
    private MovimientoAdapter movimientoAdapter;
    private IngresosEgresosPresenter presenter;

    /**
     * prevenir instanciación externa
     */
    @SuppressLint("ValidFragment")
    private IngresosEgresosFragment(IngresosEgresosPresenter presenter) {
        this.presenter = presenter;
    }

    public static IngresosEgresosFragment getInstance(IngresosEgresosPresenter presenter) {
        if (instance == null) {
            instance = new IngresosEgresosFragment(presenter);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ingresos_egresos, container, false);

        listView = root.findViewById(R.id.lista_movimientos);
        movimientoAdapter = new MovimientoAdapter(new ArrayList<>());
        listView.setAdapter(movimientoAdapter);

        this.presenter.subscribe();

        return root;
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
            dialog = ProgressDialog.show(getActivity(), "Cargando", "Por favor espere...", true);
        } else {
            if (dialog != null) {
                dialog.cancel();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(this.getView(), message, LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(IngresosEgresosContract.Presenter presenter) {
        this.presenter = (IngresosEgresosPresenter) presenter;
    }

    public void replaceMovimientoList(List<Movimiento> movimientos) {
        movimientoAdapter.replaceData(movimientos);
    }

    public void addMovimientotList(Movimiento movimiento){
        movimientoAdapter.add(movimiento);
    }

    private static class MovimientoAdapter extends BaseAdapter {

        private List<Movimiento> movimientos;
        private TextView tvFecha;
        private TextView tvCantidad;
        private TextView tvDescripcion;


        public MovimientoAdapter(List<Movimiento> movimientos) {
            this.movimientos = movimientos;
        }

        protected void replaceData(List<Movimiento> movimientos) {
            this.movimientos = movimientos;
            this.notifyDataSetChanged();
        }

        protected  void add(Movimiento movimiento){
            this.movimientos.add(movimiento);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return movimientos.size();
        }

        @Override
        public Object getItem(int i) {
            return movimientos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.adapter_movimiento, viewGroup, false);
            }
            Movimiento movimiento = movimientos.get(i);

            tvFecha = rowView.findViewById(R.id.tvFecha);
            tvCantidad = rowView.findViewById(R.id.tvCantidad);
            tvDescripcion = rowView.findViewById(R.id.tvDescripcion);

            tvFecha.setText(UtilsDate.format_D_MM_YYYY(movimiento.getFecha()));
            tvCantidad.setText("$" + movimiento.getCantidad());
            tvDescripcion.setText(movimiento.getDescripcion());

            return rowView;
        }
    }

}
