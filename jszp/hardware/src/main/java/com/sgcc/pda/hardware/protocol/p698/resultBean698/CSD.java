package com.sgcc.pda.hardware.protocol.p698.resultBean698;

/**
 * @author ZXS
 *	CSD数据类型
 */
public class CSD {
	/**
	 * CSD单元的总长度，默认为1个字节
	 */
	private Integer totalLength=1;
	
	/**
	 * CSD类型选择的值
	 */
	private Integer value;

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * @param bytes
	 * @param oad
	 * 解析CSD数据字节，为CSD赋值
	 */
	public void valuAtion(int choice){
		this.setValue(choice);
	}

	@Override
	public String toString() {
		return ""+this.getValue();
	}
	
	
}
