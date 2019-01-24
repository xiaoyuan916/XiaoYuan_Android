package com.xiao.jszpdemo.bean;

public class DemoEntity extends BaseEntity {
    private String demoString;
    private DemoTwo demo;

    public String getDemoString() {
        return demoString;
    }

    public void setDemoString(String demoString) {
        this.demoString = demoString;
    }

    public DemoTwo getDemo() {
        return demo;
    }

    public void setDemo(DemoTwo demo) {
        this.demo = demo;
    }

    public class DemoTwo {
        private String demoStringTwo;

        public String getDemoStringTwo() {
            return demoStringTwo;
        }

        public void setDemoStringTwo(String demoStringTwo) {
            this.demoStringTwo = demoStringTwo;
        }
    }
}
