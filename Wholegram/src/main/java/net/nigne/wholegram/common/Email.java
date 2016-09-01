package net.nigne.wholegram.common;

import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class Email {

	@Inject
	private Encrypt encrypt;
	
	private String authstr = "";	// 인증 문자 or 새로운 비밀번호
	private int authEmail = 1;
	private int lostpasswd = 2;

	
	/*메일 보내기 설정*/
	public Properties proPerties() {
		
		Properties properties = new Properties();
        properties.put("mail.smtp.user", "edwinj0326@gmail.com"); //구글 계정
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        
		return properties;
	}
	
	/*메일 인증 보내기*/
	public String sendMail(Properties properties, String emailaddress) {
		
        try {
            Authenticator auth = new senderAccount();
            Session session = Session.getInstance(properties, auth);
            session.setDebug(true); 														     // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
            
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("Wholegram Email인증");
            Address fromAddr = new InternetAddress("wholegramroot@gmail.com");					 // 보내는사람 EMAIL
            msg.setFrom(fromAddr);
            
            Address toAddr = new InternetAddress(emailaddress);				   					 // 받는사람 EMAIL
            msg.addRecipient(Message.RecipientType.TO, toAddr);			
            
            AuthenticationString authstrclass = new AuthenticationString();
            authstr = authstrclass.makeRandomAuthenticationString(authEmail);					 // 인증문자 받기
            String html = "Wholegram Email 인증 문자입니다. " +
            				"<div>" +
            					authstr + 
            				"</div>";
            msg.setText(html, "UTF-8", "html");
//          msg.setContent("메일에 전송될 내용", "text/plain;charset=KSC5601"); 					 //메일 전송될 내용
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return authstr;
	}
	
	/*메일 (비밀번호찾기) 보내기*/
	public String sendFindMail(Properties properties, String emailaddress) {

		try {
            Authenticator auth = new senderAccount();
            Session session = Session.getInstance(properties, auth);
            session.setDebug(true); 														     // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
            
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("Wholegram 회원 비밀번호 찾기");
            Address fromAddr = new InternetAddress("wholegramroot@gmail.com");					 // 보내는사람 EMAIL
            msg.setFrom(fromAddr);
            
            Address toAddr = new InternetAddress(emailaddress);				   					 //받는사람 EMAIL
            msg.addRecipient(Message.RecipientType.TO, toAddr);			
            
            AuthenticationString authstrclass = new AuthenticationString();
            authstr = authstrclass.makeRandomAuthenticationString(lostpasswd);
            String html = "Wholegram 회원 새로운 비밀번호 입니다. " +
            				"<div>" +
            					authstr + 
            				"</div>";
            msg.setText(html, "UTF-8", "html");
//            msg.setContent("메일에 전송될 내용", "text/plain;charset=KSC5601"); 					//메일 전송될 내용
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return encrypt.shaEncrypt(authstr);														// 새로운 비밀번호 암호화
	}
	
	/*이메일 보낼 계정 접속인증*/
	private static class senderAccount extends javax.mail.Authenticator {
		
	    public PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication("wholegramroot@gmail.com", "wholeh0t$ix"); 		// @gmail.com 제외한 계정 ID, PASS
	    }
	}
	
	/*인증 문자열 or 비밀번호 생성*/
	private class AuthenticationString {
		public String makeRandomAuthenticationString(int flag) {
			String impl = "";
			if(flag == authEmail) {
				impl = RandomStringUtils.randomAlphanumeric(10);									  	// 문자열 랜덤 생성 (인증용)
			} else {
				impl = RandomStringUtils.randomAlphanumeric(10) + RandomStringUtils.randomNumeric(10);	// 문자+숫자 랜덤 생성 (새로운 비밀번호용)
			}
			return impl;
		}
	}
}
