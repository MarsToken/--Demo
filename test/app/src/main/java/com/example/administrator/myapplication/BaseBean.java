package com.example.administrator.myapplication;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */
public class BaseBean {

    /**
     * Content : [{"code":"11","name":"朵朵保修"},{"code":"1","name":"维保"},{"code":"2","name":"维修"},
     * {"code":"3","name":"巡检"},{"code":"4","name":"运行"}]
     * Count : 5
     * Result : success
     */

    private int Count;
    private String Result;
    private List<ContentBean> Content;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * code : 11
         * name : 朵朵保修
         */

        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
