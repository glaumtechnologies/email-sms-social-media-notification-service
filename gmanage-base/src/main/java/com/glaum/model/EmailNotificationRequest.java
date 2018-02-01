package com.glaum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glaum.util.AppUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmailNotificationRequest {

    private String from;
 
	private List<String> to;
 
	private List<String> cc;
 
	private String subject;
 
	private String message;

	@JsonProperty("is_html")
	private boolean isHtml;

	private boolean hasAttachment;

	private Map<String, String> replacements;
 
	public EmailNotificationRequest() {
		this.to = new ArrayList<String>();
		this.cc = new ArrayList<String>();
	}
 
	public EmailNotificationRequest(String from, String toList, String subject, String message) {
		this();
		this.from = from;
		this.subject = subject;
		this.message = message;
		this.to.addAll(Arrays.asList(splitByComma(toList)));
	}
 
	public EmailNotificationRequest(String from, String toList, String ccList, String subject, String message) {
		this();
		this.from = from;
		this.subject = subject;
		this.message = message;
		this.to.addAll(Arrays.asList(splitByComma(toList)));
		this.cc.addAll(Arrays.asList(splitByComma(ccList)));
	}

	public String getToAsList() {
		return AppUtil.concatenate(this.to, ",");
	}
 
	private String[] splitByComma(String toMultiple) {
		String[] toSplit = toMultiple.split(",");
		return toSplit;
	}

	public Map<String, String> getReplacements() {
		return replacements;
	}

	public void setReplacements(Map<String, String> replacements) {
		this.replacements = replacements;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean html) {
		isHtml = html;
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
}