package com.cviac.nheart.nheartapp.restapi;

/**
 * Created by Cviac on 27/02/2017.
 */
public class PushMessageInfo {

    private DataInfo data;
    private String to;

    public PushMessageInfo() {
    }

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static class DataInfo {
        private String type; //chat,invite,location
        private String msg;
        private String senderid;
        private String sendername;
        private String msgId;

        public DataInfo() {
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getSenderid() {
            return senderid;
        }

        public void setSenderid(String senderid) {
            this.senderid = senderid;
        }

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}