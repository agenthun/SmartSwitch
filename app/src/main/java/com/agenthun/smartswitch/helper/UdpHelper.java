package com.agenthun.smartswitch.helper;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2017/1/5 22:33.
 */

public class UdpHelper {
    private static final String TAG = "UdpHelper";

    private byte[] buffer = new byte[1024];
    private DatagramSocket socket = null;

    private static UdpHelper mUdpHelper = new UdpHelper();

    public static UdpHelper getInstance() {
        return mUdpHelper;
    }

    private UdpHelper() {
        try {
            socket = new DatagramSocket();
            setDefaultConfig();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private UdpHelper(DatagramSocket socket) {
        this.socket = socket;
    }

    private void setDefaultConfig() {
        try {
            this.socket.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setBroadcast(boolean on) {
        try {
            socket.setBroadcast(on);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(byte[] data, String ip, int port) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String receiveHexString() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        Log.d(TAG, "getAddress() returned: " + packet.getAddress());
        Log.d(TAG, "getPort() returned: " + packet.getPort());
        String res = byteToHexString(packet.getData(), packet.getLength());
        return res;
    }

    public synchronized byte[] receiveBytes() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[1];
        }
        Log.d(TAG, "getAddress() returned: " + packet.getAddress());
        Log.d(TAG, "getPort() returned: " + packet.getPort());
        return packet.getData();
    }

    public void close() {
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }

    private String byteToHexString(byte[] data, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            byte b = data[i];
            if ((b & 0xff) <= 0x0f) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(b & 0xff) + " ");
        }
        return sb.toString();
    }
}
