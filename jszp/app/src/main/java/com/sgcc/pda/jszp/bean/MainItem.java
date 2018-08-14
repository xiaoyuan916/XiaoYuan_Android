package com.sgcc.pda.jszp.bean;

public class MainItem {

    private int img;
    private int title;

    public MainItem(int img, int title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
