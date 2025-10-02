package com.kandan;

import java.util.List;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.*;

public class EmailService {
    
	private final String smtpHost;
	private final int smtpPort;
	private final String username;
	private final String password;
	private final String fromAddress;
	
	public EmailService(String smtpHost, int smtpPort, String username, String password, String fromAddress) {
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.username = username;
		this.password = password;
		this.fromAddress = fromAddress;
	}
	
	public void sendPatternToRecipients(List<String> recipients, Pattern pattern) {
		if(recipients == null || recipients.isEmpty() || pattern == null) return;
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", String.valueOf(smtpPort));
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			InternetAddress[] toAddresses = recipients.stream().map(r -> { try { return new InternetAddress(r); } catch(AddressException e) { return null; } }).filter(addr -> addr != null).toArray(InternetAddress[]::new);
			
			message.setRecipients(Message.RecipientType.BCC, toAddresses);
			message.setSubject("Daily DSA Pattern: " + pattern.getTitle());
			
			String htmlContent = buildHtmlContent(pattern);
			message.setContent(htmlContent,"text/html;charset=utf-8");
			
			Transport.send(message);
			
		} catch(MessagingException e) {
			e.printStackTrace();
		}	
	}
	
	
	private String buildHtmlContent(Pattern pattern) {
		StringBuilder builder = new StringBuilder();
		builder.append("<div sstyle='font-family: Arial, sans-serif;'>")
		.append("<h2>").append(escapeHtml(pattern.getTitle())).append("</h2>")
		.append("<p>").append(escapeHtml(pattern.getDescription())).append("</p>")
		.append("<pre style='background: #f8f8f8; padding: 12px; border-radius: 6px; overflow: auto;'>")
		.append(escapeHtml(pattern.getCode())).append("</pre>")
		.append("<p>").append(escapeHtml(pattern.getExplanation())).append("</p>").append("<hr>")
		.append("<p style='font-size: 12px; color: #666; '> You are receiving this email because you subscribed to the Pattern Library</p>")
		.append("</div>");
		return builder.toString();
	}
	
	private String escapeHtml(String s) {
		if(s == null) return "";
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
