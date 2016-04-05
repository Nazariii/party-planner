package com.redparty.partyplanner.common.scheduling;


import lombok.Getter;
import lombok.Setter;
import org.quartz.*;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Getter
@Setter
public class LogJob implements Job {

    private int internalId = 1;

    public static final String EXECUTION_COUNT = "EXECUTION_COUNT";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int execCount = dataMap.getInt(EXECUTION_COUNT);
        execCount++;
        dataMap.put(EXECUTION_COUNT, execCount);

        System.out.println("Log job key:" + jobKey + " executing at " + new Date() +
                " Internal Id: " + getInternalId() +
                " Execution count: " + execCount);

    }
}
