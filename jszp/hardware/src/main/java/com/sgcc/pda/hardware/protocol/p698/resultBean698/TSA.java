package com.sgcc.pda.hardware.protocol.p698.resultBean698;


import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/**
 * @author ZXS
 * TSA类型数据
 *
 */
public class TSA {
	/**
	 * 总长度
	 */
	private Integer totalLength;
	/**
	 * 服务器地址,最长16字节.
	 */
	private String serviceAdds;
	
	public Integer getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}
	
	public String getServiceAdds() {
		return serviceAdds;
	}
	public void setServiceAdds(String serviceAdds) {
		this.serviceAdds = serviceAdds;
	}
	/**
	 * @param bytes
	 * 通过字节数组，为其属性赋值
	 */
	public void valuAtion(byte[] bytes){
		//除去一个长度，一个首字节，后面是实际地址
		byte[] saBytes=new byte[this.getTotalLength()-2];
		System.arraycopy(bytes, 2, saBytes, 0, saBytes.length);
		this.serviceAdds = CommUtils.byteToHexString(saBytes);
		
	}
	@Override
	public String toString() {
		return serviceAdds;
	}
	/*public static void main(String[] args) {
		String v= "06040000000122";
		byte[] b = CommUtils.hex2Binary(v);
		TSA tsa=new TSA();
		tsa.setTotalLength(new Integer(b[0])+1);
		tsa.valuAtion(b);
		System.out.println(tsa);
	}*/
}

