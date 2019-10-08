package com.xiao.project.ui.vlayout.model;

/**
 * @ClassName ModelBean
 * @Description TODO
 * @Author Administrator
 * @Date 2019/10/8 10:22
 * @Version 1.0
 */
import java.util.List;

/**
 * Created by HaiyuKing
 * Used 模块实体类
 */
public class ModelBean {
    public String modelName;
    public String type;
    public List<ItemBean> itemDataList;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ItemBean> getItemDataList() {
        return itemDataList;
    }

    public void setItemDataList(List<ItemBean> itemDataList) {
        this.itemDataList = itemDataList;
    }
}
