package com.easymoney.modules.configuracionImpresoras;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class BTPrinterDevice {

    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BTPrinterDevice instance;
    private boolean connected = false;
    private InputStream input;
    private OutputStream output;
    private BluetoothSocket socket;
    private BluetoothAdapter bluetoothAdapter;

    private BTPrinterDevice() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BTPrinterDevice getInstance() {
        if (instance == null) {
            instance = new BTPrinterDevice();
        }
        return instance;
    }

    public synchronized boolean connectToClient(String macAddress) throws IOException {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);
        bluetoothAdapter.cancelDiscovery();
        if (!connected) {
            String error = null;
            try {
                socket = device.createRfcommSocketToServiceRecord(SPP_UUID);
                socket.connect();
                input = socket.getInputStream();
                output = socket.getOutputStream();
                connected = true;
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage());
                error = e.getLocalizedMessage();
            }

            if (!connected && !error.equals("Service discovery failed")) {
                try {
                    Method method = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                    socket = (BluetoothSocket) method.invoke(device, 1);
                    socket.connect();
                    input = socket.getInputStream();
                    output = socket.getOutputStream();
                    connected = true;
                    error = null;
                } catch (SecurityException
                        | NoSuchMethodException
                        | IllegalArgumentException
                        | IllegalAccessException
                        | InvocationTargetException e) {
                } catch (IOException e) {
                    error = e.getLocalizedMessage();
                }
            }
            if (error != null) {
                throw new IOException(error);
            }
        }

        return connected;
    }

    public synchronized boolean disconnectFromClient() {
        if (connected) {
            connected = false;
            try {
                input.close();
            } catch (IOException e) {
            }
            try {
                output.close();
            } catch (IOException e) {
            }
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        return !connected;
    }

}