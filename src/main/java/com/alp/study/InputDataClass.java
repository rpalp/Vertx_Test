package com.alp.study;

import java.util.concurrent.atomic.AtomicInteger;

public class InputDataClass {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private int id;
    private String name;
    private String post;
    private String dateStart;
    private String dateEnd;
    private String addressStart;
    private String addressEnd;


    public InputDataClass(int id , String name, String post, String dateStart, String dateEnd, String addressStart, String addressEnd) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
    }

    public InputDataClass(int id, String name, String post, String dateStart, String dateEnd) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }



    //Наличие геттеров, сеттеров и дурацкого конструктора необходимо для работы преобразования из/в json


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getadressStart() {
        return addressStart;
    }

    public String getaddressEnd() {
        return addressEnd;
    }


    public InputDataClass() {
        this.id = COUNTER.getAndIncrement();
    }



}