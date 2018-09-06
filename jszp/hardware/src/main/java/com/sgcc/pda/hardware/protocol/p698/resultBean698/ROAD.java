package com.sgcc.pda.hardware.protocol.p698.resultBean698;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ZXS
 * ROAD数据类型
 */
public class ROAD {
	/**
	 * ROAD数据类型的总长度
	 */
	private Integer totalLength;
	/**
	 * 对象属性描述符
	 */
	private OAD oad=new OAD();
	/**
	 * 关联对象属性描述符,是一个列表
	 */
	private List<OAD> relationOad=new ArrayList<OAD>();
	
	public Integer getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}
	public OAD getOad() {
		return oad;
	}
	public void setOad(OAD oad) {
		this.oad = oad;
	}
	public List<OAD> getRelationOad() {
		return relationOad;
	}
	public void setRelationOad(List<OAD> relationOad) {
		this.relationOad = relationOad;
	}
	//通过关联对象属性描述符的个数，获取字节总长度
	public Integer getTotalLengthByByte(int i) {
		return this.getOad().getTotalLength()+1+i*this.getOad().getTotalLength();
	}
	
	/**
	 * @param bytes
	 * 解析所占的字节数组，给各属性赋值
	 */
	public void valuAtion(byte[] bytes){
		byte[] oadBytes=new byte[this.getOad().getTotalLength()];
		System.arraycopy(bytes, 0, oadBytes, 0, oadBytes.length);
		this.getOad().valuAtion(oadBytes);
		int size=bytes[4];
		for(int i = 0;i<size;i++){
			byte[] roBytes=new byte[this.getOad().getTotalLength()];
			System.arraycopy(bytes, 5+i*4, roBytes, 0, roBytes.length);
			OAD oad = new OAD();
			oad.valuAtion(bytes);
			this.getRelationOad().add(oad);
		}
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(this.getOad().toString()+" ");
		for(int i=0;i<this.getRelationOad().size();i++){
			if(i==(this.getRelationOad().size()-1)){
				sb.append(this.getRelationOad().get(i).toString());
			}else{
				sb.append(this.getRelationOad().get(i).toString()+" ");
			}
		}
		return sb.toString();
	}
	/*public static void main(String[] args) {
		//20 00 02 00
		String v= "2000020003200002002000020020000200";
		byte[] b = CommUtils.hex2Binary(v);
		ROAD road=new ROAD();
		road.getTotalLengthByByte(b[4]);
		road.valuAtion(b);
		System.out.println(road);
	}*/
	
	
}
