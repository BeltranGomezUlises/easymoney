package com.easymoney.modules.renovacion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easymoney.R;
import com.easymoney.entities.Cliente;
import com.easymoney.entities.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class RenovacionFragment extends RenovacionContract.Fragment {

    private ClienteAdapter adapter;
    private RecyclerView rv;
    private SearchView sv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_renovacion, container, false);

        rv = rootView.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        adapter = new ClienteAdapter(getPresenter(), new ArrayList<Cliente>());
        rv.setAdapter(adapter);

        sv = rootView.findViewById(R.id.search_view);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.setIconified(false);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getPresenter().filtrar(s);
                return false;
            }
        });

        getPresenter().subscribe();
        return rootView;
    }

    @Override
    void setClientes(List<Cliente> clientes) {
        adapter.setClientes(clientes);
    }

    @Override
    public void mostrarPrestamos(List<Prestamo> prestamos) {
        PrestamoAdapter adapter = new PrestamoAdapter(getPresenter(), prestamos);
        rv.setAdapter(adapter);
        sv.setVisibility(View.GONE);
        this.showMessage("Seleccione un prestamo");
    }

}

