package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 22:02.
 */

public class DeviceCmdRsp implements Parcelable {

    public static final String CODE_STATUS_CLOSE_MANUAL = "FD000011020002FFFF1002FFFF2002FFFF3001FFFF000000";
    public static final String CODE_STATUS_CLOSE_AUTO = "FD000011020001FFFF1001FFFF2001FFFF3001FFFF000000";

    public static final String CODE_STATUS_OPEN_MANUAL = "FD000011020001FFFF1001FFFF2001FFFF3002FFFF000000";
    public static final String CODE_STATUS_OPEN_AUTO = "FD000011020002FFFF1002FFFF2002FFFF3002FFFF000000";

    public static final String CODE_IS_INTERVAL_INITIAL = "FD00000205FF0000";
    public static final String CODE_IS_INTERVAL_FALSE = "FD00001805";
    public static final String CODE_IS_INTERVAL_TRUE = "FD00001705";
    public static final String CODE_IS_TIME_SLOT_INTERVAL_FALSE = "FD00002C05";
    public static final String CODE_IS_TIME_SLOT_INTERVAL_TRUE = "FD00002905";

    public static final String CODE_CONFIG_CLOSE = "0001FFFF1001FFFF2001FFFF3001FFFF";
    public static final String CODE_CONFIG_OPEN = "0002FFFF1002FFFF2002FFFF3002FFFF";


    /**
     * output : {"ACCF23218698":"FE000011010001FFFF1001FFFF2001FFFF3001FFFF000000"}
     * result : 1
     * SN : 30
     * CID : 20032
     * SID : 1871316616
     * NM : DeviceSetRsp
     */

    private Map<String, String> output;
    private int result;
    private int SN;
    private int CID;
    private int SID;
    private String NM;

    public Map<String, String> getOutput() {
        return output;
    }

    public void setOutput(Map<String, String> output) {
        this.output = output;
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
        dest.writeInt(this.output.size());
        for (Map.Entry<String, String> entry : this.output.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.result);
        dest.writeInt(this.SN);
        dest.writeInt(this.CID);
        dest.writeInt(this.SID);
        dest.writeString(this.NM);
    }

    public DeviceCmdRsp() {
    }

    protected DeviceCmdRsp(Parcel in) {
        int outputSize = in.readInt();
        this.output = new HashMap<String, String>(outputSize);
        for (int i = 0; i < outputSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.output.put(key, value);
        }
        this.result = in.readInt();
        this.SN = in.readInt();
        this.CID = in.readInt();
        this.SID = in.readInt();
        this.NM = in.readString();
    }

    public static final Parcelable.Creator<DeviceCmdRsp> CREATOR = new Parcelable.Creator<DeviceCmdRsp>() {
        @Override
        public DeviceCmdRsp createFromParcel(Parcel source) {
            return new DeviceCmdRsp(source);
        }

        @Override
        public DeviceCmdRsp[] newArray(int size) {
            return new DeviceCmdRsp[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceCmdRsp that = (DeviceCmdRsp) o;

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
        StringBuilder sb = new StringBuilder();
        sb.append("DeviceCmdRsp{output=");

        Iterator<Map.Entry<String, String>> iterator = output.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append("[" + entry.getKey() + ": " + entry.getValue() + "]");
        }
        sb.append(", result=" + result +
                ", SN=" + SN +
                ", CID=" + CID +
                ", SID=" + SID +
                ", NM='" + NM + '\'' +
                '}');
        return sb.toString();
    }
}
