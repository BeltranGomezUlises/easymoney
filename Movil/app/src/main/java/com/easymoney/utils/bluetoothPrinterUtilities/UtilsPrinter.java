package com.easymoney.utils.bluetoothPrinterUtilities;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.easymoney.models.ModelImpresionAbono;
import com.easymoney.modules.configuracionImpresoras.BTPrinterDevice;
import com.easymoney.utils.UtilsPreferences;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;

import java.io.OutputStream;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class UtilsPrinter {

    private static boolean imprimio = true;

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
    public static boolean imprimirRecibo(@NonNull final ModelImpresionAbono mia,final String macAddress) {
        //TODO: utileria de conexion TPC para impresoras bluetooth
        //TODO: metodo para armar el recibo con el modelo
        //TODO: metodo para mandar a imprimir el arreglo de bytes, buffer, stream, etc...
        new Thread(new Runnable() {
            public void run() {
                try {
                    // This example prints "This is a CPCL test." near the top of the label.
                    /*String cpclData = "! 0 200 200 50 1\r\n"
                            + "TEXT 4 0 30 40 This is a CPCL test.\r\n"
                            + "FORM\r\n"
                            + "PRINT\r\n";*/

                    int tamañoNombre = mia.getCliente().length();
                    String nombreRenglon1 = "";
                    String nombreRenglon2 = "";
                    if(tamañoNombre > 30){
                        nombreRenglon1 = mia.getCliente().substring(0,30);
                        nombreRenglon2 = mia.getCliente().substring(31,tamañoNombre-1);
                    }else{
                        nombreRenglon1 = mia.getCliente().substring(0,tamañoNombre-1);
                    }

                    String cpclData = "! 0 200 200 1523 1\r\n" +
                            "PCX 95 53 !<EASY.PCX\r\n" +
                            "T 5 1 4 1328 Por pagar: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 1 4 1328 $"+mia.getTotalParaSaldar()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 1 6 1028 Totales\r\n" +
                            "T 5 1 5 767 Distribucion de pago\r\n" +
                            "T 5 1 7 668 Importe abono: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 1 7 668 $"+mia.getTotalAbonado()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 1 6 607 Fecha abono: "+mia.getFechaHoraAbono()+"\r\n" +
                            "T 5 0 6 564 Cobrador: "+mia.getCobrador()+"\r\n" +
                            "ML 36\r\n" +
                            "T 5 0 8 470 Cliente: \r\n" +
                            ""+nombreRenglon1+"\r\n" +
                            ""+nombreRenglon2+"\r\n" +
                            "ENDML\r\n" +
                            "T 5 0 4 371 Cantidad a pagar: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 4 371 $"+mia.getTotalAPagar()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 4 324 Cantidad prestamo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 4 324 $"+mia.getCantidadPrestamo()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 2 280 Fecha limite: "+mia.getFechaLimite()+"\r\n" +
                            "T 5 0 4 239 Fecha: "+mia.getFechaHoraPrestamo()+"\r\n" +
                            "T 5 0 7 942 Multa post-plazo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 7 942 $"+mia.getMultaPosPlazo()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 8 911 Multa: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 8 911 $"+mia.getMulta()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 3 1208 Total multado post-plazo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 3 1208 $"+mia.getTotalMultadoPosPlazo()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 6 1169 Total multado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 6 1169 $"+mia.getTotalMultado()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 5 1412 --- Mensaje de agradecimiento ---\r\n" +
                            "T 5 0 7 1134 Porcentaje pagado: "+mia.getPorcentajeAbonado()+"%\r\n" +
                            "T 5 0 5 1097 Total abonado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 5 1097 $"+mia.getTotalAbonado()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 8 878 Abono: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 8 878 $"+mia.getAbono()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 3 199 Prestamo: "+mia.getPrestamoId()+"\r\n" +
                            "T 4 0 3 1274 --------------------------\r\n" +
                            "T 4 0 0 971 --------------------------\r\n" +
                            "T 4 0 4 820 --------------------------\r\n"
                            + "PRINT\r\n";
                    BTPrinterDevice.getInstance().connectToClient(macAddress);
                    BTPrinterDevice.getInstance().getInputStream();
                    OutputStream output = BTPrinterDevice.getInstance().getOutputString();
                    output.write(cpclData.getBytes());
                    output.flush();
                    Thread.sleep(2000);
                    BTPrinterDevice.getInstance().disconnectFromClient();

                } catch (Exception e) {
                    // Handle communications error here.
                    e.printStackTrace();
                    imprimio = false;
                }
            }
        }).start();
        return imprimio;
    }

}
