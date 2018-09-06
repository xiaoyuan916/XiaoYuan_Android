package com.sgcc.pda.hardware.event;

import com.sgcc.pda.hardware.frame.bluetooth.BlueDeviceEntity;

import java.util.List;

/**
 * Created by Yang on 2017/5/2.
 */
public class BlueEntityEvent {
    private final List<BlueDeviceEntity> blueDeviceEntity;

    public BlueEntityEvent(List<BlueDeviceEntity> blueDeviceEntity) {
        this.blueDeviceEntity = blueDeviceEntity;
    }

    public List<BlueDeviceEntity> getStr(){
        return blueDeviceEntity;
    }
}
