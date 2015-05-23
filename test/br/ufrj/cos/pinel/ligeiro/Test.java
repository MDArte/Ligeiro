package br.ufrj.cos.pinel.ligeiro;

import java.util.Calendar;

import br.ufrj.cos.pinel.ligeiro.common.Util;


/**
 * 
 * @author Roque Pinel
 *
 */
public class Test
{
	protected static void printTime(Calendar a, Calendar b)
	{
		long milliseconds1 = a.getTimeInMillis();
		long milliseconds2 = b.getTimeInMillis();

		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);

		Util.println("\nTime in milliseconds: " + diff + " milliseconds.");
		Util.println("Time in seconds: " + diffSeconds + " seconds.");
		Util.println("Time in minutes: " + diffMinutes + " minutes.");
		Util.println("Time in hours: " + diffHours + " hours.");
		Util.println("Time in days: " + diffDays + " days.");
	}
}
