package com.atguigu.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class Springboot04TaskApplicationTests {

	/**
	 * 邮件任务：首先引入对应的依赖spring-boot-starter-mail
	 * 在配置文件中进行配置
	 * 然后通过JavaMailSenderImpl进行操作邮件发送
	 */
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Test
	public void testMail(){              //简单邮件的发送
		SimpleMailMessage message = new SimpleMailMessage();
		//邮件设置
		message.setSubject("通知-今晚开会");
		message.setText("今晚7：30开会");
//		message.setTo("15797899690@163.com");//这个好像收不到
		message.setTo("1339393412@qq.com");
		message.setFrom("liulei0128@bupt.edu.cn");
		mailSender.send(message);
	}

	//复杂邮件的发送测试
	@Test
	public void testMail02() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//邮件设置
		helper.setSubject("通知-今晚开会");
//		helper.setText("今晚7：30开会");
		helper.setText("<b style='color:red'>今天 7：30 开会</b>",true);
//		helper.setTo("15797899690@163.com");//这个好像收不到
		helper.setTo("1339393412@qq.com");
		helper.setFrom("liulei0128@bupt.edu.cn");

		//helper上传文件附件
		helper.addAttachment("0.png",new File("C:\\Users\\15797\\Desktop\\0.png"));
		helper.addAttachment("avatar.jpg",new File("C:\\Users\\15797\\Desktop\\avatar.jpg"));
		mailSender.send(mimeMessage);
	}

	@Test
	void contextLoads() {
	}

}
