package com.easymoney.modules.renovacion;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.easymoney.R;
import com.easymoney.entities.Cliente;
import com.easymoney.entities.Prestamo;
import com.easymoney.models.services.Status;
import com.easymoney.modules.main.MainFiltroDialogFragment;

import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class RenovacionFragment extends Fragment implements RenovacionContract.Fragment {

    private ProgressDialog dialog;
    private RenovacionPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_renovacion, container, false);

        final EditText edtPrestamoId = root.findViewById(R.id.edtPrestamoId);
        edtPrestamoId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = textView.getText().toString();
                    if (text.isEmpty()) {
                        return true;
                    }
                    int prestamoId = Integer.parseInt(text);
                    presenter.buscarPrestamoId(prestamoId);
                    return true;
                }
                return false;
            }
        });

        this.presenter.subscribe();
        return root;
    }

    @Override
    public void setPresenter(RenovacionContract.Presenter presenter) {
        this.presenter = (RenovacionPresenter) presenter;
    }

    @Override
    public void showLoading(boolean value) {
        if (value) {
            dialog = ProgressDialog.show(getActivity(), "Cargando", "Por favor espere...", true);
        } else {
            if (dialog != null) {
                dialog.cancel();
            }
        }
    }

    @Override
    public void showMessage(String message, Status status) {
        switch (status) {
            case OK:
                Snackbar.make(this.getView(), message, LENGTH_LONG).show();
                break;
            case WARNING:
                Snackbar sbw = Snackbar.make(this.getView(), message, LENGTH_LONG);
                sbw.getView().setBackgroundResource(R.color.warning);
                sbw.show();
                break;
            case ERROR:
                Snackbar sbe = Snackbar.make(this.getView(), message, LENGTH_LONG);
                sbe.getView().setBackgroundColor(Color.RED);
                sbe.show();
                break;
        }
    }

    @Override
    public void showDialogRenovar(Prestamo prestamo) {
        RenovacionDialogFragment renovacionDialogFragment = new RenovacionDialogFragment(presenter, prestamo);
        renovacionDialogFragment.show(getFragmentManager(), "Renovación");
    }

}

