package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 19:18.
 */

public class UserBean implements Parcelable {
    /**
     * createTime : 1481486476072
     * clientName : GT-P3100
     * mac : 352123052298
     * lng : 0.0
     * clientIp : 180.160.47.220
     * clientId : F0002C0004
     * lat : 0.0
     */

    private String createTime;
    private String clientName;
    private String mac;
    private String lng;
    private String clientIp;
    private String clientId;
    private String lat;

    public UserBean(String createTime, String clientName, String mac, String lng, String clientIp, String clientId, String lat) {
        this.createTime = createTime;
        this.clientName = clientName;
        this.mac = mac;
        this.lng = lng;
        this.clientIp = clientIp;
        this.clientId = clientId;
        this.lat = lat;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeString(this.clientName);
        dest.writeString(this.mac);
        dest.writeString(this.lng);
        dest.writeString(this.clientIp);
        dest.writeString(this.clientId);
        dest.writeString(this.lat);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.createTime = in.readString();
        this.clientName = in.readString();
        this.mac = in.readString();
        this.lng = in.readString();
        this.clientIp = in.readString();
        this.clientId = in.readString();
        this.lat = in.readString();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        if (!mac.equals(userBean.mac)) return false;
        return clientId.equals(userBean.clientId);

    }

    @Override
    public int hashCode() {
        int result = mac.hashCode();
        result = 31 * result + clientId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "createTime='" + createTime + '\'' +
                ", clientName='" + clientName + '\'' +
                ", mac='" + mac + '\'' +
                ", lng='" + lng + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", clientId='" + clientId + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
