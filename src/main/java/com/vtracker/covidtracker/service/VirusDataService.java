package com.vtracker.covidtracker.service;

import com.vtracker.covidtracker.domain.VirusGeoData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface VirusDataService {
    void fetchAsyncConfirmedCases() throws ExecutionException, InterruptedException, IOException;
}
