package com.xiao.project.ui.vlayout.model;

/**
 * @ClassName ItemBean
 * @Description TODO
 * @Author Administrator
 * @Date 2019/10/8 10:31
 * @Version 1.0
 */
/**
 * Created by HaiyuKing
 * Used 模块下的文章实体类
 */
public class ItemBean {
    public String imageUrl;
    public String urlPath;
    public String title;
    public String id;
    public String order;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
