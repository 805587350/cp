package com.qbb.test.jobs;

import com.qbb.jobs.award.GetSSQAwardJob;
import com.qbb.test.QuartzJobTestCase;
import org.junit.Test;
import org.quartz.JobExecutionException;

public class RunEveryDayJobTest extends QuartzJobTestCase {

	@Test
	public void test() {
		try {
			testJob(GetSSQAwardJob.class);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}

}