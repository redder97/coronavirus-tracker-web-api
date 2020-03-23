package com.vtracker.covidtracker.service;

import com.vtracker.covidtracker.domain.ComposedData;
import com.vtracker.covidtracker.domain.ProvincialCase;
import com.vtracker.covidtracker.domain.VirusGeoData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class VirusDataServiceImpl implements VirusDataService{

    private static final String CONFIRMED_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private static final String DEATHS_URL =  "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
    private static final String RECOVERIES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";

    private static List<VirusGeoData> confirmedVirusData = new ArrayList<>();
    private static List<ProvincialCase> allProvincialCases = new ArrayList<>();
    private static ComposedData composedData = null;

    @Override
    @PostConstruct
    @Scheduled(cron = "* 1 * * * *")
    public void fetchAsyncConfirmedCases() throws ExecutionException, InterruptedException, IOException {
        System.out.println("LIST RETRIEVAL");
        List<VirusGeoData> virusGeoData = new ArrayList<>();
        List<ProvincialCase> provincialCases = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest confirmedCasesRequest = HttpRequest.newBuilder()
                .uri(URI.create(CONFIRMED_CASES_URL))
                .build();

        HttpRequest deathsRequest = HttpRequest.newBuilder()
                .uri(URI.create(DEATHS_URL))
                .build();

        HttpRequest recoveriesRequest = HttpRequest.newBuilder()
                .uri(URI.create(RECOVERIES_URL))
                .build();

        CompletableFuture<HttpResponse<String>> confirmedCasesResponse = httpClient.sendAsync(confirmedCasesRequest, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> deathsResponse = httpClient.sendAsync(deathsRequest, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> recoveriesResponse = httpClient.sendAsync(recoveriesRequest, HttpResponse.BodyHandlers.ofString());

        StringReader confirmedCasesReader = new StringReader(confirmedCasesResponse.thenApply(s -> {
            String response = s.body();
            return response;
        }).get());

        StringReader deathsReader = new StringReader(deathsResponse.thenApply(s -> {
            String response = s.body();
            return response;
        }).get());

        StringReader recoveriesReader = new StringReader(recoveriesResponse.thenApply(s -> {
            String response = s.body();
            return response;
        }).get());

        Iterable<CSVRecord> confirmedCasesRecord = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(confirmedCasesReader);
        Iterable<CSVRecord> deathsRecord = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(deathsReader);
        Iterable<CSVRecord> recoveriesRecord = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(recoveriesReader);

        for (CSVRecord r : confirmedCasesRecord) {
            String country = r.get("Country/Region");
            String region = r.get("Province/State");
            String longitude = r.get("Long");
            String latitude = r.get("Lat");
            int confirmedCases = Integer.parseInt(r.get(r.size() - 1));

            ProvincialCase p = new ProvincialCase(country, region, longitude, latitude);
            p.setConfirmedCases(confirmedCases);
            provincialCases.add(p);
        }

        for(CSVRecord r : deathsRecord){
            String country = r.get("Country/Region");
            String region = r.get("Province/State");
            String longitude = r.get("Long");
            String latitude = r.get("Lat");
            int deaths = Integer.parseInt(r.get(r.size() - 1));

            ProvincialCase p = new ProvincialCase(country, region, longitude, latitude);
            if(provincialCases.contains(p))
                provincialCases.stream().filter(z -> z.equals(p)).collect(Collectors.toList()).get(0).setDeaths(deaths);
            else
                provincialCases.add(p);
        }

        for(CSVRecord r : recoveriesRecord){
            String country = r.get("Country/Region");
            String region = r.get("Province/State");
            String longitude = r.get("Long");
            String latitude = r.get("Lat");
            int recoveries = Integer.parseInt(r.get(r.size() - 1));

            ProvincialCase p = new ProvincialCase(country, region, longitude, latitude);
            if(provincialCases.contains(p))
                provincialCases.stream().filter(z -> z.equals(p)).collect(Collectors.toList()).get(0).setRecoveries(recoveries);
            else
                provincialCases.add(p);
        }

        Map<String, List<ProvincialCase>> groupedByCountry = provincialCases.stream()
                .collect(Collectors.groupingBy(ProvincialCase::getCountry));

        virusGeoData = groupedByCountry.entrySet().stream().map(e -> {
            String country = e.getKey();
            VirusGeoData geoData = new VirusGeoData(country, e.getValue());
            return geoData;
        }).collect(Collectors.toList());

        virusGeoData.stream().forEach(s -> {
            System.out.println(s);
        });

        allProvincialCases = provincialCases;
        setComposedData(virusGeoData, provincialCases);
        confirmedVirusData = virusGeoData;
    }



    @Bean
    public List<VirusGeoData> retrieveConfirmedCases() {
        return confirmedVirusData;
    }

    @Bean
    public ComposedData retrieveComposedCases(){
        return composedData;
    }

    private void setComposedData(List<VirusGeoData> virusGeoData, List<ProvincialCase> provincialCases){
        ComposedData composedDataGenerated = new ComposedData();

        int globalCases = virusGeoData.stream().mapToInt(VirusGeoData::getTotalConfirmedCases).sum();
        int globalRecoveries = virusGeoData.stream().mapToInt(VirusGeoData::getTotalRecoveries).sum();
        int globalDeaths = virusGeoData.stream().mapToInt(VirusGeoData::getTotalDeaths).sum();

        composedDataGenerated.setGlobalCases(globalCases);
        composedDataGenerated.setGlobalDeaths(globalDeaths);
        composedDataGenerated.setGlobalRecoveries(globalRecoveries);

        List<ProvincialCase> topByDeath = provincialCases.stream()
                .sorted(Comparator.comparingInt(ProvincialCase::getDeaths).reversed()).collect(Collectors.toList());

        List<ProvincialCase> topByRecoveries = provincialCases.stream()
                .sorted(Comparator.comparingInt(ProvincialCase::getRecoveries).reversed()).collect(Collectors.toList());

        List<ProvincialCase> topByConfirmedCases = provincialCases.stream()
                .sorted(Comparator.comparingInt(ProvincialCase::getConfirmedCases).reversed()).collect(Collectors.toList());

        composedDataGenerated.setTopByDeaths(topByDeath.subList(0,10));
        composedDataGenerated.setTopByConfirmedCases(topByConfirmedCases.subList(0,10));
        composedDataGenerated.setTopByRecoveries(topByRecoveries.subList(0,10));


        composedData = composedDataGenerated;
    }




}
