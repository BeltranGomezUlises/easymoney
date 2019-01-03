package com.easymoney.modules.renovacion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteCardHolder> {

    private RenovacionContract.Presenter presenter;
    private List<Cliente> clientes;

    ClienteAdapter(RenovacionContract.Presenter presenter, List<Cliente> clientes) {
        this.presenter = presenter;
        this.clientes = clientes;
    }

    @Override
    public ClienteCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cliente, null);
        return new ClienteCardHolder(v, presenter);
    }

    @Override
    public void onBindViewHolder(ClienteCardHolder holder, int position) {
        holder.setCliente(clientes.get(position));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        this.notifyDataSetChanged();
    }

    public static class ClienteCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RenovacionContract.Presenter presenter;
        private final ImageView img;
        private final TextView txtNombre;
        private final TextView txtApodo;
        private final TextView txtTelefono;
        private final TextView txtDireccion;
        private Cliente cliente;

        ClienteCardHolder(View itemView, RenovacionContract.Presenter presenter) {
            super(itemView);
            this.presenter = presenter;

            img = itemView.findViewById(R.id.imageView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtApodo = itemView.findViewById(R.id.txtApodo);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            presenter.cargarPrestamos(cliente);
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
            txtNombre.setText(cliente.getNombre());
            txtApodo.setText(cliente.getApodo());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
        }

    }

}
