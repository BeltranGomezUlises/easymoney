package com.easymoney.utils.bluetoothPrinterUtilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.easymoney.R;
import com.easymoney.models.ModelImpresionAbono;
import com.easymoney.modules.configuracionImpresoras.Bixolon.PrinterControl.BixolonPrinter;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.activities.Funcion;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.graphics.ZebraImageFactory;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

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
     * <p>
     * <p>-----------------</p>
     * <h3>Por pagar: $totalParaSaldar</h3>
     * <p>Mensaje de agradecimiento...</p>
     *
     * @param mia modelo con los atributos a imprimir en el recibo
     */
    public static void imprimirRecibo(final ModelImpresionAbono mia, final Funcion<Throwable> onError) {
        //validaciones
        String macAddress = UtilsPreferences.loadMacPrinter();
        if (macAddress == null || macAddress.isEmpty()) {
            onError.accept(new Exception("No hay impresora configurada"));
            return;
        }
        String modeloImpresora = UtilsPreferences.loadPrinterModel();
        if (modeloImpresora == null || modeloImpresora.equals("")) {
            onError.accept(new Exception("No hay impresora configurada"));
            return;
        }
        //mandar a imprimir
        switch (modeloImpresora) {
            case "Bixolon R200III":
                imprimirBixolon(macAddress, onError);
                break;
            case "Zebra 220":
                imprimirZebra(mia, macAddress, onError);
                break;
        }

    }

    private static void imprimirBixolon(String macAddress, Funcion<Throwable> onError) {
        try {
            BixolonPrinter bxlPrinter = SingletonPrinterConnection.getBxlInstance(macAddress);
            int width = bxlPrinter.getPrinterMaxWidth();
            String imageUri = Environment.getExternalStorageDirectory() + "/" + "easy.jpg";
            bxlPrinter.printImage(imageUri, width, 2, 50);

            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("Prestamo: 12453\n", 0, 0, 1);
            bxlPrinter.printText("Fecha: 20/noviembre/2018\n", 0, 0, 1);
            bxlPrinter.printText("Fecha limite: 20/noviembre/2018\n", 0, 0, 1);
            bxlPrinter.printText("Cantidad prestamo: $5000\n", 0, 0, 1);
            bxlPrinter.printText("Cantidad a pagar: $5000\n", 0, 0, 1);
            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("Cliente:\n", 0, 0, 1);
            bxlPrinter.printText("Jorge Fernando Barraza Zambada (Desarrollador):\n", 0, 0, 1);
            bxlPrinter.printText("Cobrador: Admin\n", 0, 0, 1);
            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("Fecha Abono: 20/noviembre/2018\n", 0, 8, 1);
            bxlPrinter.printText("Importe abono: $3000\n", 0, 8, 1);
            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("Distribuacion pago\n", 0, 8, 1);
            bxlPrinter.printText("--------------------------------\n", 0, 8, 1);
            bxlPrinter.printText("Abono:                     $500\n", 0, 1, 1);
            bxlPrinter.printText("Multa:                     $500\n", 0, 1, 1);
            bxlPrinter.printText("Multa post-plazo:          $500\n", 0, 1, 1);
            bxlPrinter.printText("--------------------------------\n", 0, 8, 1);
            bxlPrinter.printText("Totales\n", 0, 8, 1);
            bxlPrinter.printText("Total abonado:             $500\n", 0, 1, 1);
            bxlPrinter.printText("Porcentaje pagado:         %500\n", 0, 1, 1);
            bxlPrinter.printText("Total multado:             $500\n", 0, 1, 1);
            bxlPrinter.printText("Total multado post-plazo   $500\n", 0, 1, 1);
            bxlPrinter.printText("--------------------------------\n", 0, 8, 1);
            bxlPrinter.printText("Por pagar:                 $500\n", 0, 8, 1);
            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("Gracias por su interes en saldar su cuenta a tiempo en EasyMoney\n", 0, 0, 1);
            bxlPrinter.printText("\n", 0, 0, 1);
            bxlPrinter.printText("\n", 0, 0, 1);

            Thread.sleep(3000);
            bxlPrinter.printerClose();
        } catch (Exception e) {
            onError.accept(e);
            e.printStackTrace();
        }
    }

    /**
     * Metodo para imprimir el ticket en formato CPCL
     *
     * @param mia        objecto con los datos del recibo
     * @param macAddress direccion MAC de la impresora*
     */
    private static void imprimirZebra(final ModelImpresionAbono mia, final String macAddress,
                                      final Funcion<Throwable> onError) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    int longitudNombre = mia.getCliente().length();
                    String nombreRenglon1 = "";
                    String nombreRenglon2 = "";
                    if (longitudNombre > 30) {
                        nombreRenglon1 = mia.getCliente().substring(0, 30);
                        nombreRenglon2 = mia.getCliente().substring(30, longitudNombre);
                    } else {
                        nombreRenglon1 = mia.getCliente();
                    }
                    int totalImporteAbono = mia.getAbono() + mia.getMulta() + mia.getMultaPosPlazo();

                    String cpclData = "! 0 200 200 1453 1\r\n" +
                            //"PCX 95 53 !<EASY.PCX\r\n" +
                            "T 5 1 4 1198 Por pagar: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 1 4 1198 $" + mia.getTotalParaSaldar() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 1 6 898 Totales\r\n" +
                            "T 5 1 5 637 Distribucion de pago\r\n" +
                            "T 5 1 7 538 Importe abono: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 1 7 538 $" + totalImporteAbono + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 1 6 477 Fecha abono: " + mia.getFechaHoraAbono() + "\r\n" +
                            "T 5 0 6 434 Cobrador: " + mia.getCobrador() + "\r\n" +
                            "ML 32\r\n" +
                            "T 5 0 8 340 Cliente: \r\n" +
                            "" + nombreRenglon1 + "\r\n" +
                            "" + nombreRenglon2 + "\r\n" +
                            "ENDML\r\n" +
                            "T 5 0 4 241 Cantidad a pagar: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 4 241 $" + mia.getTotalAPagar() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 4 194 Cantidad prestamo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 4 194 $" + mia.getCantidadPrestamo() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 2 150 Fecha limite: " + mia.getFechaLimite() + "\r\n" +
                            "T 5 0 4 109 Fecha: " + mia.getFechaHoraPrestamo() + "\r\n" +
                            "T 5 0 7 812 Multa post-plazo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 7 812 $" + mia.getMultaPosPlazo() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 8 781 Multa: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 8 781 $" + mia.getMulta() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 3 1039 Total multado post-plazo: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 3 1039 $" + mia.getTotalMultadoPosPlazo() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 6 1119 Total multado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 6 1119 $" + mia.getTotalMultado() + "\r\n" +
                            "LEFT\r\n" +
                            "ML 36\r\n" +
                            "T 5 0 5 1282 Gracias por su interes en saldar\r\n" +
                            "su cuenta a tiempo en EasyMoney\r\n" +
                            "ENDML\r\n" +
                            "T 5 0 7 1004 Porcentaje pagado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 6 1004 " + mia.getPorcentajeAbonado() + "%\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 5 967 Total abonado: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 5 967 $" + mia.getTotalAbonado() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 8 748 Abono: \r\n" +
                            "RIGHT\r\n" +
                            "T 5 0 8 748 $" + mia.getAbono() + "\r\n" +
                            "LEFT\r\n" +
                            "T 5 0 3 69 Prestamo: " + mia.getPrestamoId() + "\r\n" +
                            "T 4 0 3 1144 --------------------------\r\n" +
                            "T 4 0 0 841 --------------------------\r\n" +
                            "T 4 0 4 690 --------------------------\r\n"
                            + "PRINT\r\n";

                    Connection con = SingletonPrinterConnection.getZebraConnection(macAddress);
//                    int x = 95, y = 53;
                    Bitmap bm = SingletonPrinterConnection.getImageBitmap();
                    ZebraPrinter printer = ZebraPrinterFactory.getInstance(PrinterLanguage.CPCL, con);
                    con.write("! U1 setvar \"device.languages\" \"zpl\"\r\n! U1 JOURNAL\r\n! U1 SETFF 20 2\r\n".getBytes());
                    printer.printImage(ZebraImageFactory.getImage(bm), 0, 0, bm.getWidth(), bm.getHeight(), false);
                    con.write(cpclData.getBytes());

                    //CONEXION POR CLASES GENERICAS
                    /*BTPrinterDevice.getInstance().connectToClient(macAddress);
                    BTPrinterDevice.getInstance().getInputStream();
                    OutputStream output = BTPrinterDevice.getInstance().getOutputString();
                    output.write(cpclData.getBytes());
                    output.flush();
                    Thread.sleep(2000);
                    BTPrinterDevice.getInstance().disconnectFromClient();*/

                } catch (Exception e) {
                    onError.accept(e);
                }
            }
        }).start();
    }

}
