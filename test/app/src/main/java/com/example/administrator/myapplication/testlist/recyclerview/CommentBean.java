package com.example.administrator.myapplication.testlist.recyclerview;

/**
 * Created by Administrator on 2018/4/19.
 */
public class CommentBean {
    public float score = 0;
    public String comment = "";
    public String name;
    /**
     * 是否是可编辑状态
     */
    public boolean isEdit = true;
    /**
     * 是否允许改变RatingBar状态
     */
    //public boolean isAllowChange = false;

    public CommentBean(String name) {
        this.name = name;
    }
}
