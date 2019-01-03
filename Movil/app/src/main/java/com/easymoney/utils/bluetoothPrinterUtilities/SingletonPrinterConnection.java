package com.easymoney.utils.bluetoothPrinterUtilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.easymoney.R;
import com.easymoney.modules.configuracionImpresoras.Bixolon.PrinterControl.BixolonPrinter;
import com.easymoney.utils.UtilsPreferences;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;

public class SingletonPrinterConnection {

    private static BixolonPrinter bxlPrinter;

    private static Connection btConnection;
    private static Bitmap bm;

    public static BixolonPrinter getBxlInstance(String macAddress) {
        if (bxlPrinter == null) {
            bxlPrinter = new BixolonPrinter(UtilsPreferences.getContext());
            bxlPrinter.printerOpen(0, UtilsPrinter.MODELO_BIXOLONR200, macAddress, true);
        }
        if (!bxlPrinter.printerIsReady()) {
            bxlPrinter.reanimate(UtilsPrinter.MODELO_BIXOLONR200, true);
        }
        return bxlPrinter;
    }

    public static Connection getZebraConnection(String macAddress) throws ConnectionException {
        if (btConnection == null) {
            btConnection = new BluetoothConnection(macAddress);
        }
        if (!btConnection.isConnected()) {
            btConnection.open();
        }
        return btConnection;
    }

    public static Bitmap getImageBitmap() {
        if (bm == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            bm = BitmapFactory.decodeResource(UtilsPreferences.getContext().getResources(), R.drawable.easy, options);
        }
        return bm;
    }
}
