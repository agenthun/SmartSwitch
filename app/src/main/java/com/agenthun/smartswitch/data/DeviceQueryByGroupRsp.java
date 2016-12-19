package com.agenthun.smartswitch.data;

import java.util.List;
import java.util.Map;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 03:34.
 */

public class DeviceQueryByGroupRsp {

    /**
     * subRspMap : {"2710":{"totalNum":1,"pageSize":100,"pageNum":1,"itemList":[{"mac":"ACCF23218698","needRemoteControl":false,"factoryId":1,"type":257,"hardwareVer":"v1.0","softwareVer":"1.0.0.4a.gn13_0917","bindTime":"20150301120757718","totalOnlineTime":34353280417,"gpsLat":0,"gpsLng":0,"image":"icon_default_photo1.png","online":true,"id":1514,"pid":2710,"nodeType":1,"orderNo":0,"name":"公牛智能插座"}]}}
     * result : 1
     * SN : 17
     * CID : 30082
     * SID : 975921257
     * NM : DeviceQueryByGroupRsp
     */

    private Map<String, DevicePageListRsp> subRspMap;
    private int result;
    private int SN;
    private int CID;
    private int SID;
    private String NM;

    public DeviceQueryByGroupRsp(Map<String, DevicePageListRsp> subRspMap, int result, int SN, int CID, int SID, String NM) {
        this.subRspMap = subRspMap;
        this.result = result;
        this.SN = SN;
        this.CID = CID;
        this.SID = SID;
        this.NM = NM;
    }

    public Map<String, DevicePageListRsp> getSubRspMap() {
        return subRspMap;
    }

    public void setSubRspMap(Map<String, DevicePageListRsp> subRspMap) {
        this.subRspMap = subRspMap;
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

    public static class DevicePageListRsp {
        /**
         * totalNum : 1
         * pageSize : 100
         * pageNum : 1
         * itemList : [{"mac":"ACCF23218698","needRemoteControl":false,"factoryId":1,"type":257,"hardwareVer":"v1.0","softwareVer":"1.0.0.4a.gn13_0917","bindTime":"20150301120757718","totalOnlineTime":34353280417,"gpsLat":0,"gpsLng":0,"image":"icon_default_photo1.png","online":true,"id":1514,"pid":2710,"nodeType":1,"orderNo":0,"name":"公牛智能插座"}]
         */

        private int totalNum;
        private int pageSize;
        private int pageNum;
        private List<Device> itemList;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public List<Device> getItemList() {
            return itemList;
        }

        public void setItemList(List<Device> itemList) {
            this.itemList = itemList;
        }
    }
}
