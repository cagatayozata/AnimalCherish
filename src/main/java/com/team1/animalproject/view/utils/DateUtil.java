package com.team1.animalproject.view.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtil {

	public static DateTimeFormatter droolsDateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static DateTimeFormatter formatterForStorage = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
	public static DateTimeFormatter formatterForDownload = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter formatterCalendar = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	public static DateTimeFormatter formatterCalendarForPfCalendar = DateTimeFormatter.ofPattern("yyyy,MM,dd,HH,mm");
	public static DateTimeFormatter formatterWithoutTime = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	public static DateTimeFormatter humanReadableFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat formatterAracYolcuExcel = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	private final static long ONE_MINUTE_IN_MILLISECONDS = 6000;
	private final static long ONE_SECOND_IN_MILLISECONDS = 1000;

	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static Date nowAsDate() {
		return asDate(LocalDateTime.now());
	}

	public static String nowAsString() {
		return now().format(formatter);
	}

	public static String nowAsStringForStorage() {
		return now().format(formatterForStorage);
	}

	public static String nowAsStringForDownload() {
		return now().format(formatterForDownload);
	}

	public static String asStringHumanReadable(LocalDateTime localDateTime) {
		return localDateTime.format(humanReadableFormatter);
	}

	public static String dateAsString(Date date) {
		if(ObjectUtils.isEmpty(date)){
			return "";
		}
		return asLocalDateTime(date).format(formatter);
	}

	public static String dateAsStringWithCalendar(Date date) {
		if(ObjectUtils.isEmpty(date)){
			return "";
		}
		return asLocalDateTime(date).format(formatterCalendar);
	}

	public static String dateAsString(Date date, DateTimeFormatter dateTimeFormatter) {
		return asLocalDateTime(date).format(dateTimeFormatter);
	}

	public static Date stringAsDate(String tarih) {
		if(StringUtils.isBlank(tarih)){
			return null;
		}
		return asDate(LocalDate.parse(tarih, formatter));
	}

	public static Date stringAsDateForDrools(String tarih) {
		if(StringUtils.isEmpty(tarih)){
			return null;
		}
		return asDate(LocalDate.parse(tarih, droolsDateformatter));
	}

	public static Date stringAsDate(String tarih, DateTimeFormatter dateTimeFormatter) {
		return asDate(LocalDate.parse(tarih, dateTimeFormatter));
	}

	public static Date stringAsDateTime(String tarih) {
		return asDate(LocalDateTime.parse(tarih, formatter));
	}

	public static Date stringAsDateTime(String tarih, DateTimeFormatter dateTimeFormatter) {
		return asDate(LocalDateTime.parse(tarih, dateTimeFormatter));
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static Date infiniteDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, 12);
		calendar.set(Calendar.YEAR, 3000);
		Date date = calendar.getTime();

		return date;
	}

	public static Date getNowDateWithoutTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getDateWithoutTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date asEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date asStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);
		return calendar.getTime();
	}

	public static Date nSecondsBefore(Date currentDate, int seconds) {
		long dateInMillis = currentDate.getTime();
		Date newDate = new Date(dateInMillis - (seconds * ONE_SECOND_IN_MILLISECONDS));
		return newDate;
	}

	public static Date nMinutesBefore(Date currentDate, int minutes) {
		long dateInMillis = currentDate.getTime();
		Date newDate = new Date(dateInMillis - (minutes * ONE_MINUTE_IN_MILLISECONDS));
		return newDate;
	}

	public static Date addingDay(Date date, int gun) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(gun);
		Date nevDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		return nevDate;
	}

	public static Date minusDays(Date date, int gun) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).minusDays(gun);
		Date nevDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		return nevDate;
	}

	public static Date nMinutesAfter(int minutes) {
		LocalDateTime localDateTime = now();
		return asDate(localDateTime.plusMinutes(1));
	}

	public static Date nMinutesAfter(Date currentDate, int minutes) {
		long dateInMillis = currentDate.getTime();
		Date newDate = new Date(dateInMillis + (minutes * ONE_MINUTE_IN_MILLISECONDS));
		return newDate;
	}

	public static Date get01012010() {
		return asDate(LocalDateTime.of(2010, 01, 01, 00, 00));
	}

	public static Date get01012099() {
		return asDate(LocalDateTime.of(2099, 01, 01, 00, 00));
	}

	public static String getTwoDigitYear(Date date) {
		return getTwoDigitYear(asLocalDateTime(date).toLocalDate());
	}

	public static String getTwoDigitYear(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern("yy"));
	}

	public static String getFourDigitYear(Date date) {
		return getFourDigitYear(asLocalDateTime(date).toLocalDate());
	}

	public static String getFourDigitYear(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern("yyyy"));
	}

	public static Date newDate(Long dateInMillis) {
		Date newDate = new Date(dateInMillis);
		return newDate;
	}

	public static List<LocalDate> getAllDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate, boolean withEndDate) {
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		if(withEndDate) numOfDaysBetween++;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i)).collect(Collectors.toList());
	}

	public static List<LocalDate> getAllDatesBetweenTwoDates(Date startDate, Date endDate, boolean withEndDate) {
		return getAllDatesBetweenTwoDates(DateUtil.asLocalDate(startDate), DateUtil.asLocalDate(endDate), withEndDate);
	}

	public static Date dateFormatter(Date date, String pattern, boolean hasTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		if(hasTime){
			return asDate(LocalDateTime.parse(formatter.format(asLocalDateTime(date)), formatter));
		}
		return asDate(LocalDate.parse(formatter.format(asLocalDate(date)), formatter));
	}

	public static boolean isWithinDateRange(Date startDate, Date endDate, Date date) {
		return !ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(date) && (startDate.before(date) || esittir(startDate, date) && (endDate == null || endDate.after(date) || esittir(endDate,
				date)));
	}

	public static String differenceBetweenTwoDates(Date startDate, Date endDate) {
		return calculateDifference(endDate, startDate);
	}

	public static String calculateDifference(Date startDate, Date endDate) {
		if(ObjectUtils.isEmpty(startDate) || ObjectUtils.isEmpty(endDate)){
			return "";
		}
		long diff = startDate.getTime() - endDate.getTime();
		if(diff < 0){
			return "";
		}
		long days = TimeUnit.MILLISECONDS.toDays(diff);
		long remainingHoursInMillis = diff - TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis);
		long remainingMinutesInMillis = remainingHoursInMillis - TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);
		long remainingSecondsInMillis = remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);

		StringBuilder stringBuilder = new StringBuilder();
		if(days > 0){
			stringBuilder.append(days).append(" Gün ");
		}
		if(days > 0 || hours > 0){
			stringBuilder.append(hours).append(" Saat ");
		}

		if(hours > 0 || minutes > 0){
			stringBuilder.append(minutes).append(" Dakika ");
		}

		return stringBuilder.toString();
	}

	public static List<Integer> getLast30Years() {
		List<Integer> years = new ArrayList<>();
		int currentYear = now().getYear();
		for(int i = 0; i < 30; i++){
			years.add(currentYear - i);
		}
		return years;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendarNow() {
		try{
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = null;
			datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			return now;
		} catch (DatatypeConfigurationException e){
			return null;
		}
	}

	public static boolean esittir(Date firstDate, Date secondDate) {
		return firstDate.compareTo(secondDate) == 0;
	}

}
