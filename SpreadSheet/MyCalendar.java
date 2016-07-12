/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JPanel;

import com.javaswingcomponents.calendar.JSCCalendar;
import com.javaswingcomponents.calendar.plaf.darksteel.DarkSteelCalendarUI;

public class MyCalendar extends JPanel{
	
	/*
	 * This class is creates a Jpanel contains a calendar
	 * reference: http://www.javaswingcomponents.com/product/calendar
	 */
	
	JSCCalendar calendar = null;
	JPanel calendarPanel = new JPanel();
	
	// Initiate a calendar
	public MyCalendar() { 
		TimeZone timeZone = TimeZone.getDefault();
		Locale locale = Locale.getDefault();
		calendar = new JSCCalendar(timeZone, locale);
		DarkSteelCalendarUI newUI = (DarkSteelCalendarUI) DarkSteelCalendarUI.createUI(calendar);
		calendar.setUI(newUI);
	}
	
	// put the calendar into a JPanel
	JPanel createCalendar() {
		calendarPanel.setLayout(new BorderLayout());
		calendarPanel.add(calendar, BorderLayout.CENTER);
		return calendarPanel;
	}
}