package com.agenthun.smartswitch.adddevice;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.helper.UdpHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AddDeviceActivity extends AppCompatActivity {

    private static final String TAG = "AddDeviceActivity";
    public static final int REQUEST_ADD_DEVICE = 1;

    private String ip;
    private byte[] sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final WifiInfo info = wifiManager.getConnectionInfo();
                    Log.d(TAG, "getConnectionInfo() returned: " + info.getSSID());

                    String broadcastIp = intToIp(wifiManager.getDhcpInfo().gateway);
                    Log.d(TAG, "broadcastIp : " + broadcastIp);

                    try {
                        final byte[] broadcastAddress = InetAddress.getByName(broadcastIp).getAddress();

                        broadcastAddress[3] = -1;
                        ip = InetAddress.getByAddress(broadcastAddress).getHostAddress();

                        if (ip.equals("10.10.100.255")) {
                            Log.d(TAG, "ip: 10.10.100.255");
                        } else {
                            Log.d(TAG, "ip: " + ip);
                        }

                        byte[] a = {1, 1, 1};
                        byte[] b = "~@@Hello,Thing!**#".getBytes();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(a.length + b.length);
                        byteBuffer.put(a);
                        byteBuffer.put(b);
                        sendData = byteBuffer.array();

                        UdpHelper.getInstance().setBroadcast(true);

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

//                    UdpHelper.getInstance().send(sendData, ip, 38899);
                    UdpHelper.getInstance().send("HF-A11ASSISTHREAD".getBytes(), ip, 48899);
                    byte[] data = UdpHelper.getInstance().receiveBytes();

                    try {
                        KeyGenerator generator = KeyGenerator.getInstance("AES");
                        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                        random.setSeed(data);
                        generator.init(128, random);
                        SecretKey key = generator.generateKey();
                        byte[] raw = key.getEncoded();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        wifiManager.startScan();
        List<ScanResult> list = wifiManager.getScanResults();
        if (list != null) {
            for (ScanResult scanResult :
                    list) {
                if (scanResult.SSID.indexOf("GN-") == 0) {
                    Log.d(TAG, "GN- returned: " + scanResult.SSID);
                } else {
                    Log.d(TAG, "SSID : " + scanResult.SSID);
                }
            }
        }

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.hiddenSSID = true;
        wifiConfiguration.allowedAuthAlgorithms.set(0);
        wifiConfiguration.allowedGroupCiphers.set(2);
        wifiConfiguration.allowedGroupCiphers.set(3);
        wifiConfiguration.allowedPairwiseCiphers.set(1);
        wifiConfiguration.allowedPairwiseCiphers.set(2);
        wifiConfiguration.allowedPairwiseCiphers.set(0);
        wifiConfiguration.allowedKeyManagement.set(1);
        wifiConfiguration.allowedProtocols.set(0);
        wifiConfiguration.allowedProtocols.set(1);
        wifiConfiguration.status = 2;
    }

    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }
}
