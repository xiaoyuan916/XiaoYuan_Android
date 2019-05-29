package com.xiao.project.bean;

public class Poetry {


    /**
     * code : 200
     * message : 成功!
     * result : {"title":"广教寺","content":"英雄居处终奇特，几度欲来今始能。|骑省文高存古篆，桥公树朽长寒藤。|清流并遶分三涧，翠竹相依住数僧。|沙鸟讵知千载恨，日斜飞下立渔罾。","authors":"张弋"}
     */

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title : 广教寺
         * content : 英雄居处终奇特，几度欲来今始能。|骑省文高存古篆，桥公树朽长寒藤。|清流并遶分三涧，翠竹相依住数僧。|沙鸟讵知千载恨，日斜飞下立渔罾。
         * authors : 张弋
         */

        private String title;
        private String content;
        private String authors;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }
    }
}
