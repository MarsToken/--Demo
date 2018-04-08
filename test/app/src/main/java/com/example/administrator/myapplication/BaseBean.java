package com.example.administrator.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/29.
 */
public class BaseBean {
    private String name;
    private int age;
    private List<String> list;
    private Map<String, String> map;
    /**
     * Content : [{"code":"11","name":"朵朵保修"},{"code":"1","name":"维保"},{"code":"2","name":"维修"},
     * {"code":"3","name":"巡检"},{"code":"4","name":"运行"}]
     * Count : 5
     * Result : success
     */
    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

}
