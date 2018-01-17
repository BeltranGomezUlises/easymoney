package com.easymoney.modules.detallePrestamo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Abono;
import com.easymoney.utils.UtilsDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulises on 16/01/2018.
 */


public class AbonoFragment extends Fragment {

    private DetallePrestamoPresenter presenter;
    ListView listView;
    AbonoAdapter abonoAdapter;

    public static AbonoFragment getInstance(DetallePrestamoPresenter presenter){
        AbonoFragment fragment = new AbonoFragment();
        presenter.setAbonoFragment(fragment);
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_abonos, container, false);

        listView = rootView.findViewById(R.id.abonoList);
        abonoAdapter = new AbonoAdapter(new ArrayList<>());
        listView.setAdapter(abonoAdapter);



        return rootView;
    }

    public DetallePrestamoPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(DetallePrestamoPresenter presenter) {
        this.presenter = presenter;
    }

    public void replaceData(List<Abono> abonos){
        abonoAdapter.replaceData(abonos);
    }

    private static class AbonoAdapter extends BaseAdapter{

        private List<Abono> abonos;
        private TextView tvFecha;
        private TextView tvAbono;
        private TextView tvMulta;
        private TextView tvDescripcion;

        public AbonoAdapter(List<Abono> abonos) {
            this.abonos = abonos;
        }

        public void replaceData(List<Abono> abonos){
            this.abonos = abonos;
            this.notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return abonos.size();
        }

        @Override
        public Object getItem(int i) {
            return abonos.get(i);
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
                rowView = inflater.inflate(R.layout.adapter_abono, viewGroup, false);
            }
            Abono abono = abonos.get(i);

            tvFecha = rowView.findViewById(R.id.tvFecha);
            tvAbono = rowView.findViewById(R.id.tvAbono);
            tvMulta = rowView.findViewById(R.id.tvMulta);
            tvDescripcion = rowView.findViewById(R.id.tvDescripcion);

            tvFecha.setText(UtilsDate.format_D_MM_YYYY(abono.getAbonoPK().getFecha()));
            tvAbono.setText("$" + abono.getCantidad());
            tvMulta.setText("$" + abono.getMulta().getMulta());
            tvDescripcion.setText(abono.getMulta().getMultaDes());

            return rowView;
        }
    }

}
