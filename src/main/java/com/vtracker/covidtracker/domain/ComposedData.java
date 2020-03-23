package com.vtracker.covidtracker.domain;

import java.util.List;

public class ComposedData {

    private List<ProvincialCase> topByDeaths;
    private List<ProvincialCase> topByConfirmedCases;
    private List<ProvincialCase> topByRecoveries;

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

    @Override
    public String toString() {
        return "ComposedData{" +
                "topByDeaths=" + topByDeaths +
                ", topByConfirmedCases=" + topByConfirmedCases +
                ", topByRecoveries=" + topByRecoveries +
                '}';
    }
}
