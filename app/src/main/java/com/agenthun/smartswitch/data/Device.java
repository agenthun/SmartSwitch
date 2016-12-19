package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.agenthun.smartswitch.helper.ParcelableHelper;

import java.util.UUID;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/14 05:01.
 */

public class Device implements Parcelable {

    public static final int STATUS_DEVICE_INITIAL = -1;
    public static final int STATUS_DEVICE_CLOSE = 0;
    public static final int STATUS_DEVICE_OPEN = 1;

    @NonNull
    private String myId;

    @NonNull
    private String mac;
    @Nullable
    private Boolean needRemoteControl;
    @Nullable
    private Integer factoryId;
    @Nullable
    private Integer type;
    @Nullable
    private String hardwareVer;
    @Nullable
    private String softwareVer;
    @Nullable
    private String bindTime;
    @Nullable
    private Long totalOnlineTime;
    @Nullable
    private Double gpsLat;
    @Nullable
    private Double gpsLng;
    @Nullable
    private String image;
    @Nullable
    private Boolean online;
    @Nullable
    private Integer id;
    @Nullable
    private Integer pid;
    @Nullable
    private Integer nodeType;
    @Nullable
    private Integer orderNo;

    @Nullable
    private String name;

    @Nullable
    private String configTime;

    @Nullable
    private Integer configStatus;

    @Nullable
    private Integer configInterval;

    private Boolean status;

    public Device() {
        this.myId = UUID.randomUUID().toString();
        this.configTime = "";
        this.configStatus = STATUS_DEVICE_INITIAL;
        this.configInterval = 0;
        this.status = false;
    }

    public Device(@NonNull String mac) {
        this(mac, false);
    }

    public Device(@NonNull String mac, Boolean status) {
        this(mac, "公牛智能插座", "", STATUS_DEVICE_INITIAL, 0, status);
    }

    public Device(@NonNull String mac, @Nullable String name,
                  @Nullable String configTime, @Nullable Integer configStatus,
                  @Nullable Integer configInterval, Boolean status) {
        this(mac, "icon_default_photo1.png", name,
                configTime, configStatus,
                configInterval, status);
    }

    public Device(@NonNull String mac,
                  @Nullable String image, @Nullable String name,
                  @Nullable String configTime, @Nullable Integer configStatus,
                  @Nullable Integer configInterval, Boolean status) {
        this(mac, false, 1,
                257, "v1.0",
                "1.0.0.4a.gn13_0917", "0",
                0l, 0d,
                0d, image,
                true, 0,
                0, 1,
                0, name,
                configTime, configStatus,
                configInterval, status);
    }

    public Device(@NonNull String mac,
                  @Nullable Boolean needRemoteControl, @Nullable Integer factoryId,
                  @Nullable Integer type, @Nullable String hardwareVer,
                  @Nullable String softwareVer, @Nullable String bindTime,
                  @Nullable Long totalOnlineTime, @Nullable Double gpsLat,
                  @Nullable Double gpsLng, @Nullable String image,
                  @Nullable Boolean online, @Nullable Integer id,
                  @Nullable Integer pid, @Nullable Integer nodeType,
                  @Nullable Integer orderNo, @Nullable String name,
                  @Nullable String configTime, @Nullable Integer configStatus,
                  @Nullable Integer configInterval, Boolean status) {
        this(UUID.randomUUID().toString(), mac,
                needRemoteControl, factoryId,
                type, hardwareVer,
                softwareVer, bindTime,
                totalOnlineTime, gpsLat,
                gpsLng, image,
                online, id,
                pid, nodeType,
                orderNo, name,
                configTime, configStatus,
                configInterval, status);
    }

    public Device(@NonNull String myId, @NonNull String mac,
                  @Nullable Boolean needRemoteControl, @Nullable Integer factoryId,
                  @Nullable Integer type, @Nullable String hardwareVer,
                  @Nullable String softwareVer, @Nullable String bindTime,
                  @Nullable Long totalOnlineTime, @Nullable Double gpsLat,
                  @Nullable Double gpsLng, @Nullable String image,
                  @Nullable Boolean online, @Nullable Integer id,
                  @Nullable Integer pid, @Nullable Integer nodeType,
                  @Nullable Integer orderNo, @Nullable String name,
                  @Nullable String configTime, @Nullable Integer configStatus,
                  @Nullable Integer configInterval, Boolean status) {
        this.myId = myId;
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
        this.configTime = configTime;
        this.configStatus = configStatus;
        this.configInterval = configInterval;
        this.status = status;
    }

    protected Device(Parcel in) {
        myId = in.readString();
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

        configTime = in.readString();
        configStatus = in.readInt();
        configInterval = in.readInt();
        status = ParcelableHelper.readBoolean(in);
    }

    @NonNull
    public String getMyId() {
        return myId;
    }

    public void setMyId(@NonNull String myId) {
        this.myId = myId;
    }

    public void setMyId() {
        this.myId = UUID.randomUUID().toString();
    }

    @NonNull
    public String getMac() {
        return mac;
    }

    public void setMac(@NonNull String mac) {
        this.mac = mac;
    }

    @Nullable
    public Boolean getNeedRemoteControl() {
        return needRemoteControl;
    }

    public void setNeedRemoteControl(@Nullable Boolean needRemoteControl) {
        this.needRemoteControl = needRemoteControl;
    }

    @Nullable
    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(@Nullable Integer factoryId) {
        this.factoryId = factoryId;
    }

    @Nullable
    public Integer getType() {
        return type;
    }

    public void setType(@Nullable Integer type) {
        this.type = type;
    }

    @Nullable
    public String getHardwareVer() {
        return hardwareVer;
    }

    public void setHardwareVer(@Nullable String hardwareVer) {
        this.hardwareVer = hardwareVer;
    }

    @Nullable
    public String getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(@Nullable String softwareVer) {
        this.softwareVer = softwareVer;
    }

    @Nullable
    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(@Nullable String bindTime) {
        this.bindTime = bindTime;
    }

    @Nullable
    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(@Nullable Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    @Nullable
    public Double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(@Nullable Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    @Nullable
    public Double getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(@Nullable Double gpsLng) {
        this.gpsLng = gpsLng;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    @Nullable
    public Boolean getOnline() {
        return online;
    }

    public void setOnline(@Nullable Boolean online) {
        this.online = online;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    @Nullable
    public Integer getPid() {
        return pid;
    }

    public void setPid(@Nullable Integer pid) {
        this.pid = pid;
    }

    @Nullable
    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(@Nullable Integer nodeType) {
        this.nodeType = nodeType;
    }

    @Nullable
    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(@Nullable Integer orderNo) {
        this.orderNo = orderNo;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getConfigTime() {
        return configTime;
    }

    public void setConfigTime(@Nullable String configTime) {
        this.configTime = configTime;
    }

    @Nullable
    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(@Nullable Integer configStatus) {
        this.configStatus = configStatus;
    }

    @Nullable
    public Integer getConfigInterval() {
        return configInterval;
    }

    public void setConfigInterval(@Nullable Integer configInterval) {
        this.configInterval = configInterval;
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
        parcel.writeString(myId);
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

        parcel.writeString(configTime);
        parcel.writeInt(configStatus);
        parcel.writeInt(configInterval);
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

    @Override
    public String toString() {
        return "Device{" +
                "myId='" + myId + '\'' +
                ", mac='" + mac + '\'' +
                ", needRemoteControl=" + needRemoteControl +
                ", factoryId=" + factoryId +
                ", type=" + type +
                ", hardwareVer='" + hardwareVer + '\'' +
                ", softwareVer='" + softwareVer + '\'' +
                ", bindTime='" + bindTime + '\'' +
                ", totalOnlineTime=" + totalOnlineTime +
                ", gpsLat=" + gpsLat +
                ", gpsLng=" + gpsLng +
                ", image='" + image + '\'' +
                ", online=" + online +
                ", id=" + id +
                ", pid=" + pid +
                ", nodeType=" + nodeType +
                ", orderNo=" + orderNo +
                ", name='" + name + '\'' +
                ", configTime='" + configTime + '\'' +
                ", configStatus=" + configStatus +
                ", configInterval='" + configInterval + '\'' +
                ", status=" + status +
                '}';
    }
}
