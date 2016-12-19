package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 01:28.
 */

public class UserOnlineQueryReq implements Parcelable {
    /**
     * SID : 975921257
     * SN : 15
     * timeout : 5000
     * NM : UserOnlineQueryReq
     * CID : 10251
     * userId : 2405
     */

    private int SID;
    private int SN;
    private int timeout;
    private String NM;
    private int CID;
    private int userId;

    public UserOnlineQueryReq(int SID, int SN, int timeout, String NM, int CID, int userId) {
        this.SID = SID;
        this.SN = SN;
        this.timeout = timeout;
        this.NM = NM;
        this.CID = CID;
        this.userId = userId;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getNM() {
        return NM;
    }

    public void setNM(String NM) {
        this.NM = NM;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SID);
        dest.writeInt(this.SN);
        dest.writeInt(this.timeout);
        dest.writeString(this.NM);
        dest.writeInt(this.CID);
        dest.writeInt(this.userId);
    }

    protected UserOnlineQueryReq(Parcel in) {
        this.SID = in.readInt();
        this.SN = in.readInt();
        this.timeout = in.readInt();
        this.NM = in.readString();
        this.CID = in.readInt();
        this.userId = in.readInt();
    }

    public static final Parcelable.Creator<UserOnlineQueryReq> CREATOR = new Parcelable.Creator<UserOnlineQueryReq>() {
        @Override
        public UserOnlineQueryReq createFromParcel(Parcel source) {
            return new UserOnlineQueryReq(source);
        }

        @Override
        public UserOnlineQueryReq[] newArray(int size) {
            return new UserOnlineQueryReq[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOnlineQueryReq that = (UserOnlineQueryReq) o;

        if (SID != that.SID) return false;
        return userId == that.userId;

    }

    @Override
    public int hashCode() {
        int result = SID;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "UserOnlineQueryReq{" +
                "SID=" + SID +
                ", SN=" + SN +
                ", timeout=" + timeout +
                ", NM='" + NM + '\'' +
                ", CID=" + CID +
                ", userId=" + userId +
                '}';
    }
}
