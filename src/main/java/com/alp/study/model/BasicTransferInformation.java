package com.alp.study.model;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

import java.sql.Date;

@Entity
public interface BasicTransferInformation {


    @Key @Generated
    int getId();

    @Column(length = 20)
    String getName();

    @Column(length = 20)
    String getPost();

    @Column(length = 20)
    Date getDateStart();

    @Column(length = 20)
    Date getDateEnd();


}