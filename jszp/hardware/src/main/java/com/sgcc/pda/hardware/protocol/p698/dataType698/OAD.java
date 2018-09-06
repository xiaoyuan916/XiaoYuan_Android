package com.sgcc.pda.hardware.protocol.p698.dataType698;


import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;

public class OAD   extends IPharesItem698 {

	@Override
	public String getResStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public   byte []  getBytes(){
		try{
			 byte  []   oad = new   byte[4];
			 DataItem698 dataItem698 =(DataItem698)this.getObj();
			 if(dataItem698 instanceof DataItem698){
				 if(null!=dataItem698.getElementIndex()&&!"".equals(dataItem698.getElementIndex())){
					 byte []   objId =  CommUtils.hex2Binary(dataItem698.getObjIdent());
				     System.arraycopy(objId, 0, oad, 0, 2);  ////对象标识
				     oad[2] = getParam(!"".equals(dataItem698.getObjFeatures())&&dataItem698.getObjFeatures()!=null? Integer.toHexString(Integer.parseInt(dataItem698.getObjFeatures())):"00",  Integer.toHexString(Integer.parseInt(dataItem698.getMemberCode()))); //特征(5--7) ------属性编号(0---4)
				     oad[3] =!"".equals(dataItem698.getElementIndex())&& dataItem698.getElementIndex()!=null ? CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dataItem698.getElementIndex())))[0] : 00; //属性内元素索引
				 }else{
					  byte []   objId =  CommUtils.hex2Binary(dataItem698.getObjIdent());
					  System.arraycopy(objId, 0, oad, 0, 4);  ////对象标识
				 }
			 }else{
				 byte []   objId =  CommUtils.hex2Binary(this.getInputValue());
				  System.arraycopy(objId, 0, oad, 0, 4);  ////对象标识
			 }
		     return  oad;
		} catch (Exception e) {
			throw new RuntimeException("IPharesItem698数据格式解析类：["
					+ this.getClass().getName()
					+ "] 未实现对应的getBytes方法，无法获取下行报文,请核实！");
		}
	}
	
	
	//返回属性标识  及其特征
    private   static   byte   getParam(String fea, String param){
   	 byte    b = (byte) (((CommUtils.hex2Binary(fea)[0]<<5)&0xE0)|(CommUtils.hex2Binary(param)[0]&0x1F));
   	 return  b;
    }
}
