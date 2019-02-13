package com.alp.study;

import java.util.concurrent.atomic.AtomicInteger;


/*
public class DataStructure {


    BasicInformation basicInformation;
    DestinationInformation destinationInformation;

    public DataStructure(String name, String post, Date dateStart, Date dateEnd, String adressStart, String adressEnd) {
        this.basicInformation = new BasicInformation(name, post, dateStart, dateEnd);
        this.destinationInformation = new DestinationInformation(this.basicInformation, adressStart, adressEnd);

    }
}
*/


public class DataStructure {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final int id;
    private String name;
    private String post;
    private String dateStart;
    private String dateEnd;
    private String addressStart;
    private String addressEnd;


    public DataStructure(String name, String post, String dateStart, String dateEnd, String addressStart, String addressEnd) {
        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.post = post;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
    }

    public DataStructure(String name, String post, String dateStart, String addressStart, String addressEnd) {
        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.post = post;
        this.dateStart = dateStart;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
    }

    //Наличие геттеров, сеттеров и дурацкого конструктора необходимо для работы преобразования из/в json

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


    public DataStructure() {
        this.id = COUNTER.getAndIncrement();
    }



}

