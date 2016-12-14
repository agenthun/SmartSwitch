package com.agenthun.smartswitch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.agenthun.smartswitch.helper.ParcelableHelper;

import java.util.Objects;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/14 05:01.
 */

public class Device implements Parcelable {
    private final String mac;
    private Boolean needRemoteControl;
    private final Integer factoryId;
    private final Integer type;
    private final String hardwareVer;
    private final String softwareVer;
    private String bindTime;
    private Long totalOnlineTime;
    private Double gpsLat;
    private Double gpsLng;
    private String image;
    private Boolean online;
    private Integer id;
    private Integer pid;
    private Integer nodeType;
    private Integer orderNo;
    private String name;

    private String timeSetting;
    private Boolean status;

    public Device(String mac, Integer factoryId, Integer type, String hardwareVer, String softwareVer, String name, String timeSetting, Boolean status) {
        this.mac = mac;
        this.factoryId = factoryId;
        this.type = type;
        this.hardwareVer = hardwareVer;
        this.softwareVer = softwareVer;
        this.name = name;
        this.timeSetting = timeSetting;
        this.status = status;
    }

    public Device(String mac, Boolean needRemoteControl,
                  Integer factoryId, Integer type,
                  String hardwareVer, String softwareVer,
                  String bindTime, Long totalOnlineTime,
                  Double gpsLat, Double gpsLng,
                  String image, Boolean online,
                  Integer id, Integer pid,
                  Integer nodeType, Integer orderNo,
                  String name, String timeSetting,
                  Boolean status) {
        this.mac = mac;
        this.needRemoteControl = needRemoteControl;
        this.factoryId = factoryId;
        this.type = type;
        this.hardwareVer = hardwareVer;
        this.softwareVer = softwareVer;
        this.bindTime = bindTime;
        this.totalOnlineTime = totalOnlineTime;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.image = image;
        this.online = online;
        this.id = id;
        this.pid = pid;
        this.nodeType = nodeType;
        this.orderNo = orderNo;
        this.name = name;

        this.timeSetting = timeSetting;
        this.status = status;
    }

    protected Device(Parcel in) {
        mac = in.readString();
        needRemoteControl = ParcelableHelper.readBoolean(in);
        factoryId = in.readInt();
        type = in.readInt();
        hardwareVer = in.readString();
        softwareVer = in.readString();
        bindTime = in.readString();
        totalOnlineTime = in.readLong();
        gpsLat = in.readDouble();
        gpsLng = in.readDouble();
        image = in.readString();
        online = ParcelableHelper.readBoolean(in);
        id = in.readInt();
        pid = in.readInt();
        nodeType = in.readInt();
        orderNo = in.readInt();
        name = in.readString();

        timeSetting = in.readString();
        status = ParcelableHelper.readBoolean(in);
    }

    public String getMac() {
        return mac;
    }

    public Boolean getNeedRemoteControl() {
        return needRemoteControl;
    }

    public void setNeedRemoteControl(Boolean needRemoteControl) {
        this.needRemoteControl = needRemoteControl;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public Integer getType() {
        return type;
    }

    public String getHardwareVer() {
        return hardwareVer;
    }

    public String getSoftwareVer() {
        return softwareVer;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public Double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public Double getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(Double gpsLng) {
        this.gpsLng = gpsLng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeSetting() {
        return timeSetting;
    }

    public void setTimeSetting(String timeSetting) {
        this.timeSetting = timeSetting;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mac);
        ParcelableHelper.writeBoolean(parcel, needRemoteControl);
        parcel.writeInt(factoryId);
        parcel.writeInt(type);
        parcel.writeString(hardwareVer);
        parcel.writeString(softwareVer);
        parcel.writeString(bindTime);
        parcel.writeLong(totalOnlineTime);
        parcel.writeDouble(gpsLat);
        parcel.writeDouble(gpsLng);
        parcel.writeString(image);
        ParcelableHelper.writeBoolean(parcel, online);
        parcel.writeInt(id);
        parcel.writeInt(pid);
        parcel.writeInt(nodeType);
        parcel.writeInt(orderNo);
        parcel.writeString(name);

        parcel.writeString(timeSetting);
        ParcelableHelper.writeBoolean(parcel, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;

        if (!mac.equals(device.mac)) {
            return false;
        }
        if (!factoryId.equals(device.factoryId)) {
            return false;
        }
        if (!type.equals(device.type)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = mac.hashCode();
        result = 31 * result + factoryId.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
