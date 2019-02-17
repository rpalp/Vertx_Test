package com.alp.study;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;

import java.util.Arrays;
import java.util.List;

public class GeoData {

    private String city;
    private String administrativeArea;
    private String country;
    private Double latitude;
    private Double longitude;

    static GeoApiContext geoApiContext;

    static {
        String googleMapsApiKey = "";
        if (googleMapsApiKey != null) {
            geoApiContext = new GeoApiContext().setApiKey(googleMapsApiKey);
        } else {
            System.out.println("It is recommended to set the googleMapsApiKey property to enable geocoding");
        }

    }



    public GeoData(String city)  {
        if (city != null && geoApiContext != null) {
            try {
                GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, city).await();
                if (results.length > 0) {
                    GeocodingResult result = results[0];
                    for (AddressComponent component : result.addressComponents) {
                        List<AddressComponentType> types = Arrays.asList(component.types);
                        if (types.contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2)) {
                            this.administrativeArea = component.longName;
                        }
                        if (types.contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1)) {
                            this.administrativeArea = component.longName;
                        }
                        if (types.contains(AddressComponentType.LOCALITY)) {
                            this.city = component.longName;
                        }
                        if (types.contains(AddressComponentType.COUNTRY)) {
                            this.country = component.longName;
                        }


                    }
                    this.latitude = result.geometry.location.lat;
                    this.longitude = result.geometry.location.lng;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public String getCity() {
        return city;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
