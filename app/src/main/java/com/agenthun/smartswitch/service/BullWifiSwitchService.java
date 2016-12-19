package com.agenthun.smartswitch.service;

import com.agenthun.smartswitch.data.DeviceCmdRsp;
import com.agenthun.smartswitch.data.DeviceQueryByGroupRsp;
import com.agenthun.smartswitch.data.ImageHomeUrlRsp;
import com.agenthun.smartswitch.data.LoginRsp;
import com.agenthun.smartswitch.data.UserOnlineQueryReq;
import com.agenthun.smartswitch.data.UserOnlineQueryRsp;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/16 22:40.
 */

public interface BullWifiSwitchService {
    public static final String API_SERVICE_URL = "http://www.gn-smart.cn/usvc/";


    //登录
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("API_SERVICE_URL")
    Observable<LoginRsp> login(@Body RequestBody body);

    //获取在线用户
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("API_SERVICE_URL")
    Observable<UserOnlineQueryRsp> queryUserOnline(@Body RequestBody body);

    //获取图片存储页面URL
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("API_SERVICE_URL")
    Observable<ImageHomeUrlRsp> queryImageHomeUrl(@Body RequestBody body);

    //获取所有设备
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("API_SERVICE_URL")
    Observable<DeviceQueryByGroupRsp> queryDeviceGroup(@Body RequestBody body);

    //操作设备
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("API_SERVICE_URL")
    Observable<DeviceCmdRsp> operateDevice(@Body RequestBody body);
}
