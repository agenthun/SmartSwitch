package com.agenthun.smartswitch.data.bean.remote;

import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.bean.DevicesDataBean;
import com.android.annotations.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/15 04:18.
 */

public class DevicesRemoteDataBean implements DevicesDataBean {

    private static DevicesRemoteDataBean INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, Device> DEVICES_SERVICE_DATA;

    static {
        DEVICES_SERVICE_DATA = new LinkedHashMap<>(2);
        buildDevice("ACCF23218698", "公牛智能插座1", "13:14", Device.STATUS_DEVICE_CLOSE, 0xff, true);
        buildDevice("ACCF23218699", "公牛智能插座2", "13:14-14:13", Device.STATUS_DEVICE_OPEN, 0x11, false);
    }

    public static DevicesRemoteDataBean getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DevicesRemoteDataBean();
        }
        return INSTANCE;
    }

    private DevicesRemoteDataBean() {
    }

    private static void buildDevice(String mac, String name,
                                    String configTime, Integer configStatus,
                                    Integer configInterval, Boolean status) {
        Device d = new Device(mac, name,
                configTime, configStatus,
                configInterval, status);
        DEVICES_SERVICE_DATA.put(d.getMyId(), d);
    }

    @Override
    public Observable<List<Device>> getDevices() {
        return Observable
                .from(DEVICES_SERVICE_DATA.values())
                .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
                .toList();
    }

    @Override
    public Observable<Device> getDevice(@NonNull String id) {
        Device d = DEVICES_SERVICE_DATA.get(id);
        if (d != null) {
            return Observable
                    .just(d)
                    .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);
        } else {
            return Observable.empty();
        }
    }

    @Override
    public void addDevice(@NonNull Device device) {
        DEVICES_SERVICE_DATA.put(device.getMyId(), device);
    }

    @Override
    public void toggleDevice(@NonNull Device device) {
        Device d = new Device(device.getMyId(), device.getMac(),
                device.getNeedRemoteControl(), device.getFactoryId(),
                device.getType(), device.getHardwareVer(),
                device.getSoftwareVer(), device.getBindTime(),
                device.getTotalOnlineTime(), device.getGpsLat(),
                device.getGpsLng(), device.getImage(),
                device.getOnline(), device.getId(),
                device.getPid(), device.getNodeType(),
                device.getOrderNo(), device.getName(),
                device.getConfigTime(), device.getConfigStatus(),
                device.getConfigInterval(), device.getStatus() ? false : true);
        DEVICES_SERVICE_DATA.put(device.getMyId(), d);
    }

    @Override
    public void configTime(@NonNull Device device, String time) {
        Device d = new Device(device.getMyId(), device.getMac(),
                device.getNeedRemoteControl(), device.getFactoryId(),
                device.getType(), device.getHardwareVer(),
                device.getSoftwareVer(), device.getBindTime(),
                device.getTotalOnlineTime(), device.getGpsLat(),
                device.getGpsLng(), device.getImage(),
                device.getOnline(), device.getId(),
                device.getPid(), device.getNodeType(),
                device.getOrderNo(), device.getName(),
                time, device.getConfigStatus(),
                device.getConfigInterval(), device.getStatus());
        DEVICES_SERVICE_DATA.put(device.getMyId(), d);
    }

    @Override
    public void configStatus(@NonNull Device device, Integer status) {
        Device d = new Device(device.getMyId(), device.getMac(),
                device.getNeedRemoteControl(), device.getFactoryId(),
                device.getType(), device.getHardwareVer(),
                device.getSoftwareVer(), device.getBindTime(),
                device.getTotalOnlineTime(), device.getGpsLat(),
                device.getGpsLng(), device.getImage(),
                device.getOnline(), device.getId(),
                device.getPid(), device.getNodeType(),
                device.getOrderNo(), device.getName(),
                device.getConfigTime(), status,
                device.getConfigInterval(), device.getStatus());
        DEVICES_SERVICE_DATA.put(device.getMyId(), d);
    }

    @Override
    public void configInterval(@NonNull Device device, Integer interval) {
        Device d = new Device(device.getMyId(), device.getMac(),
                device.getNeedRemoteControl(), device.getFactoryId(),
                device.getType(), device.getHardwareVer(),
                device.getSoftwareVer(), device.getBindTime(),
                device.getTotalOnlineTime(), device.getGpsLat(),
                device.getGpsLng(), device.getImage(),
                device.getOnline(), device.getId(),
                device.getPid(), device.getNodeType(),
                device.getOrderNo(), device.getName(),
                device.getConfigTime(), device.getConfigStatus(),
                interval, device.getStatus());
        DEVICES_SERVICE_DATA.put(device.getMyId(), d);
    }

    @Override
    public void configName(@NonNull Device device, String name) {
        Device d = new Device(device.getMyId(), device.getMac(),
                device.getNeedRemoteControl(), device.getFactoryId(),
                device.getType(), device.getHardwareVer(),
                device.getSoftwareVer(), device.getBindTime(),
                device.getTotalOnlineTime(), device.getGpsLat(),
                device.getGpsLng(), device.getImage(),
                device.getOnline(), device.getId(),
                device.getPid(), device.getNodeType(),
                device.getOrderNo(), name,
                device.getConfigTime(), device.getConfigStatus(),
                device.getConfigInterval(), device.getStatus());
        DEVICES_SERVICE_DATA.put(device.getMyId(), d);
    }

    @Override
    public void configImage(@NonNull Device device, String image) {
        Device d = new Device(device.getMyId(), device.getMac(),
                device.getNeedRemoteControl(), device.getFactoryId(),
                device.getType(), device.getHardwareVer(),
                device.getSoftwareVer(), device.getBindTime(),
                device.getTotalOnlineTime(), device.getGpsLat(),
                device.getGpsLng(), image,
                device.getOnline(), device.getId(),
                device.getPid(), device.getNodeType(),
                device.getOrderNo(), device.getName(),
                device.getConfigTime(), device.getConfigStatus(),
                device.getConfigInterval(), device.getStatus());
        DEVICES_SERVICE_DATA.put(device.getMyId(), d);
    }

    @Override
    public void deleteDevice(@NonNull String id) {
        DEVICES_SERVICE_DATA.remove(id);
    }

    @Override
    public void deleteAllDevices() {
        DEVICES_SERVICE_DATA.clear();
    }

    @Override
    public void refreshDevices() {

    }
}
