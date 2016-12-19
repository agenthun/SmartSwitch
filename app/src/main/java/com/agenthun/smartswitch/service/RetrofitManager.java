package com.agenthun.smartswitch.service;

import android.util.Log;

import com.agenthun.smartswitch.R;
import com.agenthun.smartswitch.data.Device;
import com.agenthun.smartswitch.data.DeviceCmdReq;
import com.agenthun.smartswitch.data.DeviceCmdRsp;
import com.agenthun.smartswitch.data.DeviceQueryByGroupReq;
import com.agenthun.smartswitch.data.DeviceQueryByGroupRsp;
import com.agenthun.smartswitch.data.ImageHomeUrlReq;
import com.agenthun.smartswitch.data.ImageHomeUrlRsp;
import com.agenthun.smartswitch.data.LoginRsp;
import com.agenthun.smartswitch.data.LoginReq;
import com.agenthun.smartswitch.data.UserOnlineQueryReq;
import com.agenthun.smartswitch.data.UserOnlineQueryRsp;
import com.agenthun.smartswitch.helper.PreferencesHelper;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/16 23:36.
 */

public class RetrofitManager {

    private static BullWifiSwitchService service;

    public static RetrofitManager builder() {
        return new RetrofitManager();
    }

    private RetrofitManager() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BullWifiSwitchService.API_SERVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            service = retrofit.create(BullWifiSwitchService.class);
        }
    }

    //登录
    public Observable<LoginRsp> loginObservable(LoginReq request) {
        String content = new Gson().toJson(request);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        return service.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    //获取在线用户
    public Observable<UserOnlineQueryRsp> queryUserOnlineObservable(UserOnlineQueryReq request) {
        String content = new Gson().toJson(request);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        return service.queryUserOnline(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    //获取图片存储页面URL
    public Observable<ImageHomeUrlRsp> queryImageHomeUrlObservable(ImageHomeUrlReq request) {
        String content = new Gson().toJson(request);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        return service.queryImageHomeUrl(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    //获取所有设备
    public Observable<DeviceQueryByGroupRsp> queryDeviceGroupObservable(DeviceQueryByGroupReq request) {
        String content = new Gson().toJson(request);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        return service.queryDeviceGroup(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    //操作设备
    public Observable<DeviceCmdRsp> operateDeviceObservable(DeviceCmdReq request) {
        String content = new Gson().toJson(request);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        return service.operateDevice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    //更新设备状态
    public Observable<Device> updateDeviceObservable(final int sid, final Device device) {
        Map<String, String> mapStatus = new HashMap<>(1);
        mapStatus.put(device.getMac(), DeviceCmdReq.CMD_GET_STATUS);
        DeviceCmdReq requestStatus = new DeviceCmdReq(
                sid,
                mapStatus,
                DeviceCmdReq.FUNCTION_GET,
                DeviceCmdReq.FUNCTION_GET_CID
        );

        Observable<DeviceCmdRsp> deviceStatus = RetrofitManager.builder().operateDeviceObservable(requestStatus);

        Map<String, String> mapInterval = new HashMap<>(1);
        mapInterval.put(device.getMac(), DeviceCmdReq.CMD_GET_INTERVAL);
        DeviceCmdReq requestInterval = new DeviceCmdReq(
                sid,
                mapInterval,
                DeviceCmdReq.FUNCTION_GET,
                DeviceCmdReq.FUNCTION_GET_CID
        );

        Observable<DeviceCmdRsp> deviceInterval = RetrofitManager.builder().operateDeviceObservable(requestInterval);

        return Observable.zip(deviceStatus, deviceInterval, new Func2<DeviceCmdRsp, DeviceCmdRsp, Device>() {
            @Override
            public Device call(DeviceCmdRsp statusRsp, DeviceCmdRsp intervalRsp) {
                String codeStatus = statusRsp.getOutput().get(device.getMac());
                switch (codeStatus) {
                    case DeviceCmdRsp.CODE_STATUS_OPEN_MANUAL:
                    case DeviceCmdRsp.CODE_STATUS_OPEN_AUTO:
                        device.setStatus(true);
                        break;
                    case DeviceCmdRsp.CODE_STATUS_CLOSE_MANUAL:
                    case DeviceCmdRsp.CODE_STATUS_CLOSE_AUTO:
                        device.setStatus(false);
                        break;
                    default:
                        device.setStatus(false);
                        break;
                }
                parseIntervalRsp(intervalRsp.getOutput().get(device.getMac()), device);
                return device;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    private void parseIntervalRsp(String cmd, Device device) {
        if (cmd.startsWith(DeviceCmdRsp.CODE_IS_INTERVAL_INITIAL)) {
            device.setConfigTime("");
            device.setConfigStatus(Device.STATUS_DEVICE_INITIAL);
            device.setConfigInterval(0);
        } else if (cmd.startsWith(DeviceCmdRsp.CODE_IS_INTERVAL_FALSE)) {
            String time = cmd.substring(20, 24);
            device.setConfigTime(parseConfigTime(time));

            if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_CLOSE, 24)) {
                device.setConfigStatus(Device.STATUS_DEVICE_CLOSE);
            } else if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_OPEN, 24)) {
                device.setConfigStatus(Device.STATUS_DEVICE_OPEN);
            }

            device.setConfigInterval(0xff);
        } else if (cmd.startsWith(DeviceCmdRsp.CODE_IS_INTERVAL_TRUE)) {
            String time = cmd.substring(18, 22);
            device.setConfigTime(parseConfigTime(time));

            if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_CLOSE, 22)) {
                device.setConfigStatus(Device.STATUS_DEVICE_CLOSE);
            } else if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_OPEN, 22)) {
                device.setConfigStatus(Device.STATUS_DEVICE_OPEN);
            }

            Integer interval = Integer.parseInt(cmd.substring(16, 18), 16);
            device.setConfigInterval(interval);
        } else if (cmd.startsWith(DeviceCmdRsp.CODE_IS_TIME_SLOT_INTERVAL_FALSE)) {
            String timeStart = cmd.substring(20, 24);
            String timeEnd = cmd.substring(28 + DeviceCmdRsp.CODE_CONFIG_CLOSE.length(), 32 + DeviceCmdRsp.CODE_CONFIG_CLOSE.length());
            device.setConfigTime(parseConfigTime(timeStart) + "-" + parseConfigTime(timeEnd));

            if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_CLOSE, 24)) {
                device.setConfigStatus(Device.STATUS_DEVICE_CLOSE);
            } else if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_OPEN, 24)) {
                device.setConfigStatus(Device.STATUS_DEVICE_OPEN);
            }

            device.setConfigInterval(0xff);
        } else if (cmd.startsWith(DeviceCmdRsp.CODE_IS_TIME_SLOT_INTERVAL_TRUE)) {
            String timeStart = cmd.substring(18, 22);
            String timeEnd = cmd.substring(22 + DeviceCmdRsp.CODE_CONFIG_CLOSE.length(), 26 + DeviceCmdRsp.CODE_CONFIG_CLOSE.length());
            device.setConfigTime(parseConfigTime(timeStart) + "-" + parseConfigTime(timeEnd));

            if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_CLOSE, 22)) {
                device.setConfigStatus(Device.STATUS_DEVICE_CLOSE);
            } else if (cmd.startsWith(DeviceCmdRsp.CODE_CONFIG_OPEN, 22)) {
                device.setConfigStatus(Device.STATUS_DEVICE_OPEN);
            }

            Integer interval = Integer.parseInt(cmd.substring(16, 18), 16);
            device.setConfigInterval(interval);
        }
    }

    private String parseConfigTime(String timeHex) {
        if (timeHex.length() == 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%02d", Integer.parseInt(timeHex.substring(0, 2), 16)) + ":");
            sb.append(String.format("%02d", Integer.parseInt(timeHex.substring(2), 16)));
            return sb.toString();
        }
        return "";
    }
}
