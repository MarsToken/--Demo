package com.example.administrator.myapplication.testlist;

/**
 * Created by Administrator on 2018/4/19.
 */
public class CommentBean {
    public float score = 0;
    public String comment = "";
    public String name;
    public boolean isEdit = true;

    public CommentBean(String name) {
        this.name = name;
    }
}
