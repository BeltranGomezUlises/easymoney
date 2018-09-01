package com.easymoney.utils.bluetoothPrinterUtilities;

import android.support.annotation.NonNull;

import com.easymoney.models.ModelImpresionAbono;

public class UtilsPrinter {

    /**
     * Mandar a imprimir a impresora ESC/POS por bluetooth configurada con formato siguiente:
     * <p>-------------------</p>
     * <h1>LOGO</h1>
     * <p>-------------------</p>
     * <p># Prestamo: prestamoId</p>
     * <p>Fecha: fechaHoraPrestamo</p>
     * <p>Fecha limite: fechaLimite</p>
     * <p>Cantidad prestamo: $ cantidadPrestamo</p>
     * <p>Cantidad pagar: $ totalAPagar</p>
     * <p></p>
     * <p>Cliente: cliente</p>
     * <p>Cobrador: cobrador</p>
     * <p></p>
     * <h3>Fecha abono: fechaHoraAbono</h3>
     * <h3>Importe abono: $ total</h3>
     * <p></p>
     * <h3>Distribucion de pago</h3>
     * <p>-----------------</p>
     * <p>Abono: $ abono</p>
     * <p>Multa: $ multa</p>
     * <p>Multa post-plazo: $ multaPosPlazo</p>
     * <p>-----------------</p>
     * <h3>Totales</h3>
     * <p>Total abonado: $ totalAbonado</p>
     * <p>Porcentaje pagado: % porcentajeAbonado</p>
     * <p>Total multado: $ totalMultado</p>
     * <p>Total multado post-plazo: $totalMultadoPosPlazo</p>
     *
     * <p>-----------------</p>
     * <h3>Por pagar: $totalParaSaldar</h3>
     * <p>Mensaje de agradecimiento...</p>
     * @param mia modelo con los atributos a imprimir en el recibo
     */
    public static void imprimirRecibo(@NonNull ModelImpresionAbono mia) {
        //TODO: utileria de conexion TPC para impresoras bluetooth
        //TODO: metodo para armar el recibo con el modelo
        //TODO: metodo para mandar a imprimir el arreglo de bytes, buffer, stream, etc...

    }
}
