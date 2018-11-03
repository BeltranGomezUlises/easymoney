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
import com.easymoney.utils.baseClases.BaseFragment;

import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class RenovacionFragment extends RenovacionContract.Fragment{


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
                    getPresenter().buscarPrestamoId(prestamoId);
                    return true;
                }
                return false;
            }
        });

        getPresenter().subscribe();
        return root;
    }

    public void showDialogRenovar(Prestamo prestamo) {
        RenovacionDialogFragment renovacionDialogFragment = new RenovacionDialogFragment(getPresenter(), prestamo);
        renovacionDialogFragment.show(getFragmentManager(), "Renovación");
    }

}

