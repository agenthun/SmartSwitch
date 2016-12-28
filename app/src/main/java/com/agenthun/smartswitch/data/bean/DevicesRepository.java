package com.agenthun.smartswitch.data.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.DeviceCmdRsp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/15 04:14.
 */

public class DevicesRepository implements DevicesDataBean {

    @Nullable
    private static DevicesRepository INSTANCE = null;

    @NonNull
    private final DevicesDataBean mDevicesRemoteDataBean;

    @VisibleForTesting
    @Nullable
    Map<String, Device> mCachedDevices;

    @VisibleForTesting
    boolean mCacheIsDirty = false;

    public static DevicesRepository getInstance(@NonNull DevicesDataBean devicesRemoteDataBean) {
        if (INSTANCE == null) {
            INSTANCE = new DevicesRepository(devicesRemoteDataBean);
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    private DevicesRepository(@NonNull DevicesDataBean devicesRemoteDataBean) {
        mDevicesRemoteDataBean = checkNotNull(devicesRemoteDataBean);
    }

    @Override
    public Observable<List<Device>> getDevices(int sid) {
        if (mCachedDevices != null && !mCacheIsDirty) {
            return Observable.from(mCachedDevices.values()).toList();
        } else if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }

        Observable<List<Device>> remoteDevices = getRemoteDevices(sid);

        if (mCacheIsDirty) {
            return remoteDevices;
        } else {
            return Observable.from(mCachedDevices.values()).toList();
        }
    }

    private Observable<List<Device>> getRemoteDevices(int sid) {
        return mDevicesRemoteDataBean
                .getDevices(sid)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mCacheIsDirty = false;
                    }
                });
    }

    @Override
    public Observable<Device> getDevice(@NonNull String id) {
        checkNotNull(id);
        Device cachedDevice = mCachedDevices.get(id);

        if (cachedDevice != null) {
            return Observable.just(cachedDevice);
        }

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }

        Observable<Device> remoteDevices = mDevicesRemoteDataBean
                .getDevice(id)
                .doOnNext(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mCachedDevices.put(device.getMyId(), device);
                    }
                });

        return remoteDevices;
    }

    @Override
    public void addDevice(@NonNull Device device) {
        checkNotNull(device);
        mDevicesRemoteDataBean.addDevice(device);

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.put(device.getMyId(), device);
    }

    @Override
    public Observable<DeviceCmdRsp> toggleDevice(int sid, @NonNull Device device, Boolean status) {
        checkNotNull(device);
        return mDevicesRemoteDataBean.toggleDevice(sid, device, status);
    }

    @Override
    public Observable<Device> refreshDevice(int sid, @NonNull Device device) {
        checkNotNull(device);
        return mDevicesRemoteDataBean.refreshDevice(sid, device);
    }

    @Override
    public void configTime(@com.android.annotations.NonNull Device device, String time) {
        checkNotNull(device);

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

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.put(device.getMyId(), d);
    }

    @Override
    public void configStatus(@com.android.annotations.NonNull Device device, Integer status) {
        checkNotNull(device);

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

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.put(device.getMyId(), d);
    }

    @Override
    public void configInterval(@com.android.annotations.NonNull Device device, Integer interval) {
        checkNotNull(device);

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

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.put(device.getMyId(), d);
    }

    @Override
    public void configName(@com.android.annotations.NonNull Device device, String name) {
        checkNotNull(device);

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

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.put(device.getMyId(), d);
    }

    @Override
    public void configImage(@com.android.annotations.NonNull Device device, String image) {
        checkNotNull(device);

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

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.put(device.getMyId(), d);
    }


    @Override
    public void deleteDevice(@NonNull String id) {
        mDevicesRemoteDataBean.deleteDevice(id);

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.remove(id);
    }

    @Override
    public void deleteAllDevices() {
        mDevicesRemoteDataBean.deleteAllDevices();

        if (mCachedDevices == null) {
            mCachedDevices = new LinkedHashMap<>();
        }
        mCachedDevices.clear();
    }

    @Override
    public void refreshDevices() {
        mCacheIsDirty = true;
    }

    private Device getDeviceWithId(String id) {
        checkNotNull(id);
        if (mCachedDevices == null || mCachedDevices.isEmpty()) {
            return null;
        } else {
            return mCachedDevices.get(id);
        }
    }
}
