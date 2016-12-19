package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/16 23:52.
 */

public class LoginRsp implements Parcelable {
    /**
     * userId : 2405
     * result : 1
     * reason : welcome null
     * SN : 13
     * CID : 10012
     * SID : 1970065752
     * NM : LoginRsp
     */

    private int userId;
    private int result;
    private String reason;
    private int SN;
    private int CID;
    private int SID;
    private String NM;

    public LoginRsp(int userId, int result, String reason, int SN, int CID, int SID, String NM) {
        this.userId = userId;
        this.result = result;
        this.reason = reason;
        this.SN = SN;
        this.CID = CID;
        this.SID = SID;
        this.NM = NM;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeInt(this.result);
        dest.writeString(this.reason);
        dest.writeInt(this.SN);
        dest.writeInt(this.CID);
        dest.writeInt(this.SID);
        dest.writeString(this.NM);
    }

    protected LoginRsp(Parcel in) {
        this.userId = in.readInt();
        this.result = in.readInt();
        this.reason = in.readString();
        this.SN = in.readInt();
        this.CID = in.readInt();
        this.SID = in.readInt();
        this.NM = in.readString();
    }

    public static final Parcelable.Creator<LoginRsp> CREATOR = new Parcelable.Creator<LoginRsp>() {
        @Override
        public LoginRsp createFromParcel(Parcel source) {
            return new LoginRsp(source);
        }

        @Override
        public LoginRsp[] newArray(int size) {
            return new LoginRsp[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginRsp loginRsp = (LoginRsp) o;

        if (userId != loginRsp.userId) return false;
        return SID == loginRsp.SID;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + SID;
        return result;
    }

    @Override
    public String toString() {
        return "LoginRsp{" +
                "userId=" + userId +
                ", result=" + result +
                ", reason='" + reason + '\'' +
                ", SN=" + SN +
                ", CID=" + CID +
                ", SID=" + SID +
                ", NM='" + NM + '\'' +
                '}';
    }
}
