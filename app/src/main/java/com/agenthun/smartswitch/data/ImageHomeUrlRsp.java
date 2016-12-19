package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 03:31.
 */

public class ImageHomeUrlRsp implements Parcelable {
    /**
     * url : http://www.gn-smart.cn/image/F0002C0004
     * result : 1
     * SN : 16
     * CID : 30362
     * SID : 975921257
     * NM : ImageHomeUrlRsp
     */

    private String url;
    private int result;
    private int SN;
    private int CID;
    private int SID;
    private String NM;

    public ImageHomeUrlRsp(String url, int result, int SN, int CID, int SID, String NM) {
        this.url = url;
        this.result = result;
        this.SN = SN;
        this.CID = CID;
        this.SID = SID;
        this.NM = NM;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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
        dest.writeString(this.url);
        dest.writeInt(this.result);
        dest.writeInt(this.SN);
        dest.writeInt(this.CID);
        dest.writeInt(this.SID);
        dest.writeString(this.NM);
    }

    protected ImageHomeUrlRsp(Parcel in) {
        this.url = in.readString();
        this.result = in.readInt();
        this.SN = in.readInt();
        this.CID = in.readInt();
        this.SID = in.readInt();
        this.NM = in.readString();
    }

    public static final Parcelable.Creator<ImageHomeUrlRsp> CREATOR = new Parcelable.Creator<ImageHomeUrlRsp>() {
        @Override
        public ImageHomeUrlRsp createFromParcel(Parcel source) {
            return new ImageHomeUrlRsp(source);
        }

        @Override
        public ImageHomeUrlRsp[] newArray(int size) {
            return new ImageHomeUrlRsp[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageHomeUrlRsp that = (ImageHomeUrlRsp) o;

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
        return "ImageHomeUrlRsp{" +
                "url='" + url + '\'' +
                ", result=" + result +
                ", SN=" + SN +
                ", CID=" + CID +
                ", SID=" + SID +
                ", NM='" + NM + '\'' +
                '}';
    }
}
