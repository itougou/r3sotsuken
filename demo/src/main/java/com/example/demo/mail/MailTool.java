package com.example.demo.mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/*
 * メール クラス
 */
@Component
public class MailTool {

    @Autowired
    private MailSender sender;
    
    @Autowired
    private Environment environment;
    
	public String send( String title, String body , String email ) {	//メール送信
		
		System.out.println("★mailTool spring.mail.username："+environment.getProperty("spring.mail.username")); 

		SimpleMailMessage mailMessage = new SimpleMailMessage();

	    mailMessage.setTo( email );	//送信先アドレス
	    mailMessage.setReplyTo("yamadataro@icc.core.ac.jp");	//返信先アドレス （山田太郎）
	    mailMessage.setFrom("yamadataro@icc.core.ac.jp");	//送信元アドレス （山田太郎）
	    mailMessage.setSubject(title);	//タイトル
	    mailMessage.setText(body);	//本文"テストメールです、\nここから次の行\nおわりです\n"

	    sender.send(mailMessage);

		return "ok";
	}
}
