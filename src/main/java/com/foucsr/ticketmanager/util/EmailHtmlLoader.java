package com.foucsr.ticketmanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailHtmlLoader {

	Logger logger = LoggerFactory.getLogger(EmailHtmlLoader.class);
	
	@Autowired
	private TemplateEngine templateEngine;

	public String getText(String templateName, String forgetPasswordContent) {

		Context context = new Context();

		String body = new String();

		if ("email/forgetpassword_template".equals(templateName)) {

			body = loadForgetPasswordText(context, templateName, forgetPasswordContent);
			
//			logger.info("***************** Email Template body *********************\n" + body);
			
		}

		return body;
	}

	private String loadForgetPasswordText(Context context, String templateName, String forgetPasswordContent) {
		
//		logger.info("***************** Email ForgetPassword Link *********************\n" + forgetPasswordContent);

		context.setVariable("password_link", forgetPasswordContent);
		String body = templateEngine.process(templateName, context);

		return body;

	}
	
	
}