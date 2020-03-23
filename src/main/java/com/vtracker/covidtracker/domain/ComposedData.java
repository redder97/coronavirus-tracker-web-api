package com.vtracker.covidtracker.domain;

import java.util.List;

public class ComposedData {
    private int globalCases;
    private int globalDeaths;
    private int globalRecoveries;
    private List<ProvincialCase> topByDeaths;
    private List<ProvincialCase> topByConfirmedCases;
    private List<ProvincialCase> topByRecoveries;

    public int getGlobalCases() {
        return globalCases;
    }

    public void setGlobalCases(int globalCases) {
        this.globalCases = globalCases;
    }

    public int getGlobalDeaths() {
        return globalDeaths;
    }

    public void setGlobalDeaths(int globalDeaths) {
        this.globalDeaths = globalDeaths;
    }

    public int getGlobalRecoveries() {
        return globalRecoveries;
    }

    public void setGlobalRecoveries(int globalRecoveries) {
        this.globalRecoveries = globalRecoveries;
    }


    public List<ProvincialCase> getTopByDeaths() {
        return topByDeaths;
    }

    public void setTopByDeaths(List<ProvincialCase> topByDeaths) {
        this.topByDeaths = topByDeaths;
    }

    public List<ProvincialCase> getTopByConfirmedCases() {
        return topByConfirmedCases;
    }

    public void setTopByConfirmedCases(List<ProvincialCase> topByConfirmedCases) {
        this.topByConfirmedCases = topByConfirmedCases;
    }

    public List<ProvincialCase> getTopByRecoveries() {
        return topByRecoveries;
    }

    public void setTopByRecoveries(List<ProvincialCase> topByRecoveries) {
        this.topByRecoveries = topByRecoveries;
    }
}
