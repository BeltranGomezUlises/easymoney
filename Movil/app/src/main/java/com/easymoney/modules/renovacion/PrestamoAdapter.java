package com.easymoney.modules.renovacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Prestamo;
import com.easymoney.utils.UtilsDate;

import java.util.List;

public class PrestamoAdapter extends RecyclerView.Adapter<PrestamoAdapter.PrestamoCardHolder> {

    private RenovacionContract.Presenter presenter;
    private List<Prestamo> prestamos;

    PrestamoAdapter(RenovacionContract.Presenter presenter, List<Prestamo> prestamos) {
        this.presenter = presenter;
        this.prestamos = prestamos;
    }

    @Override
    public PrestamoCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_prestamo, null);
        return new PrestamoCardHolder(v, presenter);
    }

    @Override
    public void onBindViewHolder(PrestamoCardHolder holder, int position) {
        holder.setPrestamo(prestamos.get(position));
    }

    @Override
    public int getItemCount() {
        return prestamos.size();
    }

    public static class PrestamoCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RenovacionContract.Presenter presenter;
        private final TextView txtNombre;
        private final TextView txtApodo;
        private final TextView txtTelefono;
        private final TextView txtDireccion;
        private final TextView txtFecha;
        private final TextView txtCantidad;
        private Prestamo prestamo;

        PrestamoCardHolder(View itemView, RenovacionContract.Presenter presenter) {
            super(itemView);
            this.presenter = presenter;

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtApodo = itemView.findViewById(R.id.txtApodo);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtFecha = itemView.findViewById(R.id.txtFecha);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            presenter.detallePrestamo(prestamo);
        }

        public void setPrestamo(Prestamo prestamo) {
            this.prestamo = prestamo;
            txtNombre.setText(prestamo.getCliente().getNombre());
            txtApodo.setText(prestamo.getCliente().getApodo());
            txtDireccion.setText(prestamo.getCliente().getDireccion());
            txtTelefono.setText(prestamo.getCliente().getTelefono());
            txtCantidad.setText("$" + prestamo.getCantidad());
            txtFecha.setText(UtilsDate.format_D_MM_YYYY(prestamo.getFecha()));
        }

    }

}
