package com.sgcc.pda.jszp.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/6
 */
public class JszpDpsResultEntity extends BaseEntity {
   private List<JszpOrgListEntity> orgList;

   public List<JszpOrgListEntity> getOrgList() {
      return orgList;
   }

   public void setOrgList(List<JszpOrgListEntity> orgList) {
      this.orgList = orgList;
   }
}
