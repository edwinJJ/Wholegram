package net.nigne.wholegram.controller;

import java.util.Locale;
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
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.EncryptService;
import net.nigne.wholegram.service.SignService;


@RestController
@RequestMapping("/")
public class SignController{

	@Inject
	private EncryptService encrypt;
	@Inject
	private SignService sign;
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model) {
	
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView insertSignUp(MemberVO vo,Locale locale, Model model, HttpServletRequest req) {
		String passwd = vo.getPasswd();
		vo.setPasswd(encrypt.shaEncrypt(passwd));
		sign.insert(vo);
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/checkId/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> CheckId(@PathVariable("user_id") String id) {
		ResponseEntity<Boolean> entity = null;
		try{
			entity = new ResponseEntity<>(checkValue.status(sign.checkId(id)),HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@RequestMapping(value = "/checkEmail/{email}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> CheckEmail(@PathVariable("email") String email) {
		System.out.println(sign.checkEmail(email+"%"));
		ResponseEntity<Boolean> entity = null;
		try{
			entity = new ResponseEntity<>(checkValue.status(sign.checkEmail(email+"%")),HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public ResponseEntity<Boolean> sendMail() {
		
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
        
        try {
            Authenticator auth = new senderAccount();
            Session session = Session.getInstance(properties, auth);
            session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
            MimeMessage msg = new MimeMessage(session);
 
            msg.setSubject("메일 제목");
            Address fromAddr = new InternetAddress("wholegramroot@gmail.com"); // 보내는사람 EMAIL
            msg.setFrom(fromAddr);
            Address toAddr = new InternetAddress("edwin0326@naver.com");    //받는사람 EMAIL
            msg.addRecipient(Message.RecipientType.TO, toAddr);
            msg.setContent("메일에 전송될 내용", "text/plain;charset=KSC5601"); //메일 전송될 내용
            Transport.send(msg);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		ResponseEntity<Boolean> entity = new ResponseEntity<>(true,HttpStatus.OK);
		
		return entity;
	}
	
	private static class senderAccount extends javax.mail.Authenticator {
		 
	    public PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication("wholegramroot@gmail.com", "wholeh0t$ix"); // @gmail.com 제외한 계정 ID, PASS

	    }
	}
}




class checkValue{
	// 중복되는 값이 없을 경우('0') TRUE 중복되는 값이 있을 경우 FALSE
	private final static int TRUE = 0;
	public static boolean status(int value){
		if(value==TRUE){
			return true;
		}else{
			return false;
		}
	}
}