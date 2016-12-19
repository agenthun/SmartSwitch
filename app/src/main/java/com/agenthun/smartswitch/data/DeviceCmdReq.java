package com.agenthun.smartswitch.data;

import java.util.Map;

/**
 * @project SmartSwitch
 * @authors agenthun
 * @date 2016/12/17 21:53.
 */

public class DeviceCmdReq {
    public static final String FUNCTION_SET = "DeviceSetReq";
    public static final String FUNCTION_GET = "DeviceGetReq";

    public static final int FUNCTION_SET_CID = 20031;
    public static final int FUNCTION_GET_CID = 20021;

    public static final String CMD_SET_STATUS_CLOSE = "FE000011010001FFFF1001FFFF2001FFFF3001FFFF";
    public static final String CMD_SET_STATUS_OPEN = "FE000011010002FFFF1002FFFF2002FFFF3002FFFF";
    public static final String CMD_GET_STATUS = "FD00000102";
    public static final String CMD_GET_INTERVAL = "FD00000105";

    /**
     * SID : 1671757927
     * SN : 30
     * input : {"ACCF23218698":"FE000011010002FFFF1002FFFF2002FFFF3002FFFF"}
     * timeout : 5000
     * NM : DeviceSetReq
     * CID : 20031
     */

    private int SID;
    private int SN;
    private Map<String, String> input;
    private int timeout;
    private String NM;
    private int CID;

    public DeviceCmdReq(int SID, Map<String, String> input, String NM, int CID) {
        this.SID = SID;
        this.SN = 98;
        this.input = input;
        this.timeout = 5000;
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

    public Map<String, String> getInput() {
        return input;
    }

    public void setInput(Map<String, String> input) {
        this.input = input;
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
}
