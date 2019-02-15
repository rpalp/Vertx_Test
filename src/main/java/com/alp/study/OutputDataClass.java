package com.alp.study;

public class OutputDataClass {



    private int transferId = -1;
    private String name = "-1";
    private String post = "-1";
    private String dateStart = "-1";
    private String dateEnd = "-1";

    private int destinationRowId = -1;

    private String originCountry = "-1";
    private String originStatePost = "-1";
    private String originCity = "-1";
    private double originLatitude = -1;
    private double originLongitude = -1;


    private String destinationCountry = "-1";
    private String destinationStatePost = "-1";
    private String destinationCity = "-1";
    private double destinationLatitude = -1;
    private double destinationLongitude = -1;

    public OutputDataClass(InputDataClass inputDataClass, DestinationDataClass destinationDataClass) {

        this.transferId = inputDataClass.getId();
        this.name = inputDataClass.getName();
        this.post = inputDataClass.getPost();
        this.dateStart = inputDataClass.getDateStart();
        this.dateEnd = inputDataClass.getDateEnd();
        this.destinationRowId = destinationDataClass.getId();
        this.originCountry = destinationDataClass.getOriginCountry();
        this.originStatePost = destinationDataClass.getOriginStatePost();
        this.originCity = destinationDataClass.getOriginCity();
        this.originLatitude = destinationDataClass.getOriginLatitude();
        this.originLongitude = destinationDataClass.getOriginLongitude();
        this.destinationCountry = destinationDataClass.getDestinationCountry();
        this.destinationStatePost = destinationDataClass.getDestinationStatePost();
        this.destinationCity = destinationDataClass.getDestinationCity();
        this.destinationLatitude = destinationDataClass.getDestinationLatitude();
        this.destinationLongitude = destinationDataClass.getDestinationLongitude();
    }

    public OutputDataClass(InputDataClass inputDataClass) {

        this.transferId = inputDataClass.getId();
        this.name = inputDataClass.getName();
        this.post = inputDataClass.getPost();
        this.dateStart = inputDataClass.getDateStart();
        this.dateEnd = inputDataClass.getDateEnd();
    }

    public OutputDataClass() {
    }

    public int getTransferId() {
        return transferId;
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

    public int getDestinationRowId() {
        return destinationRowId;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getOriginStatePost() {
        return originStatePost;
    }

    public String getOriginCity() {
        return originCity;
    }

    public double getOriginLatitude() {
        return originLatitude;
    }

    public double getOriginLongitude() {
        return originLongitude;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getDestinationStatePost() {
        return destinationStatePost;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }
}
