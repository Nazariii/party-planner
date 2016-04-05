package com.redparty.partyplanner.common.scheduling;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.junit.Assert.*;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class LogJobTest {

    @Test
    public void execute() throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetail job = newJob(LogJob.class)
                .withIdentity("job", "group")
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger", "group")
                .startAt(futureDate(1, DateBuilder.IntervalUnit.MILLISECOND))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(20))
                .build();
        job.getJobDataMap().put(LogJob.EXECUTION_COUNT, 1);
        job.getJobDataMap().put("internalId", 11);
        sched.scheduleJob(job, trigger);

        sched.start();
        Thread.sleep(60L * 1000L);
        sched.shutdown(true);
    }

@Test
    public void execute2() throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetail job = newJob(LogJob.class)
                .withIdentity("job", "group")
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger", "group")
                .startAt(futureDate(1, DateBuilder.IntervalUnit.MILLISECOND))
                .withSchedule(cronSchedule("0/4 * * * * ?"))
                .build();
        job.getJobDataMap().put(LogJob.EXECUTION_COUNT, 1);
        job.getJobDataMap().put("internalId", 11);
        sched.scheduleJob(job, trigger);

        sched.start();
        Thread.sleep(60L * 1000L);
        sched.shutdown(true);
    }

}