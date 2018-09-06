package com.sgcc.pda.hardware.protocol.p698.bean698;

import java.util.List;
import java.util.Map;

//定义读取记录型对象属性的记录选择列描述符属性
public class RecordRcsdBean {

	    //Map  中  key 为0   说明是 对象属性描述符    key 为1 说明 是 记录型对象属性描述符
	    /***
	     * 
	     * 如果是记录型对象，则第一个对象为记录型对象，后面的为关联对象
	     * 
	     */
	    private List<Map<String, List<DataItem698>>> recordRcse;

		public List<Map<String, List<DataItem698>>> getRecordRcse() {
			return recordRcse;
		}

		public void setRecordRcse(List<Map<String, List<DataItem698>>> recordRcse) {
			this.recordRcse = recordRcse;
		}
	    
	    
	    
}
