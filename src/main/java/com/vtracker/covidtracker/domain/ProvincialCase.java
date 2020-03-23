package com.vtracker.covidtracker.domain;

import java.util.Objects;

public class ProvincialCase {

    private String country;
    private String province;
    private int confirmedCases;
    private int deaths;
    private int recoveries;
    private String latitude;
    private String longitude;

    public ProvincialCase(String country, String province, String latitude, String longitude) {
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public ProvincialCase(String country, String province, int confirmedCases, int deaths, int recoveries, String latitude, String longitude) {
        this.country = country;
        this.province = province;
        this.confirmedCases = confirmedCases;
        this.deaths = deaths;
        this.recoveries = recoveries;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setRecoveries(int recoveries) {
        this.recoveries = recoveries;
    }

    public String getProvince() {
        return province;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public String getCountry() {
        return country;
    }

    public int getRecoveries() {
        return recoveries;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvincialCase that = (ProvincialCase) o;
        return province.equals(that.province) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(province, latitude, longitude);
    }

    @Override
    public String toString() {
        return "ProvincialCase{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", confirmedCases=" + confirmedCases +
                ", deaths=" + deaths +
                ", recoveries=" + recoveries +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
