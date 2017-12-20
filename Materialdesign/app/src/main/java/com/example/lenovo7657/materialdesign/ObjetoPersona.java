package com.example.lenovo7657.materialdesign;

/**
 * Created by Lenovo7657 on 07/10/2017.
 */

public class ObjetoPersona {

    String name;
    String age;
    int photoId;

    ObjetoPersona(){ }

    ObjetoPersona(String name, String age, int photoId){
        this.name=name;
        this.age=age;
        this.photoId=photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    }