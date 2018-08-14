package com.sgcc.pda.jszp.bean;

public class OptionItem {
    private String name;
    private boolean selected;

    public OptionItem(String name) {
        this.name = name;
    }

    public OptionItem(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
