package com.foucsr.ticketmanager.util;

public interface AppConstants {
	String DEFAULT_PAGE_NUMBER = "0";
	String DEFAULT_PAGE_SIZE = "30";

	int MAX_PAGE_SIZE = 50;

	long ASN_ID = 1;

	String forgetPasswordSubject = "Password change Link";

	String forgetPasswordText = "To reset your password, click the link below.\n"
			+ "This change password link will become invalid after 7 days.\n\n";

	String forgetPasswordTemplate = "email/forgetpassword_template";

	String Success_Message = "Success";

}
