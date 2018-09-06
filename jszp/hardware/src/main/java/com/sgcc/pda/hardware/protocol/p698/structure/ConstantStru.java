package com.sgcc.pda.hardware.protocol.p698.structure;


import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6000128Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6000129Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6000130Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6000Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6001Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6012Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6013Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6014Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6015Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6016Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6017Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6018128Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6018Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru6019Impl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru601EImpl;
import com.sgcc.pda.hardware.protocol.p698.structure.impl.Stru601FImpl;

import java.util.HashMap;
import java.util.Map;

//存储  698协议中  数据类型为structure 的对象标识
public class ConstantStru {
	
	/*保存  数据类型为 structure 的对象标识   <标识、 标识> */
	public static final Map<String, IStructure> STRUCTURE_MAP=new HashMap<String, IStructure>();
	
	static {
		STRUCTURE_MAP.put("6014", new Stru6014Impl());  //设置批量、操作方法批量增加或更新任务配置单元
		STRUCTURE_MAP.put("6015", new Stru6015Impl());   //普通采集方案集
		STRUCTURE_MAP.put("601F", new Stru601FImpl());  //采集规则
		STRUCTURE_MAP.put("601E", new Stru601EImpl());  //设置批量、操作方法批量增加或更新采集规则
		STRUCTURE_MAP.put("6013", new Stru6013Impl());  //任务配置单元
		STRUCTURE_MAP.put("6012", new Stru6012Impl());  //设置批量、操作方法批量增加或更新任务配置单元
		STRUCTURE_MAP.put("6000", new Stru6000Impl());  //设置批量、操作方法批量增加或更新采集档案配置单元  方法 127  128
		STRUCTURE_MAP.put("6000128", new Stru6000128Impl());
		STRUCTURE_MAP.put("6000129", new Stru6000129Impl());
		STRUCTURE_MAP.put("6000130", new Stru6000130Impl());
		STRUCTURE_MAP.put("6001", new Stru6001Impl());  //采集档案配置单元
		STRUCTURE_MAP.put("6018", new Stru6018Impl());  //设置批量、操作方法批量增加或更新透明方案
		STRUCTURE_MAP.put("6018128", new Stru6018128Impl());  //设置批量、操作方法批量增加或更新透明方案
		STRUCTURE_MAP.put("6019", new Stru6019Impl());  //透明方案
		STRUCTURE_MAP.put("6017", new Stru6017Impl());  //事件采集方案
		STRUCTURE_MAP.put("6016", new Stru6016Impl());  //设置批量、操作方法批量增加或更新事件采集方案
	}

}
