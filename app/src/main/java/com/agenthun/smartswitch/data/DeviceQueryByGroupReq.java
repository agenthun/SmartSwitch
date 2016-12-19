package com.agenthun.smartswitch.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 03:32.
 */

public class DeviceQueryByGroupReq {
    /**
     * SID : 975921257
     * SN : 17
     * subReqList : [{"pageSize":100,"deviceGroupId":-1,"pageNum":1}]
     * timeout : 5000
     * NM : DeviceQueryByGroupReq
     * CID : 30081
     */

    private int SID;
    private int SN;
    private List<DevicePageListReq> subReqList;
    private int timeout;
    private String NM;
    private int CID;

    public DeviceQueryByGroupReq(int SID, int SN, List<DevicePageListReq> subReqList, int timeout, String NM, int CID) {
        this.SID = SID;
        this.SN = SN;
        this.subReqList = subReqList;
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

    public List<DevicePageListReq> getSubReqList() {
        return subReqList;
    }

    public void setSubReqList(List<DevicePageListReq> subReqList) {
        this.subReqList = subReqList;
    }

    public static class DevicePageListReq implements Parcelable {
        /**
         * pageSize : 100
         * deviceGroupId : -1
         * pageNum : 1
         */

        private int pageSize;
        private int deviceGroupId;
        private int pageNum;

        public DevicePageListReq(int pageSize, int deviceGroupId, int pageNum) {
            this.pageSize = pageSize;
            this.deviceGroupId = deviceGroupId;
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getDeviceGroupId() {
            return deviceGroupId;
        }

        public void setDeviceGroupId(int deviceGroupId) {
            this.deviceGroupId = deviceGroupId;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.pageSize);
            dest.writeInt(this.deviceGroupId);
            dest.writeInt(this.pageNum);
        }

        protected DevicePageListReq(Parcel in) {
            this.pageSize = in.readInt();
            this.deviceGroupId = in.readInt();
            this.pageNum = in.readInt();
        }

        public static final Parcelable.Creator<DevicePageListReq> CREATOR = new Parcelable.Creator<DevicePageListReq>() {
            @Override
            public DevicePageListReq createFromParcel(Parcel source) {
                return new DevicePageListReq(source);
            }

            @Override
            public DevicePageListReq[] newArray(int size) {
                return new DevicePageListReq[size];
            }
        };
    }
}
