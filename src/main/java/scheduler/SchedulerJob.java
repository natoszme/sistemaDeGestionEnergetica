package scheduler;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import repositorio.RepoReglas;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerJob {

	 public static void main(String[] args) throws Exception {
	        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

	        scheduler.start();

	        JobDetail jobDetail = newJob(JobEjecutadorDeReglas.class).build();

	        Trigger trigger = newTrigger()
	                .startNow()
	                .withSchedule(repeatSecondlyForever(2))
	                .build();

	        scheduler.scheduleJob(jobDetail, trigger);
	    }
	 
	 public static class JobEjecutadorDeReglas implements Job {

	        @Override
	        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	        	RepoReglas.getInstance().ejecutarTodas();
	        }
	    }
}
