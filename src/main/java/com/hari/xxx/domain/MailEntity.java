package com.hari.xxx.domain;
import java.util.Locale;
import java.util.Map;


public class MailEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1939785516287389389L;

	private final String messageTo;
	private final String messageSubject;
	private final Map<String, Object> model;
	private final String messageTemplate;
	private final String attachmentPath;
	private final Locale locale;

	public MailEntity(String messageTo, String messageSubject, Map<String, Object> model, String messageTemplate) {
		this.messageTo = messageTo;
		this.messageSubject = messageSubject;
		this.model = model;
		this.messageTemplate = messageTemplate;
		this.attachmentPath = null;
		this.locale= Locale.US;
	}
	
	public MailEntity(String messageTo, String messageSubject, Map<String, Object> model, String messageTemplate, Locale locale) {
		this.messageTo = messageTo;
		this.messageSubject = messageSubject;
		this.model = model;
		this.messageTemplate = messageTemplate;
		this.attachmentPath = null;
		this.locale  = locale;
	}

	public MailEntity(String messageTo, String messageSubject, Map<String, Object> model, String messageTemplate,
			String attachmentPath) {
		this.messageTo = messageTo;
		this.messageSubject = messageSubject;
		this.model = model;
		this.messageTemplate = messageTemplate;
		this.attachmentPath = attachmentPath;
		this.locale= Locale.US;
	}

	public String getMessageTo() {
		return messageTo;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public Locale getLocale() {
		return locale;
	}

}
