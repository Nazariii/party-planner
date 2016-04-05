package com.redparty.partyplanner.common.scheduling;

import com.redparty.partyplanner.common.scheduling.annotation.CronTriggeredJob;
import com.redparty.partyplanner.common.scheduling.annotation.SimpleTriggeredJob;
import com.redparty.partyplanner.common.scheduling.annotation.QuartzService;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


import java.text.ParseException;
import java.util.*;

import static org.springframework.util.ReflectionUtils.*;

public class QuartzSchedulingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            List<CronTrigger> cronTriggers = loadCronTriggerBeans(applicationContext);
            if (!cronTriggers.isEmpty()) {
                SchedulerFactoryBean schedulerFactoryBean = applicationContext.getBean(SchedulerFactoryBean.class, cronTriggers);
                schedulerFactoryBean.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<CronTrigger> loadCronTriggerBeans(ApplicationContext context) {
        Map<String, Object> quartzServiceBeans = context.getBeansWithAnnotation(QuartzService.class);
        Set<String> beanNames = quartzServiceBeans.keySet();
        List<CronTrigger> cronTriggers = new ArrayList<>();

        for (String beanName : beanNames) {
            Object bean = quartzServiceBeans.get(beanName);

            doWithMethods(bean.getClass(), method -> {

                if (method.isAnnotationPresent(CronTriggeredJob.class)) {
                    CronTriggeredJob cronTriggerJob = method.getAnnotation(CronTriggeredJob.class);
                    cronTriggerJob.annotationType().isAnnotationPresent(SimpleTriggeredJob.class);
                    MethodInvokingJobDetailFactoryBean detailFactoryBean = new MethodInvokingJobDetailFactoryBean();
                    detailFactoryBean.setName(String.format("%s.%s.%s", beanName, cronTriggerJob.name(), method.getName()));
                    detailFactoryBean.setTargetMethod(method.getName());
                    detailFactoryBean.setTargetObject(bean);
                   // detailFactoryBean.

                    try {
                        detailFactoryBean.afterPropertiesSet();
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
                    triggerFactoryBean.setJobDetail(detailFactoryBean.getObject());
                    triggerFactoryBean.setName(String.format("%s_trigger_%s", beanName, method.getName()));
                    triggerFactoryBean.setCronExpression(cronTriggerJob.cronExp());
                    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone(cronTriggerJob.timeZone()));
                    System.out.println(calendar.getTime());

                    triggerFactoryBean.setStartTime(calendar.getTime());
                    triggerFactoryBean.setTimeZone(calendar.getTimeZone());

                    try {
                        triggerFactoryBean.afterPropertiesSet();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    cronTriggers.add(triggerFactoryBean.getObject());
                }
            });
        }
        return cronTriggers;
    }

    public Trigger getCronTrigger(String cronExpression){
        CronScheduleBuilder cronScheduleBuilder=null;
        Trigger cronTrigger=null;
        cronScheduleBuilder=CronScheduleBuilder.cronSchedule(cronExpression);
        cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
        TriggerBuilder<Trigger> cronTtriggerBuilder=TriggerBuilder.newTrigger();
        cronTtriggerBuilder.withSchedule(cronScheduleBuilder);
        cronTrigger=cronTtriggerBuilder.build();

        return cronTrigger;
    }

}
