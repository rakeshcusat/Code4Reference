package com.rak.letmeknow;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{

	int id;
	String senderEmail;
	String eventDetails;
	String time;
	String date;
	String subject;
	String gpsLoc;
	
	
	
	public Event(int id, String senderEmail, String eventDetails, String time,
			String date, String subject,String gpsLoc) {
		super();
		this.id = id;
		this.senderEmail = senderEmail;
		this.eventDetails = eventDetails;
		this.time = time;
		this.date = date;
		this.subject = subject;
		this.gpsLoc = gpsLoc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getEventDetails() {
		return eventDetails;
	}
	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGpsLoc() {
		return gpsLoc;
	}
	public void setGpsLoc(String gpsLoc) {
		this.gpsLoc = gpsLoc;
	}
	
}
