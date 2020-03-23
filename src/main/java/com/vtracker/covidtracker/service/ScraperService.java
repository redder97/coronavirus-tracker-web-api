package com.vtracker.covidtracker.service;

import com.vtracker.covidtracker.domain.ComposedData;

import java.io.IOException;

public interface ScraperService {

    void scrape() throws IOException;
}
