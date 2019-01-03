package com.easymoney.modules.ingresosEgresos;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by ulises on 03/03/2018.
 */

@SuppressLint("ValidFragment")
public class IngresosEgresosFragment extends IngresosEgresosContract.Fragment {

    private MovimientoAdapter movimientoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ingresos_egresos, container, false);

        ListView listView = root.findViewById(R.id.lista_movimientos);
        movimientoAdapter = new MovimientoAdapter(new ArrayList<Movimiento>());
        listView.setAdapter(movimientoAdapter);

        getPresenter().subscribe();

        return root;
    }

    public void replaceMovimientoList(List<Movimiento> movimientos) {
        movimientoAdapter.replaceData(movimientos);
    }

    public void addMovimientotList(Movimiento movimiento) {
        movimientoAdapter.add(movimiento);
    }

    private static class MovimientoAdapter extends BaseAdapter {

        private List<Movimiento> movimientos;
        private TextView tvFecha;
        private TextView tvCantidad;
        private TextView tvDescripcion;


        MovimientoAdapter(List<Movimiento> movimientos) {
            this.movimientos = movimientos;
        }

        void replaceData(List<Movimiento> movimientos) {
            this.movimientos = movimientos;
            this.notifyDataSetChanged();
        }

        protected void add(Movimiento movimiento) {
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
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
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
