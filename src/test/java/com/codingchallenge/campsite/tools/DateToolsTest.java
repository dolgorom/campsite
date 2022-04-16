package com.codingchallenge.campsite.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class DateToolsTest {
	
	@Test
	public void testCreateDatePeriodTOneDay() {
		
		
		LocalDate startDate = LocalDate.parse("2021-10-01");
		LocalDate endDate  = LocalDate.parse("2021-10-02");
		Set<LocalDate> result = DateTools.createDatePeriod(startDate, endDate);
		
		assertEquals(1, result.size());
		assertTrue(result.contains(startDate));
	}
	
	@Test
	public void testCreateDatePeriodZero() {
		
		
		LocalDate startDate = LocalDate.parse("2021-10-01");
		LocalDate endDate  = LocalDate.parse("2021-10-01");
		Set<LocalDate> result = DateTools.createDatePeriod(startDate, endDate);
		
		assertEquals(0, result.size());

	}
	
	@Test
	public void testCreateDatePeriod9Days() {
		
		
		LocalDate startDate = LocalDate.parse("2021-10-01");
		LocalDate endDate  = LocalDate.parse("2021-10-10");
		Set<LocalDate> result = DateTools.createDatePeriod(startDate, endDate);
		
		assertEquals(9, result.size());
		assertTrue(result.contains(startDate));
		assertTrue(result.contains(endDate.minusDays(1)));
	}

}
