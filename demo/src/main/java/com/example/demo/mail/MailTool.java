package com.example.demo.mail;
import org.springframework.beans.factory.annotation.Autowired;
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
    
	public String send( String title, String body ) {	//メール送信
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

	    mailMessage.setTo("itou@icc.core.ac.jp");	//送信先アドレス
	    mailMessage.setReplyTo("yamadataro@icc.core.ac.jp");	//返信先アドレス
	    mailMessage.setFrom("yamadataro@icc.core.ac.jp");	//送信元アドレス
	    mailMessage.setSubject(title);	//タイトル
	    mailMessage.setText(body);	//本文"テストメールです、\nここから次の行\nおわりです\n"

	    sender.send(mailMessage);

		return "ok";
	}
}
