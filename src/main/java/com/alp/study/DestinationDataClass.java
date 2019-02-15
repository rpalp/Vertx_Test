package com.alp.study;

public class DestinationDataClass {

    private int id;

    private String originCountry;
    private String originStatePost;
    private String originCity;

    private double originLatitude;
    private double originLongitude;


    private String destinationCountry;
    private String destinationStatePost;
    private String destinationCity;

    private double destinationLatitude;
    private double destinationLongitude;

    private int transferId;

    public DestinationDataClass(int id, String originCountry, String originStatePost, String originCity, double originLatitude,
                                double originLongitude, String destinationCountry, String destinationStatePost,
                                String destinationCity, double destinationLatitude, double destinationLongitude, int transferId) {
        this.id = id;
        this.originCountry = originCountry;
        this.originStatePost = originStatePost;
        this.originCity = originCity;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationCountry = destinationCountry;
        this.destinationStatePost = destinationStatePost;
        this.destinationCity = destinationCity;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.transferId = transferId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginStatePost() {
        return originStatePost;
    }

    public void setOriginStatePost(String originStatePost) {
        this.originStatePost = originStatePost;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public double getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }

    public double getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(double originLongitude) {
        this.originLongitude = originLongitude;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getDestinationStatePost() {
        return destinationStatePost;
    }

    public void setDestinationStatePost(String destinationStatePost) {
        this.destinationStatePost = destinationStatePost;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }




}
