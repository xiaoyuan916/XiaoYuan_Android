package com.sgcc.pda.jszp.bean;

import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/7
 */
public class JszpBoxResultEntity extends BaseEntity {
   private List<JszpBoxRecallDetEntity> turnoverBoxRecallDets;

    public List<JszpBoxRecallDetEntity> getTurnoverBoxRecallDets() {
        return turnoverBoxRecallDets;
    }

    public void setTurnoverBoxRecallDets(List<JszpBoxRecallDetEntity> turnoverBoxRecallDets) {
        this.turnoverBoxRecallDets = turnoverBoxRecallDets;
    }
}
