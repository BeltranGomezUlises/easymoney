package com.easymoney.modules.configuracionImpresoras;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
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

    private Object bluetoothMutex = new Object();
    private boolean connected = false;
    private InputStream input;
    private OutputStream output;
    private BluetoothSocket socket;
    private BluetoothAdapter bluetoothAdapter;

    public static BTPrinterDevice getInstance() {
        if (instance == null) instance = new BTPrinterDevice();
        return instance;
    }

    private BTPrinterDevice() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean connectToClient(String macAddress) throws IOException {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);
        bluetoothAdapter.cancelDiscovery();
        synchronized (bluetoothMutex) {
            if (!connected) {
                String error = null;
                connected = false;
                try {
                    socket = device.createRfcommSocketToServiceRecord(SPP_UUID);
                    socket.connect();
                    input = socket.getInputStream();
                    output = socket.getOutputStream();
                    connected = true;
                } catch (IOException e) {
                    Log.e(getClass().getName(), "Error de conexion");
                    error = e.getLocalizedMessage();
                }

                if (!connected && !error.equals("Service discovery failed")) {
                    try {
                        Method method = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                        socket = (BluetoothSocket) method.invoke(device, 1);
                        socket.connect();
                        input = socket.getInputStream();
                        output = socket.getOutputStream();
                        connected = true;
                        error = null;
                    } catch (SecurityException e) {
                    } catch (NoSuchMethodException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e) {
                    } catch (IOException e) {
                        error = e.getLocalizedMessage();
                    }
                }
                if (error != null) throw new IOException(error);
            }
        }
        return connected;
    }

    public boolean disconnectFromClient() {
        synchronized (bluetoothMutex) {
            if (connected) {
                connected = false;
                try {
                    input.close();
                } catch (IOException e) { }
                try {
                    output.close();
                } catch (IOException e) { }
                try {
                    socket.close();
                } catch (IOException e) { }
            }
        }
        return !connected;
    }

    public boolean isConnected() {
        synchronized (bluetoothMutex) {
            return connected;
        }
    }

    public InputStream getInputStream() {
        return input;
    }

    public OutputStream getOutputString() {
        return output;
    }

}