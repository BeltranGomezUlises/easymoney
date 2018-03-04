package com.easymoney.modules.cambiarContra;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.easymoney.R;
import com.easymoney.entities.Usuario;
import com.easymoney.models.ModelCambiarContra;
import com.easymoney.utils.UtilsPreferences;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by ulises on 15/01/2018.
 */

public class CambiarContraFragment extends Fragment implements CambiarContraContract.View {

    private static CambiarContraFragment instance;
    private CambiarContraPresenter presenter;
    //views
    private EditText tvContraAcutal;
    private EditText tvContraNueva;
    private EditText tvContraNuevaConfirmacion;
    private Button btnCambiarContra;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cambiar_contra, container, false);

        // Set up the cambiarContra form.
        tvContraAcutal = root.findViewById(R.id.tvContraActual);
        tvContraNueva = root.findViewById(R.id.tvNuevaContraseña);
        tvContraNuevaConfirmacion = root.findViewById(R.id.tvNuevaContraseñaConfirmacion);
        tvContraNuevaConfirmacion.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                cambiarContra();
                return true;
            }
            return false;
        });

        Button btnLogin = root.findViewById(R.id.btnCambiarContra);
        btnLogin.setOnClickListener(view -> cambiarContra());

        return root;
    }

    private void cambiarContra() {
        String contraActual = tvContraAcutal.getText().toString();
        String contraNueva = tvContraNueva.getText().toString();
        String contraNuevaConfirmacion = tvContraNuevaConfirmacion.getText().toString();

        tvContraAcutal.setError(null);
        tvContraNueva.setError(null);
        tvContraNuevaConfirmacion.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(contraActual)){
            tvContraAcutal.setError("Campo requerido");
            focusView = tvContraAcutal;
            cancel = true;
        }

        if (TextUtils.isEmpty(contraNueva)){
            tvContraNueva.setError("Campo requerido");
            focusView = tvContraNueva;
            cancel = true;
        }

        if (TextUtils.isEmpty(contraNuevaConfirmacion)){
            tvContraNuevaConfirmacion.setError("Campo requerido");
            focusView = tvContraNuevaConfirmacion;
            cancel = true;
        }
        if (!contraNueva.equals(contraNuevaConfirmacion)){
            tvContraNuevaConfirmacion.setError("Contraseña no coincide");
            focusView = tvContraNuevaConfirmacion;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showLoading(true);
            presenter.cambiarContra(new ModelCambiarContra(UtilsPreferences.loadLogedUser().getId(), contraActual, contraNueva));
        }

    }

    @Override
    public void showLoading(boolean active) {
        if (active){
            dialog = ProgressDialog.show(getActivity(), "Cargando","Por favor espere...", true);
        }else{
            if (dialog != null){
                dialog.cancel();
            }
        }
    }

    public void showMessage(String message){
        Snackbar.make(this.getView(), message, LENGTH_LONG).show();
    }

    @Override
    public void showMain() {

    }

    @Override
    public void setPresenter(CambiarContraContract.Presenter presenter) {
        this.presenter = (CambiarContraPresenter) presenter;
        this.presenter.subscribe();
    }

}
