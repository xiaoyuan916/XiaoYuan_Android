package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

/***
 *   ROAD 记录型对象属性描述符   ----------描述记录型对象中的一个或若干个关联对象属性
 *   ROAD 对象属性之间用":" 分割
 *   ROAD 第一个 为记录型对象   后面的为关联对象属性
 */
public class ROAD extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}
	public    byte []   getBytes(){
		  try{
				byte  []    value  = CommUtils.hex2Binary(this.getInputValue());
				// +1表示 记录型对象关联对象个数
				byte   []   b =  new  byte[value.length+1];  
				System.arraycopy(value, 0, b, 0, 4);
				// 关联OAD 个数
				int count =value.length/4-1;
				System.arraycopy(CommUtils.hex2Binary(Integer.toHexString(count)), 0, b, 4, 1);
				if(count >=1){
					int  datalen =5;  //第一个关联对象开始的位置；
					int  mlen =4;  //  value 下标
					for(int i=1; i<=count; i++){
						System.arraycopy(value, mlen, b, datalen, 4);
						datalen +=4;
						mlen +=4;
					}
				}
				return  b;
			} catch (Exception e) {
				throw new RuntimeException("IPharesItem698数据格式解析类：["
						+ this.getClass().getName()
						+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
			}
	}
}
