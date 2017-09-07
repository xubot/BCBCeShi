package com.example.bckj.projectbcb.Bean;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ListViewBean_1 {
    private String title;
    private String time;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ListViewBean_1(String title, String time, int img) {
        this.title = title;
        this.time = time;
        this.img = img;
    }
}
