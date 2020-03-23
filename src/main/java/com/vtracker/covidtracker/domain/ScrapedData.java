package com.vtracker.covidtracker.domain;

public class ScrapedData {

    private int globalCases;
    private int globalDeaths;
    private int globalRecoveries;
    private int currentInfected;
    private int mildCondition;
    private int criticalCondition;

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

    public int getCurrentInfected() {
        return currentInfected;
    }

    public void setCurrentInfected(int currentInfected) {
        this.currentInfected = currentInfected;
    }

    public int getMildCondition() {
        return mildCondition;
    }

    public void setMildCondition(int mildCondition) {
        this.mildCondition = mildCondition;
    }

    public int getCriticalCondition() {
        return criticalCondition;
    }

    public void setCriticalCondition(int criticalCondition) {
        this.criticalCondition = criticalCondition;
    }

    public void infect(int mockInfection){
        this.globalCases = globalCases;
    }

    @Override
    public String toString() {
        return "ScrapedData{" +
                "globalCases=" + globalCases +
                ", globalDeaths=" + globalDeaths +
                ", globalRecoveries=" + globalRecoveries +
                ", currentInfected=" + currentInfected +
                ", mildCondition=" + mildCondition +
                ", criticalCondition=" + criticalCondition +
                '}';
    }
}
