package com.alp.study.model;
//need change and fix

import io.requery.*;

@Entity
public interface DestinationInformation {


    @Key
    @Generated
    int getId();

    @Column(length = 20)
    String getOriginCountry();
    @Column(length = 20)
    String getOriginStatePost();
    @Column(length = 20)
    String getOriginCity();

    double getOriginLatitude();
    double getOriginLongitude();


    @Column(length = 20)
    String getDestinationCountry();
    @Column(length = 20)
    String getDestinationStatePost();
    @Column(length = 20)
    String getDestinationCity();

    double getDestinationLatitude();
    double getDestinationLongitude();

    @ForeignKey(references = BasicTransferInformation.class)
    @Key
    int getTransferId();

}

