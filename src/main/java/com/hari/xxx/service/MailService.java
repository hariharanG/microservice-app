package com.hari.xxx.service;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hari.xxx.config.ApplicationProperties;
import com.hari.xxx.domain.JavaxMailSender;
import com.hari.xxx.domain.MailEntity;


@Component
public class MailService {

	/* private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	private JavaMailSender mailSender;
	private TemplateEngine templateEngine;
	private final ApplicationProperties appProperties;

	private final JavaxMailSender javaxMailSender;

	@Autowired
	public MailService(JavaMailSender mailSender, ApplicationProperties appProperties,  TemplateEngine templateEngine, MailProperties mailProperties, ApplicationProperties props) {
		this.mailSender = mailSender;
		this.appProperties = appProperties;
		this.templateEngine = templateEngine;
		logger.info("using Spring mail ? {}", props.getMail().getUseSpringMail());
		logger.info("Printing Mail Props : {} ", mailProperties);
		if (props.getMail().getUseSpringMail() == false) {
			this.javaxMailSender = new JavaxMailSender(mailProperties.getHost(), mailProperties.getPort(),
					mailProperties.getUsername(), mailProperties.getPassword(), props.getMail().getSenderName(),
					mailProperties.getProperties());
		} else {
			this.javaxMailSender = null;
		}
	}

	
	@Async
	public void sendMail(MailEntity mailDto) {
		if (this.javaxMailSender != null) {
			logger.info("Mail Client - Javax Mail Selected....");
			Context ctx = new Context();
			ctx.setLocale(mailDto.getLocale());
			if (mailDto.getModel() != null) {
				ctx.setVariables(mailDto.getModel());
				ctx.setVariable("adminURL", appProperties.getMail().getAdminWebsite());
				ctx.setVariable("customerURL", appProperties.getMail().getCustomerWebsite());
			}
			logger.info("Template processing for mail....");
			String htmlContent = this.templateEngine.process(mailDto.getMessageTemplate(), ctx);
			logger.info("Template processed for mail....");
			javaxMailSender.sendMail(mailDto.getMessageTo(), mailDto.getMessageSubject(), htmlContent,
					mailDto.getAttachmentPath());
		} else {
			logger.info("Mail Client - Spring Mail Selected....");
			sendMailByGmail(mailDto);
		}
	}

	public void sendMailByGmail(MailEntity mailDto) {
		logger.info("--MailGenerator : SendMail using Spring Mail---Begin---");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {

			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setTo(InternetAddress.parse(mailDto.getMessageTo()));
			messageHelper.setSubject(mailDto.getMessageSubject());

			Context ctx = new Context();
			ctx.setLocale(mailDto.getLocale());
			if (mailDto.getModel() != null) {
				ctx.setVariables(mailDto.getModel());
				ctx.setVariable("adminURL", appProperties.getMail().getAdminWebsite());
				ctx.setVariable("customerURL", appProperties.getMail().getCustomerWebsite());
			}

			String htmlContent = this.templateEngine.process(mailDto.getMessageTemplate(), ctx);
			messageHelper.setText(htmlContent, true);

			if (mailDto.getAttachmentPath() != null) {
				FileSystemResource file = new FileSystemResource(mailDto.getAttachmentPath());
				messageHelper.addAttachment(file.getFilename(), file);
			}

		} catch (Exception e) {
			throw new MailParseException(e);
		}
		// Sending the Message
		mailSender.send(mimeMessage);
		logger.info("--MailGenerator : SendMail ---End---");
	}
	*/
}
