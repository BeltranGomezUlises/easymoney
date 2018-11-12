package com.easymoney.utils.bluetoothPrinterUtilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.easymoney.R;
import com.easymoney.models.ModelImpresionAbono;
import com.easymoney.modules.configuracionImpresoras.Bixolon.PrinterControl.BixolonPrinter;
import com.easymoney.utils.UtilsPreferences;
import com.easymoney.utils.activities.Funcion;
import com.google.common.base.Strings;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.graphics.ZebraImageFactory;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

public class UtilsPrinter {

    public static final String MODELO_BIXOLONR200 = "SPP-R200III";
    public static final String MODELO_ZEBRA220 = "Zebra 220";

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
        if(modeloImpresora != null){
            switch (modeloImpresora){
                case MODELO_BIXOLONR200 :
                    imprimirBixolon(macAddress,mia,onError);
                    break;
                case MODELO_ZEBRA220:
                    imprimirZebra(mia,macAddress,onError);
                    break;
            }
        }

    }

    /**
     * Metodo para imprimir el recibo en la impresora BIXOLON
     * @param macAddress mac address de la impresora.
     * @param mia modelo de impresion de abono.
     */
    private static void imprimirBixolon(String macAddress,ModelImpresionAbono mia,Funcion<Throwable> onError){
        try {
            BixolonPrinter bxlPrinter = SingletonPrinterConnection.getBxlInstance(macAddress);
            //Caben 32 caracteres total tamaño 1 fondo normal
            int totalImporteAbono = mia.getAbono() + mia.getMulta() + mia.getMultaPosPlazo();
            int width = bxlPrinter.getPrinterMaxWidth();
            String imageUri = Environment.getExternalStorageDirectory() + "/" + "easy.jpg";
            bxlPrinter.printImage(imageUri,width,2,50);
            bxlPrinter.printText("\n",0,0,1);
            bxlPrinter.printText("\n",0,0,1);
            bxlPrinter.printText(aligmentText("Prestamo: ",String.valueOf(mia.getPrestamoId()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Fecha: ",mia.getFechaHoraPrestamo())+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Fecha limite: ",mia.getFechaLimite())+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Cantidad prestamo: ","$"+String.valueOf(mia.getCantidadPrestamo()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Cantidad a pagar: ","$"+String.valueOf(mia.getTotalAPagar()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Cliente: ",mia.getCliente())+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Cobrador: ",mia.getCobrador())+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Fecha abono: ",mia.getFechaHoraAbono())+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText(aligmentText("Importe abono: ","$"+String.valueOf(totalImporteAbono))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Distribució pago: ","")+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText(aligmentText("--------------------------------","")+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText(aligmentText("Abono: ","$"+String.valueOf(mia.getAbono()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Multa: ","$"+String.valueOf(mia.getMulta()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Multa pos-plazo: ","$"+String.valueOf(mia.getMultaPosPlazo()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("--------------------------------","")+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText(aligmentText("Totales: ","")+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText(aligmentText("Total abonado: ","$"+String.valueOf(mia.getTotalAbonado()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Porcentaje pagado: ",String.valueOf(mia.getPorcentajeAbonado())+"%")+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Total multado: ","$"+String.valueOf(mia.getTotalMultado()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("Total multado post-plazo: ","$"+String.valueOf(mia.getTotalMultadoPosPlazo()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText(aligmentText("--------------------------------","")+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_BOLD,1);
            bxlPrinter.printText(aligmentText("Por pagar: ","$"+String.valueOf(mia.getTotalParaSaldar()))+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("Gracias por su interes en saldar su cuenta a tiempo en EasyMoney"+"\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
            bxlPrinter.printText("\n",BixolonPrinter.ALIGNMENT_LEFT,BixolonPrinter.ATTRIBUTE_NORMAL,1);
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

    /**
     * Metodo para imprimir en una sola linea el texto de izquierda + el texto de la derecha separado
     * por espacios.
     * @param leftString String lado izquierdo
     * @param rightString String lado derecho
     * @return
     */
    public static String aligmentText(String leftString, String rightString){
        int sizeLeft = leftString.length();
        int sizeRight = rightString.length();
        int dif = 32 - (sizeLeft + sizeRight);
        if(dif > 0){
            return leftString + Strings.repeat(" ",dif) + rightString;
        }
        return leftString + rightString;
    }

}
