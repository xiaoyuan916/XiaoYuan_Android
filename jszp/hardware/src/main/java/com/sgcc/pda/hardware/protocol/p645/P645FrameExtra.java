package com.sgcc.pda.hardware.protocol.p645;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.sgcc.pda.hardware.protocol.BaseFrame;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;

public class P645FrameExtra extends BaseFrame {
    private static final String FRAME_HEAD = "68";
    private static final String FRAME_TAIL = "16";
    private String meterAddress;

    public P645FrameExtra(String meterAddress, String control, String data) {
        this.setFrameHead("68");
        this.setFrameTail("16");
        this.setMeterAddress(meterAddress);
        this.setControl(control);
        this.setData(data);
    }

    public String getMeterAddress() {
        return this.meterAddress;
    }

    public void setMeterAddress(String meterAddress) {
        this.meterAddress = meterAddress;
    }

    public void setData(String data) {
        if(data == null) {
            data = "";
        }

        super.setData(data);
        super.setDataLength(this.computeDataLength());
    }

    public void parseUp(String data) {
        String frameString = this.getFrameString(data);
        if(frameString == null) {
            this.setException(ErrorManager.ErrorType.P645FrameError.getValue());
        } else if(frameString.equals("com.thinta.eaadp.check_error")) {
            this.setException(ErrorManager.ErrorType.P645CheckValueError.getValue());
        } else {
            this.setMeterAddress(frameString.substring(2, 14));
            this.setControl(frameString.substring(16, 18));
            this.setDataLength(frameString.substring(18, 20));
            this.setData(frameString.substring(20, frameString.length() - 4));
            this.setCheck(frameString.substring(frameString.length() - 4, frameString.length() - 2));
            int error = this.checkFrameError();
            if(error != 0) {
                this.setException(error);
            }
        }

    }

    public void parseDown(String data) {
        this.parseUp(data);
        if(this.getMeterAddress() != null) {
            this.setMeterAddress(DataConvert.strReverse(this.getMeterAddress(), 0, this.getMeterAddress().length()));
        }

        if(this.getData() != null) {
            this.setData(DataConvert.stringHexMinusEach(this.getData(), (byte)51));
        }

    }

    public String computeCheckValue() {
        int error = this.checkFrameError();
        if(error != 0) {
            this.setException(error);
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getFrameHead()).append(this.getMeterAddress() == null?"":this.getMeterAddress()).append(this.getFrameHead()).append(this.getControl() == null?"":this.getControl()).append(this.getDataLength() == null?"":this.getDataLength()).append(this.getData() == null?"":this.getData());
            return DataConvert.getSumValue(sb.toString());
        }
    }

    public String computeDataLength() {
        return DataConvert.getHexLength(this.getData(), 1);
    }

    public String getFrameHead() {
        return "68";
    }

    public String getFrameTail() {
        return "16";
    }

    public int checkFrameError() {
        if(this.getMeterAddress() != null && this.getMeterAddress().length() == 12 && this.getControl() != null && this.getControl().length() == 2 && this.getDataLength() != null && this.getDataLength().length() == 2) {
            int dataLength;
            try {
                dataLength = Integer.parseInt(this.getDataLength(), 16);
            } catch (Exception var6) {
                return ErrorManager.ErrorType.P645FrameError.getValue();
            }

            if(this.getData() != null && this.getData().length() == dataLength * 2) {
                byte mask = 31;

                int control;
                try {
                    control = Integer.parseInt(this.getControl(), 16) & mask;
                } catch (Exception var5) {
                    return ErrorManager.ErrorType.P645FrameError.getValue();
                }

                return (control == 17 || control == 18 || control == 19) && dataLength > 200? ErrorManager.ErrorType.P645DataLengthError.getValue():((control == 20 || control == 21) && dataLength > 50? ErrorManager.ErrorType.P645DataLengthError.getValue():0);
            } else {
                return ErrorManager.ErrorType.P645FrameError.getValue();
            }
        } else {
            return ErrorManager.ErrorType.P645FrameError.getValue();
        }
    }

    private String getFrameString(String data) {
        data = data.trim();
        data = data.toUpperCase();
        if(!data.contains("68")) {
            return null;
        } else if(!data.contains("16")) {
            return null;
        } else if(data.length() % 2 != 1 && data.length() / 2 >= 16) {
            int frameBegin = 0;
            String errorString = "";

            while(true) {
                frameBegin = data.indexOf("68", frameBegin);
                if(frameBegin < 0 || frameBegin + 16 >= data.length()) {
                    return errorString.equals("com.thinta.eaadp.check_error")?"com.thinta.eaadp.check_error":null;
                }

                if(!data.substring(frameBegin + 14, frameBegin + 16).equals("68")) {
                    ++frameBegin;
                } else if(frameBegin + 20 >= data.length()) {
                    ++frameBegin;
                } else {
                    int dataLength;
                    try {
                        dataLength = Integer.parseInt(data.substring(frameBegin + 18, frameBegin + 20), 16);
                    } catch (Exception var6) {
                        ++frameBegin;
                        continue;
                    }

                    if(frameBegin + 20 + dataLength * 2 + 4 > data.length()) {
                        ++frameBegin;
                    } else if(!data.substring(frameBegin + 20 + dataLength * 2 + 2, frameBegin + 20 + dataLength * 2 + 4).equals("16")) {
                        ++frameBegin;
                    } else {
                        if(data.substring(frameBegin + 20 + dataLength * 2, frameBegin + 20 + dataLength * 2 + 2).equals(DataConvert.getSumValue(data.substring(frameBegin, frameBegin + 20 + dataLength * 2)))) {
                            return data.substring(frameBegin, frameBegin + 20 + dataLength * 2 + 4);
                        }

                        errorString = "com.thinta.eaadp.check_error";
                        ++frameBegin;
                    }
                }
            }
        } else {
            return null;
        }
    }

    public String getString() {
        if(this.getException() != 0) {
            return null;
        } else {
            int error = this.checkFrameError();
            if(error != 0) {
                this.setException(error);
                return null;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(this.getFrameHead());
                sb.append(DataConvert.strReverse(this.getMeterAddress(), 0, this.getMeterAddress().length()));
                sb.append(this.getFrameHead());
                sb.append(this.getControl());
                sb.append(this.getDataLength());
                this.setData(DataConvert.stringHexAddEach(this.getData(), (byte)51));
                sb.append(this.getData());
                String check = this.computeCheckValue();
                if(check == null) {
                    this.setException(ErrorManager.ErrorType.P645FrameError.getValue());
                    return null;
                } else {
                    this.setCheck(check);
                    sb.append(this.getCheck());
                    sb.append(this.getFrameTail());
                    return sb.toString();
                }
            }
        }
    }
}
