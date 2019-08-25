package com.xiao.project.ui.searchfilter;

import org.greenrobot.greendao.annotation.Id;

/**
 * @ClassName SearchBean
 * @Description TODO
 * @Author Administrator
 * @Date 2019/8/25 14:04
 * @Version 1.0
 */
public class SearchBean {
    private String id;
    private boolean isChecked;
    private String str;

    public SearchBean(String id, boolean isChecked, String str) {
        this.id = id;
        this.isChecked = isChecked;
        this.str = str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
