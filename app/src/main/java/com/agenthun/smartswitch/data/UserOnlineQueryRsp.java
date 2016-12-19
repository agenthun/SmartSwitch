package com.agenthun.smartswitch.data;

import java.util.Map;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 01:33.
 */

public class UserOnlineQueryRsp {


    /**
     * onlineUsers : {"975921257":{"createTime":"1481486476072","clientName":"GT-P3100","mac":"352123052298","lng":"0.0","clientIp":"180.160.47.220","clientId":"F0002C0004","lat":"0.0"}}
     * result : 1
     * SN : 15
     * CID : 10252
     * SID : 975921257
     * NM : UserOnlineQueryRsp
     */

    private Map<String, UserBean> onlineUsers;
    private int result;
    private int SN;
    private int CID;
    private int SID;
    private String NM;

    public Map<String, UserBean> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(Map<String, UserBean> onlineUsers) {
        this.onlineUsers = onlineUsers;
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
}
