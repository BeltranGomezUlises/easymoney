package com.easymoney.models;

/**
 * Created by ulises on 15/01/2018.
 */
public class ModelCambiarContra {
    private int usuarioId;
    private String contraActual;
    private String nuevaContra;

    public ModelCambiarContra() {
    }

    public ModelCambiarContra(int usuarioId, String contraActual, String nuevaContra) {
        this.usuarioId = usuarioId;
        this.contraActual = contraActual;
        this.nuevaContra = nuevaContra;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getContraActual() {
        return contraActual;
    }

    public void setContraActual(String contraActual) {
        this.contraActual = contraActual;
    }

    public String getNuevaContra() {
        return nuevaContra;
    }

    public void setNuevaContra(String nuevaContra) {
        this.nuevaContra = nuevaContra;
    }
}
