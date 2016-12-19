package com.agenthun.smartswitch.data.bean;

import com.agenthun.smartswitch.data.Device;
import com.android.annotations.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/15 04:02.
 */

public interface DevicesDataBean {

    Observable<List<Device>> getDevices();

    Observable<Device> getDevice(@NonNull String id);

    //添加设备
    void addDevice(@NonNull Device device);

    //修改设备当前状态
    void toggleDevice(@NonNull Device device);

    //配置设备时间
    void configTime(@NonNull Device device, String time);

    //配置设备状态
    void configStatus(@NonNull Device device, Integer status);

    //配置设备周期
    void configInterval(@NonNull Device device, Integer interval);

    //配置设备名称
    void configName(@NonNull Device device, String name);

    //配置设备图片
    void configImage(@NonNull Device device, String image);

    //删除设备
    void deleteDevice(@NonNull String id);

    //删除所有设备
    void deleteAllDevices();

    //刷新所有设备
    void refreshDevices();
}
