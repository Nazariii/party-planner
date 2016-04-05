package com.redparty.partyplanner.service.scheduling.impl;

import com.redparty.partyplanner.common.scheduling.annotation.CronTriggeredJob;
import com.redparty.partyplanner.common.scheduling.annotation.QuartzService;
import com.redparty.partyplanner.common.scheduling.annotation.SimpleTriggeredJob;
import com.redparty.partyplanner.service.scheduling.SchedulingService;

@QuartzService
public class SchedulingServiceImpl implements SchedulingService {

    @Override
    @CronTriggeredJob(name = "scedulelog", cronExp = "0/3 * * * * ?")
    public void scheduledCalendarLog() {
        System.out.println("\n ++++++=+++++++++++++++++++ WIIIIIIIIIII =============================== \n");
    }

    @Override
    @SimpleTriggeredJob(name = "interval log", repeatCount = 3)
    public void scheduledIntervalLog() {
        System.out.println("\n !!!!!!!!!!!!!!!!!!!!!!!!! XXXXXXXXXXXXX @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ \n");
    }
}
