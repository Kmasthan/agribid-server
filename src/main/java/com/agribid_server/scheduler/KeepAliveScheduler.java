package com.agribid_server.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableScheduling
@Profile("prod")
public class KeepAliveScheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0/14 * * * ?")
    public String keepAlive() {
        return restTemplate.getForObject("https://agribid-server-1.onrender.com/agri-bid/welcome", String.class);
    }
}
