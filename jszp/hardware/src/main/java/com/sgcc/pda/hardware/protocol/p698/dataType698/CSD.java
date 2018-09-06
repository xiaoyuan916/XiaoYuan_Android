package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class CSD extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}
    //格式：00#OAD     01#OAD(记录型对象)--OAD#OAD...关联的对象
	@Override
	public byte[] getBytes() {
		    try {
		    	int  pos=0;
		    	String value1="";
		    	if(this.getInputValue().contains("|")){   //处理 设置显示列对象 属性2
		    		value1 = this.getInputValue().replace("|", "#");
		    	}
		    	byte  []   temp  = new  byte[1024];
				String[] value  = new String[2];
				if(!("".equals(value1))){
					value = value1.split("#");   //第一个元素表示 对象类型 00【一般对象】 01【记录型对象】  后面的元素表示 OAD  
				}else{
					value = this.getInputValue().replace("|", "#").split("#");   //第一个元素表示 对象类型 00【一般对象】 01【记录型对象】  后面的元素表示 OAD  
				}
				if("00".equals(value[0])){  // 对象属性描述符
					if(value[1].length()>2){
						int index = 0;   //游离下标       
						int  len  =  value.length;
					    for(int i=1; i<len; i++){
					    	temp[index++] = 0x00;
					    	System.arraycopy(CommUtils.hex2Binary(value[i]), 0, temp, 2, 4);
					    	index += 4;
					    }
					    pos = index;
					}else{
						temp[pos++]=0x00;  //非记录型对象可以只有00  表示获取所有列
					}
				}else  {    // 记录型对象属性描述符
					    int  index =6; //组装关联对象数据开始的位置
					    temp[0]=0x01;  //记录型对象属性描述符标识
					    byte  []   mid=CommUtils.hex2Binary(value[1]);
					    System.arraycopy(mid, 0, temp, 1, 4); ////第一个为记录型对象属性描述符   后面的为 关联的对象属性描述符
					    //拼接关联的OAD个数
					    int count = mid.length/4-1;
					    temp[5] = CommUtils.hex2Binary(Integer.toHexString(count))[0]; //value数组中第一个元素 表示对象类型 第二个元素表示记录型对象OAD
					 if(count>=1){
						   int  mlen =4;  
						   for(int  i=1; i<=count; i++){   
						    	System.arraycopy(mid, mlen, temp, index, 4);
						    	mlen +=4;
						    	index +=4;
						    }
					 }
					    
					    pos = index;
				}
			    return   StruUtils.reAssValue(pos, temp);
		    } catch (Exception e) {
				throw new RuntimeException("IPharesItem698数据格式解析类：["
						+ this.getClass().getName()
						+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		   }
	}
}
