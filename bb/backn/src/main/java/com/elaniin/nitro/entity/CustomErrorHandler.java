package com.elaniin.nitro.entity;

public class CustomErrorHandler {

	private String date;
	private String message;
	private String details;
	private long status;
	private String locale;

	public CustomErrorHandler(String date, String message, String details, long status, String locale) {
		super();
		this.date = date;
		this.message = message;
		this.details = details;
		this.status = status;
		this.locale = locale;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

}
