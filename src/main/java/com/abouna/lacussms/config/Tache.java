/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.config;

import com.abouna.lacussms.entities.Licence;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.tools.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */
@Component
public class Tache {

    private static final Logger log = LoggerFactory.getLogger(Tache.class);
    @Autowired
    private LacusSmsService service;

    public Tache() {
    }

    ////@Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 0 1,8,12,17 * * *")
    public void executeTask() {
        Utils.enregistrerMail(service);
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void checkLic() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        List<Licence> licences = service.getLicences();
        if (!licences.isEmpty()) {
            try {
                Licence lic = licences.get(0);
                Date date = Utils.getTimeFromInternet();
                long jour = (date.getTime() - sdf.parse(lic.getStartDate()).getTime()) / (1000 * 60 * 60 * 24);
                lic.setJour(lic.getJour() - jour);
                service.modifier(lic);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
