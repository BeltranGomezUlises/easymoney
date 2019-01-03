/*
 * Copyright (C) 2017 Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ub.easymoney.models.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * modelo de mensaje para proveedor de SMS
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
public class ModelSMSRecuperacionContra {

    private String mensaje;
    private String tipo;
    private String destino;
    private String envia;
    private String usuarioId;

    public ModelSMSRecuperacionContra() {
        this.tipo = "SMS";
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEnvia() {
        return envia;
    }

    public void setEnvia(String envia) {
        this.envia = envia;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public static class ModelSMSRescuePassResponse extends ModelSMSRecuperacionContra {

        @JsonProperty("__v")
        private int v;
        @JsonProperty("_id")
        private String id;
        private Date fechaEnviar;

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Date getFechaEnviar() {
            return fechaEnviar;
        }

        public void setFechaEnviar(Date fechaEnviar) {
            this.fechaEnviar = fechaEnviar;
        }

    }

}
