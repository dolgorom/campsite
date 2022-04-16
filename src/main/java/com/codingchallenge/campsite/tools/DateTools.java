package com.codingchallenge.campsite.tools;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


public class DateTools {
	
	public static Set<LocalDate> createDatePeriod(LocalDate startDate, LocalDate endDate){
		
		Set<LocalDate> allDates = new LinkedHashSet<>();
		
		LocalDate ld = startDate;
				
		while (ld.compareTo(endDate) < 0) {
			allDates.add(ld);
			ld =  ld.plusDays(1);					
		} 
		
		return allDates;
		
	}
}
