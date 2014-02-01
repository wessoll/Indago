package scheduler;

import java.text.ParseException;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author wesley
 */
public class Scheduler {

    public static void main(String[] args) throws ParseException {
        System.out.println("SCHEDULER STARTED");
        try {
            // Grab the Scheduler instance from the Factory 
            org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // and start it off
            scheduler.start();

            // define the job and tie it to our TestCaseExecuter class
            JobDetail job = newJob(TestCaseExecuter.class)
                    .withIdentity("job1", "group1")
                    .build();

            // Trigger the job to run now, and then repeat every hour
            Trigger trigger = newTrigger()
                    .startNow()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0 0 0/1 * * ?")) // every hour
                .forJob("job1", "group1")
                .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            //scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
        System.out.println("SCHEDULER ENDED");
    }
}