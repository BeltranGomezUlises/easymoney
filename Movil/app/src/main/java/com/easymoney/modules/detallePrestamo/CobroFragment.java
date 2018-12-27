package com.easymoney.modules.detallePrestamo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Cobro;
import com.easymoney.utils.UtilsDate;
import com.easymoney.utils.baseClases.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class CobroFragment extends BaseFragment {

    private CobroAdapter cobroAdapter  = new CobroAdapter (new ArrayList<Cobro>());

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_prestamo_cobros, container, false);
        ListView listView = rootView.findViewById(R.id.cobroList);
        listView.setAdapter(cobroAdapter);
        return rootView;
    }

    public void replaceData(List<Cobro> cobros) {
        cobroAdapter.replaceData(cobros);
    }

    private static class CobroAdapter extends BaseAdapter {

        private List<Cobro> cobros;
        private TextView tvFechaHora;
        private TextView tvCantidad;
        private TextView tvCobrador;

        CobroAdapter(List<Cobro> abonos) {
            this.cobros = abonos;
        }

        void replaceData(List<Cobro> cobros) {
            this.cobros = cobros;
            this.notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return cobros.size();
        }

        @Override
        public Object getItem(int i) {
            return cobros.get(i);
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
                rowView = inflater.inflate(R.layout.adapter_cobro, viewGroup, false);
            }
            Cobro cobro = cobros.get(i);

            tvFechaHora = rowView.findViewById(R.id.tvFechaHora);
            tvCantidad = rowView.findViewById(R.id.tvCantidad);
            tvCobrador = rowView.findViewById(R.id.tvCobrador);

            tvFechaHora.setText(UtilsDate.format_D_MM_YYYY_HH_MM(cobro.getFecha()));
            tvCantidad.setText("$" + cobro.getCantidad());
            tvCobrador.setText(cobro.getCobrador().getNombre());

            return rowView;
        }
    }

}
