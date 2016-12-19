package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/16 22:51.
 */

public class LoginReq implements Parcelable {

    /**
     * SID : 0
     * NM : LoginReq
     * clientName : GT-P3100
     * userName : hun333@126.com
     * SN : 13
     * mac : 352123052298
     * agingTime : 86400000
     * password : 333123ABC
     * timeout : 5000
     * clientId : F0002C0004
     * CID : 10011
     */

    private int SID;
    private String NM;
    private String clientName;
    private String userName;
    private int SN;
    private String mac;
    private int agingTime;
    private String password;
    private int timeout;
    private String clientId;
    private int CID;

    public LoginReq(int SID, String NM, String clientName, String userName, int SN, String mac, int agingTime, String password, int timeout, String clientId, int CID) {
        this.SID = SID;
        this.NM = NM;
        this.clientName = clientName;
        this.userName = userName;
        this.SN = SN;
        this.mac = mac;
        this.agingTime = agingTime;
        this.password = password;
        this.timeout = timeout;
        this.clientId = clientId;
        this.CID = CID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getNM() {
        return NM;
    }

    public void setNM(String NM) {
        this.NM = NM;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getAgingTime() {
        return agingTime;
    }

    public void setAgingTime(int agingTime) {
        this.agingTime = agingTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SID);
        dest.writeString(this.NM);
        dest.writeString(this.clientName);
        dest.writeString(this.userName);
        dest.writeInt(this.SN);
        dest.writeString(this.mac);
        dest.writeInt(this.agingTime);
        dest.writeString(this.password);
        dest.writeInt(this.timeout);
        dest.writeString(this.clientId);
        dest.writeInt(this.CID);
    }

    protected LoginReq(Parcel in) {
        this.SID = in.readInt();
        this.NM = in.readString();
        this.clientName = in.readString();
        this.userName = in.readString();
        this.SN = in.readInt();
        this.mac = in.readString();
        this.agingTime = in.readInt();
        this.password = in.readString();
        this.timeout = in.readInt();
        this.clientId = in.readString();
        this.CID = in.readInt();
    }

    public static final Parcelable.Creator<LoginReq> CREATOR = new Parcelable.Creator<LoginReq>() {
        @Override
        public LoginReq createFromParcel(Parcel source) {
            return new LoginReq(source);
        }

        @Override
        public LoginReq[] newArray(int size) {
            return new LoginReq[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginReq loginReq = (LoginReq) o;

        if (!userName.equals(loginReq.userName)) return false;
        return mac.equals(loginReq.mac);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + mac.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "SID=" + SID +
                ", NM='" + NM + '\'' +
                ", clientName='" + clientName + '\'' +
                ", userName='" + userName + '\'' +
                ", SN=" + SN +
                ", mac='" + mac + '\'' +
                ", agingTime=" + agingTime +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", clientId='" + clientId + '\'' +
                ", CID=" + CID +
                '}';
    }
}
