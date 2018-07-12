package com.example.administrator.myapplication.testDemo.highcharts.bean;

/**
 * Created by wangmaobo on 2018/7/5.
 */
public class JsData {
    public String[] categories;
    public YData[] series;

    public static class YData {
        public String name;
        public int[] data;
    }
}
