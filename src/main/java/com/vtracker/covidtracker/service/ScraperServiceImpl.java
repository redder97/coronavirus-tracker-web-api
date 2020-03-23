package com.vtracker.covidtracker.service;

import com.vtracker.covidtracker.domain.ScrapedData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ScraperServiceImpl implements ScraperService {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private final static Logger LOGGER = Logger.getLogger(ScraperServiceImpl.class.getName());
    private static final String COVID_TRACKER_URL = "https://www.worldometers.info/coronavirus/";


    private static ScrapedData scrapedData;

    @Override
    @PostConstruct
    @Scheduled(fixedRate = 1200000)
    public void scrape() throws IOException {
        LOGGER.info("SCRAPING");
        ScrapedData scrapedDataGenerated = new ScrapedData();

        Document doc = Jsoup.connect(COVID_TRACKER_URL).get();
        Elements elements = doc.select("div#maincounter-wrap > div.maincounter-number")
                .select("span");
        String currentActive = doc.select("div.number-table-main").first().text();
        List<String> casesSpec = doc.select("div.panel_front").first()
                .select("div").first().select("span.number-table")
                .eachText();

        try {
            scrapedDataGenerated.setCurrentInfected(NumberFormat.getInstance().parse(currentActive).intValue());
            scrapedDataGenerated.setMildCondition(NumberFormat.getInstance().parse(casesSpec.get(0)).intValue());
            scrapedDataGenerated.setCriticalCondition(NumberFormat.getInstance().parse(casesSpec.get(1)).intValue());
            scrapedDataGenerated.setGlobalCases(NumberFormat.getInstance().parse(elements.get(0).text()).intValue());
            scrapedDataGenerated.setGlobalDeaths(NumberFormat.getInstance().parse(elements.get(1).text()).intValue());
            scrapedDataGenerated.setGlobalRecoveries(NumberFormat.getInstance().parse(elements.get(2).text()).intValue());

        }catch (ParseException e){
            LOGGER.warning(e.getMessage());
        }

        scrapedData = scrapedDataGenerated;
        LOGGER.info("Scrape: " + scrapedData);
        this.simpMessagingTemplate.convertAndSend("/socket-publisher", scrapedData);
    }

    @Bean
    public ScrapedData getScrapedData(){
        return scrapedData;
    }

}
