package com.agenthun.smartswitch.data.bean.remote;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.DeviceCmdReq;
import com.agenthun.smartswitch.data.DeviceCmdRsp;
import com.agenthun.smartswitch.data.DeviceQueryByGroupReq;
import com.agenthun.smartswitch.data.DeviceQueryByGroupRsp;
import com.agenthun.smartswitch.data.bean.DevicesDataBean;
import com.agenthun.smartswitch.helper.PreferencesHelper;
import com.agenthun.smartswitch.service.RetrofitManager;
import com.android.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    public Observable<List<Device>> getDevices(final int sid) {
        DeviceQueryByGroupReq request = new DeviceQueryByGroupReq(
                sid,
                17,
                new ArrayList<DeviceQueryByGroupReq.DevicePageListReq>(Arrays.asList(new DeviceQueryByGroupReq.DevicePageListReq(100, -1, 1))),
                5000,
                "DeviceQueryByGroupReq",
                30081
        );

        return RetrofitManager.builder().queryDeviceGroupObservable(request)
                .flatMap(new Func1<DeviceQueryByGroupRsp, Observable<List<Device>>>() {

                    @Override
                    public Observable<List<Device>> call(DeviceQueryByGroupRsp deviceQueryByGroupRsp) {
                        if (deviceQueryByGroupRsp.getResult() != 1) {
                            return Observable.empty();
                        }

                        Map<String, DeviceQueryByGroupRsp.DevicePageListRsp> map = deviceQueryByGroupRsp.getSubRspMap();
                        List<Device> list = new ArrayList<Device>();
                        for (DeviceQueryByGroupRsp.DevicePageListRsp pageListRsp : map.values()) {
                            list.addAll(pageListRsp.getItemList());
                        }

                        return Observable.from(list).toList();
                    }
                });
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
    public Observable<DeviceCmdRsp> toggleDevice(int sid, @NonNull Device device, Boolean status) {
        Map<String, String> map = new HashMap<>(1);
        map.put(device.getMac(), status ? DeviceCmdReq.CMD_SET_STATUS_OPEN : DeviceCmdReq.CMD_SET_STATUS_CLOSE);
        DeviceCmdReq request = new DeviceCmdReq(
                sid,
                map,
                DeviceCmdReq.FUNCTION_SET,
                DeviceCmdReq.FUNCTION_SET_CID
        );

        return RetrofitManager.builder().operateDeviceObservable(request);
    }

    @Override
    public Observable<Device> refreshDevice(int sid, @NonNull Device device) {
        return RetrofitManager.builder().updateDeviceObservable(sid, device);
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
