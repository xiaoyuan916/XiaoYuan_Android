package com.xiao.project.bean;

import java.io.Serializable;

/**
 * author:xuxiaoyuan
 * date:2019/4/15
 */
public class MainItem implements Serializable {
    private String name;
    private Class activity;

    public MainItem(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
