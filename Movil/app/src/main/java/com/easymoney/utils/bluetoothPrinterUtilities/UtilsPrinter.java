package com.easymoney.utils.bluetoothPrinterUtilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.widget.Toast;

import com.easymoney.R;
import com.easymoney.models.ModelImpresionAbono;
import com.easymoney.modules.configuracionImpresoras.BTPrinterDevice;
import com.easymoney.utils.UtilsPreferences;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.BluetoothConnectionInsecure;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.graphics.ZebraImageFactory;
import com.zebra.sdk.graphics.ZebraImageI;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

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
    public static boolean imprimirRecibo(@NonNull final ModelImpresionAbono mia, final String macAddress, final Context context) {
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
                        nombreRenglon2 = mia.getCliente().substring(30,tamañoNombre);
                    }else{
                        nombreRenglon1 = mia.getCliente().substring(0,tamañoNombre);
                    }
                    int totalImporteAbono = mia.getAbono() + mia.getMulta() + mia.getMultaPosPlazo();

                    String cpclData = "! 0 200 200 1533 1\r\n" +
                            //"PCX 95 53 !<EASY.PCX\r\n" +
                            "T 5 1 4 1278 Por pagar: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 1 4 1278 $"+mia.getTotalParaSaldar()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 1 6 978 Totales\r\n" +
                            "T 5 1 5 717 Distribucion de pago\r\n" +
                            "T 5 1 7 618 Importe abono: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 1 7 618 $"+totalImporteAbono+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 1 6 557 Fecha abono: "+mia.getFechaHoraAbono()+"\r\n" +
                            "T 5 0 6 514 Cobrador: "+mia.getCobrador()+"\r\n" +
                            "ML 32\r\n" +
                            "T 5 0 8 420 Cliente: \r\n" +
                            ""+nombreRenglon1+"\r\n" +
                            ""+nombreRenglon2+"\r\n" +
                            "ENDML\r\n" +
                            "T 5 0 4 321 Cantidad a pagar: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 4 321 $"+mia.getTotalAPagar()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 4 274 Cantidad prestamo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 4 274 $"+mia.getCantidadPrestamo()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 2 230 Fecha limite: "+mia.getFechaLimite()+"\r\n" +
                            "T 5 0 4 189 Fecha: "+mia.getFechaHoraPrestamo()+"\r\n" +
                            "T 5 0 7 892 Multa post-plazo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 7 892 $"+mia.getMultaPosPlazo()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 8 861 Multa: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 8 861 $"+mia.getMulta()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 3 1158 Total multado post-plazo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 3 1158 $"+mia.getTotalMultadoPosPlazo()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 6 1119 Total multado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 6 1119 $"+mia.getTotalMultado()+"\r\n" +
                            "LEFT\r\n" +
                            "ML 36\r\n" +
                            "T 5 0 5 1362 Gracias por su interes en saldar\r\n" +
                            "su cuenta a tiempo en EasyMoney\r\n" +
                            "ENDML\r\n" +
                            "T 5 0 7 1084 Porcentaje pagado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 6 1084 "+mia.getPorcentajeAbonado()+"%\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 5 1047 Total abonado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 5 1047 $"+mia.getTotalAbonado()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 8 828 Abono: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 8 828 $"+mia.getAbono()+"\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 3 149 Prestamo: "+mia.getPrestamoId()+"\r\n" +
                            "T 4 0 3 1224 --------------------------\r\n" +
                            "T 4 0 0 921 --------------------------\r\n" +
                            "T 4 0 4 770 --------------------------\r\n"
                            + "PRINT\r\n";

                    Connection thePrinterConn = new BluetoothConnection(macAddress);
                    thePrinterConn.open();
                    int x = 95;
                    int y = 53;
                    ZebraPrinter zebra = ZebraPrinterFactory.getInstance(thePrinterConn);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.easy, options);
                    com.zebra.sdk.printer.ZebraPrinter printer = ZebraPrinterFactory.getInstance(PrinterLanguage.CPCL,thePrinterConn);
                    thePrinterConn.write("! U1 setvar \"device.languages\" \"zpl\"\r\n! U1 JOURNAL\r\n! U1 SETFF 20 2\r\n".getBytes());
                    printer.printImage(ZebraImageFactory.getImage(bm), 0, 0, bm.getWidth(), bm.getHeight(), false);
                    thePrinterConn.write(cpclData.getBytes());
                    Thread.sleep(500);
                    thePrinterConn.close();

                    //CONEXION POR CLASES GENERICAS
                    /*BTPrinterDevice.getInstance().connectToClient(macAddress);
                    BTPrinterDevice.getInstance().getInputStream();
                    OutputStream output = BTPrinterDevice.getInstance().getOutputString();
                    output.write(cpclData.getBytes());
                    output.flush();
                    Thread.sleep(2000);
                    BTPrinterDevice.getInstance().disconnectFromClient();*/

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
