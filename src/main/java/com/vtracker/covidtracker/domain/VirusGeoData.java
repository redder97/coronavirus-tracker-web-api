package com.vtracker.covidtracker.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirusGeoData {

    private String country;
    private List<ProvincialCase> provincialCaseList;
    private int totalConfirmedCases;
    private int totalDeaths;
    private int totalRecoveries;

    public VirusGeoData(String country, List<ProvincialCase> provincialCaseList) {
        this.country = country;
        this.provincialCaseList = provincialCaseList;
    }

    public String getCountry() {
        return country;
    }

    public List<ProvincialCase> getProvincialCaseList() {
        return provincialCaseList;
    }

    public int getTotalDeaths(){
        return provincialCaseList.stream().mapToInt(ProvincialCase::getDeaths).sum();
    }

    public int getTotalRecoveries(){
        return provincialCaseList.stream().mapToInt(ProvincialCase::getRecoveries).sum();
    }

    public int getTotalConfirmedCases(){
        return provincialCaseList.stream().mapToInt(ProvincialCase::getConfirmedCases).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirusGeoData that = (VirusGeoData) o;
        return country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country);
    }

    @Override
    public String toString() {
        return "VirusGeoData{" +
                "country='" + country + '\'' +
                ", provincialCaseList=" + provincialCaseList +
                '}';
    }
}
