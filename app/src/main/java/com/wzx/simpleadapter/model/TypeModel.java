package com.wzx.simpleadapter.model;


/**
 * Created by WangZhanXian on 2018/4/24.
 */

public class TypeModel {

    String name;

    int drawableId;

    public TypeModel(String name){
        this.name = name;
    }

    public TypeModel(int drawableId){
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }
}
