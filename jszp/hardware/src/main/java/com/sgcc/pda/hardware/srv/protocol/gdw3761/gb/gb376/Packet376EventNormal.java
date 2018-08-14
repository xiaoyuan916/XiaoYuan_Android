package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdDataBuffer;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class Packet376EventNormal extends PmPacket376EventBase{

    @Override
    protected void DecodeEventDetail(BcdDataBuffer eventData, int len) {
        switch(erc)
        {
            case 4:{  //开关量状态变位
                this.eventDetail = BcdUtils.bytesToBitSetString(eventData.getBytes(len));
                break;
            }
            case 14:{//停上电事件
                this.eventDetail = BcdUtils.binArrayToString(eventData.getBytes(len));
                break;
            }
        }
        
    }
}
