package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * 物流派车
 */
public class LogisticsSendResultEntity extends BaseEntity{
    private LogisticsSendResult result;

    public class LogisticsSendResult{
        private int sendNum;//已派车数量
        private int notSendNum;//未派车数量

        private List<LogisticsDistAutoesItem> logisticsDistAutoes;

        public int getSendNum() {
            return sendNum;
        }

        public void setSendNum(int sendNum) {
            this.sendNum = sendNum;
        }

        public int getNotSendNum() {
            return notSendNum;
        }

        public void setNotSendNum(int notSendNum) {
            this.notSendNum = notSendNum;
        }

        public List<LogisticsDistAutoesItem> getLogisticsDistAutoes() {
            return logisticsDistAutoes;
        }

        public void setLogisticsDistAutoes(List<LogisticsDistAutoesItem> logisticsDistAutoes) {
            this.logisticsDistAutoes = logisticsDistAutoes;
        }
    }

    public LogisticsSendResult getResult() {
        return result;
    }

    public void setResult(LogisticsSendResult result) {
        this.result = result;
    }
}
