package com.redparty.partyplanner.common.scheduling;

import com.redparty.partyplanner.common.scheduling.annotation.CronTriggeredJob;
import com.redparty.partyplanner.common.scheduling.annotation.QuartzService;
import com.redparty.partyplanner.common.scheduling.annotation.SimpleTriggeredJob;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.text.ParseException;
import java.util.*;

import static org.springframework.util.ReflectionUtils.doWithMethods;

/**
 * Spring application listener for registering scheduled methods in spring context
 */
public class QuartzSchedulingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            List<Trigger> cronTriggers = loadTriggerBeans(applicationContext);
            if (!cronTriggers.isEmpty()) {
                SchedulerFactoryBean schedulerFactoryBean = applicationContext.getBean(SchedulerFactoryBean.class, cronTriggers);
                schedulerFactoryBean.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all quartz triggers from spring application context by
     * {@link QuartzService @QuartzService}, {@link CronTriggeredJob @CronTriggeredJob} and {@link SimpleTriggeredJob @SimpleTriggeredJob} annotations
     *
     * @param context spring appication context
     * @return list of quartz triggers
     */
    private List<Trigger> loadTriggerBeans(ApplicationContext context) {
        Map<String, Object> quartzServiceBeans = context.getBeansWithAnnotation(QuartzService.class);
        Set<String> beanNames = quartzServiceBeans.keySet();
        List<Trigger> cronTriggers = new ArrayList<>();

        for (String beanName : beanNames) {
            Object bean = quartzServiceBeans.get(beanName);

            doWithMethods(bean.getClass(), method -> {

                if (method.isAnnotationPresent(CronTriggeredJob.class)) {
                    CronTriggeredJob cronTriggerJob = method.getAnnotation(CronTriggeredJob.class);

                    Optional<MethodInvokingJobDetailFactoryBean> detailFactoryBean = Optional.empty();
                    Optional<CronTriggerFactoryBean> triggerFactoryBean = Optional.empty();

                    try {
                        detailFactoryBean = Optional.of(createJobDetailFactoryBean(bean, beanName, cronTriggerJob.name(), method.getName()));
                        triggerFactoryBean = Optional.of(createCronTriggerFactoryBean(detailFactoryBean.get(), cronTriggerJob, beanName, method.getName()));

                    } catch (ClassNotFoundException | NoSuchMethodException | ParseException e) {
                        e.printStackTrace();
                    }

                    cronTriggers.add(triggerFactoryBean.get().getObject());
                }

                if (method.isAnnotationPresent(SimpleTriggeredJob.class)) {
                    SimpleTriggeredJob simpleTriggerJob = method.getAnnotation(SimpleTriggeredJob.class);

                    Optional<MethodInvokingJobDetailFactoryBean> detailFactoryBean = Optional.empty();
                    Optional<SimpleTriggerFactoryBean> triggerFactoryBean = Optional.empty();

                    try {
                        detailFactoryBean = Optional.of(createJobDetailFactoryBean(bean, beanName, simpleTriggerJob.name(), method.getName()));
                        triggerFactoryBean = Optional.of(createSimpleTriggerFactoryBean(detailFactoryBean.get(), simpleTriggerJob, beanName, method.getName()));

                    } catch (ClassNotFoundException | NoSuchMethodException | ParseException e) {
                        e.printStackTrace();
                    }

                    cronTriggers.add(triggerFactoryBean.get().getObject());
                }
            });
        }
        return cronTriggers;
    }

    private MethodInvokingJobDetailFactoryBean createJobDetailFactoryBean(Object bean, String beanName, String jobName, String methodName)
            throws NoSuchMethodException, ClassNotFoundException {

        MethodInvokingJobDetailFactoryBean detailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        detailFactoryBean.setName(String.format("%s.%s.%s", beanName, jobName, methodName));
        detailFactoryBean.setTargetMethod(methodName);
        detailFactoryBean.setTargetObject(bean);
        detailFactoryBean.afterPropertiesSet();
        return detailFactoryBean;
    }

    private CronTriggerFactoryBean createCronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean detailFactoryBean,
                                                                CronTriggeredJob cronTriggeredJob, String beanName, String methodName)
            throws ParseException {

        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(detailFactoryBean.getObject());
        triggerFactoryBean.setName(String.format("%s_trigger_%s", beanName, methodName));
        triggerFactoryBean.setCronExpression(cronTriggeredJob.cronExp());
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone(cronTriggeredJob.timeZone()));
        System.out.println(calendar.getTime());

        triggerFactoryBean.setStartTime(calendar.getTime());
        triggerFactoryBean.setTimeZone(calendar.getTimeZone());
        triggerFactoryBean.setGroup(cronTriggeredJob.group());

        triggerFactoryBean.afterPropertiesSet();
        return triggerFactoryBean;
    }

    private SimpleTriggerFactoryBean createSimpleTriggerFactoryBean(MethodInvokingJobDetailFactoryBean detailFactoryBean,
                                                                    SimpleTriggeredJob simpleTriggeredJob, String beanName, String methodName)
            throws ParseException {

        SimpleTriggerFactoryBean triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(detailFactoryBean.getObject());
        triggerFactoryBean.setName(String.format("%s_trigger_%s", beanName, methodName));
        triggerFactoryBean.setRepeatCount(simpleTriggeredJob.repeatCount());
        triggerFactoryBean.setRepeatInterval(simpleTriggeredJob.repeatInterval());
        triggerFactoryBean.setStartDelay(simpleTriggeredJob.startDelay());
        triggerFactoryBean.setPriority(simpleTriggeredJob.priority());
        triggerFactoryBean.setGroup(simpleTriggeredJob.group());

        triggerFactoryBean.afterPropertiesSet();
        return triggerFactoryBean;
    }
}
