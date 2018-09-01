package com.easymoney.models;

import com.easymoney.utils.bluetoothPrinterUtilities.UtilsPrinter;

public class ModelImpresionAbono {

    //identificadores
    final private int prestamoId;
    final private String cobrador;
    final private String cliente;

    //fechas
    final private String fechaHoraAbono;
    final private String fechaHoraPrestamo;
    final private String fechaLimite;

    //cantidades
    final private int abono;
    final private int multa;
    final private int multaPosPlazo;

    final private int cantidadPrestamo;
    final private int totalAPagar;
    final private int totalAbonado;
    final private int totalMultado;
    final private int totalMultadoPosPlazo;
    final private int totalParaSaldar;
    final private float porcentajeAbonado;

    public ModelImpresionAbono(int prestamoId, String cobrador, String cliente,
                               String fechaHoraAbono, String fechaHoraPrestamo,
                               String fechaLimite, int abono, int multa, int multaPosPlazo,
                               int cantidadPrestamo, int totalAPagar, int totalAbonado,
                               int totalMultado, int totalMultadoPosPlazo,
                               int totalParaSaldar, float porcentajeAbonado) {
        this.prestamoId = prestamoId;
        this.cobrador = cobrador;
        this.cliente = cliente;
        this.fechaHoraAbono = fechaHoraAbono;
        this.fechaHoraPrestamo = fechaHoraPrestamo;
        this.fechaLimite = fechaLimite;
        this.abono = abono;
        this.multa = multa;
        this.multaPosPlazo = multaPosPlazo;
        this.cantidadPrestamo = cantidadPrestamo;
        this.totalAPagar = totalAPagar;
        this.totalAbonado = totalAbonado;
        this.totalMultado = totalMultado;
        this.totalMultadoPosPlazo = totalMultadoPosPlazo;
        this.totalParaSaldar = totalParaSaldar;
        this.porcentajeAbonado = porcentajeAbonado;
    }

    public int getPrestamoId() {
        return prestamoId;
    }

    public String getCobrador() {
        return cobrador;
    }

    public String getCliente() {
        return cliente;
    }

    public String getFechaHoraAbono() {
        return fechaHoraAbono;
    }

    public String getFechaHoraPrestamo() {
        return fechaHoraPrestamo;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public int getAbono() {
        return abono;
    }

    public int getMulta() {
        return multa;
    }

    public int getMultaPosPlazo() {
        return multaPosPlazo;
    }

    public int getCantidadPrestamo() {
        return cantidadPrestamo;
    }

    public int getTotalAPagar() {
        return totalAPagar;
    }

    public int getTotalAbonado() {
        return totalAbonado;
    }

    public int getTotalMultado() {
        return totalMultado;
    }

    public int getTotalMultadoPosPlazo() {
        return totalMultadoPosPlazo;
    }

    public int getTotalParaSaldar() {
        return totalParaSaldar;
    }

    public float getPorcentajeAbonado() {
        return porcentajeAbonado;
    }
}
