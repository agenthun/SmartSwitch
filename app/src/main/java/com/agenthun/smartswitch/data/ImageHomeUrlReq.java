package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 03:28.
 */

public class ImageHomeUrlReq implements Parcelable {
    /**
     * SID : 975921257
     * SN : 16
     * timeout : 5000
     * NM : ImageHomeUrlReq
     * CID : 30351
     */

    private int SID;
    private int SN;
    private int timeout;
    private String NM;
    private int CID;

    public ImageHomeUrlReq(int SID, int SN, int timeout, String NM, int CID) {
        this.SID = SID;
        this.SN = SN;
        this.timeout = timeout;
        this.NM = NM;
        this.CID = CID;
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
    }

    protected ImageHomeUrlReq(Parcel in) {
        this.SID = in.readInt();
        this.SN = in.readInt();
        this.timeout = in.readInt();
        this.NM = in.readString();
        this.CID = in.readInt();
    }

    public static final Parcelable.Creator<ImageHomeUrlReq> CREATOR = new Parcelable.Creator<ImageHomeUrlReq>() {
        @Override
        public ImageHomeUrlReq createFromParcel(Parcel source) {
            return new ImageHomeUrlReq(source);
        }

        @Override
        public ImageHomeUrlReq[] newArray(int size) {
            return new ImageHomeUrlReq[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageHomeUrlReq that = (ImageHomeUrlReq) o;

        if (SID != that.SID) return false;
        return NM.equals(that.NM);

    }

    @Override
    public int hashCode() {
        int result = SID;
        result = 31 * result + NM.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ImageHomeUrlReq{" +
                "SID=" + SID +
                ", SN=" + SN +
                ", timeout=" + timeout +
                ", NM='" + NM + '\'' +
                ", CID=" + CID +
                '}';
    }
}
