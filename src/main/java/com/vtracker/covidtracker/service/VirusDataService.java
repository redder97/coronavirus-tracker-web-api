package com.vtracker.covidtracker.service;

import com.vtracker.covidtracker.domain.VirusGeoData;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface VirusDataService {
    @PostConstruct
    @Scheduled(cron = "* 1 * * * *")
    void initData() throws ExecutionException, InterruptedException, IOException;

}
