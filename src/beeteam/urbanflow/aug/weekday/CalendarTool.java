
package beeteam.urbanflow.aug.weekday;


import java.util.*;


public class CalendarTool {
    
	
    public static Calendar getCalendar(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day+1); 
        return calendar;
    }
    
    public static Calendar getCalendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); 
        return calendar;
    }
    
    
    
    
    
    public static boolean isToday(int year, int month, int day)
    {return (currentYear()==year && currentMonth()==month && currentDay()==day);}
    
    public static int currentYear()
    {return Calendar.getInstance().get(Calendar.YEAR);}
    
    public static int currentMonth()
    {return Calendar.getInstance().get(Calendar.MONTH);}
    
    public static int currentDay()
    {return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1;}
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static boolean isValidDay(int year, int month, int day)
    {
        int d = getCalendar(year,month,day).get(Calendar.DAY_OF_MONTH);
        return day==d-1;
    }
    
    private static int dayOfWeek_(int year, int month, int day)
    {return getCalendar(year,month,day).get(Calendar.DAY_OF_WEEK);}
    
    public static boolean isMonday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.MONDAY;}
    public static boolean isTuesday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.TUESDAY;}
    public static boolean isWednesday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.WEDNESDAY;}
    public static boolean isThursday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.THURSDAY;}
    public static boolean isFriday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.FRIDAY;}
    public static boolean isSaturday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.SATURDAY;}
    public static boolean isSunday(int year, int month, int day) {return dayOfWeek_(year,month,day)==Calendar.SUNDAY;}
    public static boolean isWeekend(int year, int month, int day) {return isSaturday(year,month,day) || isSunday(year,month,day);}
    
    
    public static int dayOfWeek_(Date date)
    {return getCalendar(date).get(Calendar.DAY_OF_WEEK);}
    
    public static boolean isMonday(Date date) {return dayOfWeek_(date)==Calendar.MONDAY;}
    public static boolean isTuesday(Date date) {return dayOfWeek_(date)==Calendar.TUESDAY;}
    public static boolean isWednesday(Date date) {return dayOfWeek_(date)==Calendar.WEDNESDAY;}
    public static boolean isThursday(Date date) {return dayOfWeek_(date)==Calendar.THURSDAY;}
    public static boolean isFriday(Date date) {return dayOfWeek_(date)==Calendar.FRIDAY;}
    public static boolean isSaturday(Date date) {return dayOfWeek_(date)==Calendar.SATURDAY;}
    public static boolean isSunday(Date date) {return dayOfWeek_(date)==Calendar.SUNDAY;}
    public static boolean isWeekend(Date date) {return isSaturday(date) || isSunday(date);}
    
    
    
    
    public static int dayOfWeek_index(int year, int month, int day)
    {return (dayOfWeek_(year,month,day)+5)%7;}
    
    public static int dayOfWeek_index(Date date)
    {return (dayOfWeek_(date)+6)%7;}
    
    
    
    
    public static String dayOfWeek_name(int year, int month, int day)
    {
    	int n = dayOfWeek_(year,month,day);
    	if(n==Calendar.MONDAY) return "MONDAY";
    	if(n==Calendar.TUESDAY) return "TUESDAY";
    	if(n==Calendar.WEDNESDAY) return "WEDNESDAY";
    	if(n==Calendar.THURSDAY) return "THURSDAY";
    	if(n==Calendar.FRIDAY) return "FRIDAY";
    	if(n==Calendar.SATURDAY) return "SATURDAY";
    	if(n==Calendar.SUNDAY) return "SUNDAY";
    	return "?"+n;
    }
    
    
    
    
    
    private static HashSet frenchHoliday;
    
    private static void initFrenchHoliday()
    {
        frenchHoliday = new HashSet();
        frenchHoliday.add("0_0");   // 1 janvier
        frenchHoliday.add("3_11");  // 12 avril
        frenchHoliday.add("4_0");  // 1 mai
        frenchHoliday.add("4_7");  // 8 mai
        frenchHoliday.add("4_19");  // 20 mai
        frenchHoliday.add("4_30");  // 31 mai
        frenchHoliday.add("6_13");  // 14 juillet
        frenchHoliday.add("7_14");  // 15 aout
        frenchHoliday.add("10_0");  // 1 novembre
        frenchHoliday.add("10_10");  // 11 novembre
        frenchHoliday.add("11_24");  // 25 decembre
    }
    
    
    public static boolean isFrenchHoliday(int month, int day)
    {
        if(frenchHoliday==null)initFrenchHoliday();
        return frenchHoliday.contains(month+"_"+day);
    }
    
    
    
}
