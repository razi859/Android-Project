package com.example.Razi;

import java.io.Serializable;

/**
 * Created by Razi Rehman on 1/25/2018.
 */

public class MessageFile implements Serializable{
	private String from;
	private String to;
	private String time;
	private String date;
	private String subject;
	private String messegeText;
	private String content;
	public MessageFile()
	{}
	public MessageFile(String from, String to, String time, String date, String subject, String msg, String content)
	{
		setContent(content);
		setFrom(from);
		setMessegeText(msg);
		setSubject(subject);
		setTime(time);
		setDate(date);
		setTo(to);
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessegeText() {
		return messegeText;
	}
	public void setMessegeText(String messegeText) {
		this.messegeText = messegeText;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
