package com.vtracker.covidtracker.controller;

import com.vtracker.covidtracker.domain.ComposedData;
import com.vtracker.covidtracker.domain.ScrapedData;
import com.vtracker.covidtracker.domain.VirusGeoData;
import com.vtracker.covidtracker.service.VirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class Controller {

    @Autowired
    VirusDataService virusDataService;

    @Autowired
    List<VirusGeoData> confirmedCases;

    @Autowired
    ScrapedData scrapedData;

    @Autowired
    ComposedData composedData;


    private static String URL = "https://github.com/CSSEGISandData/COVID-19/blob/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    @GetMapping(value = "/confirmed-cases", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VirusGeoData> getConfirmedCases() {
        return confirmedCases;
    }

    @GetMapping(value ="/composed-cases", produces = MediaType.APPLICATION_JSON_VALUE)
    public ComposedData getComposedData() { return composedData; }

    @GetMapping(value="/scraped-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScrapedData getScrapedData() { return scrapedData; }
}
