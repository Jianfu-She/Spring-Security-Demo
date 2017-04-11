package com.sjf;

import com.sjf.utils.AESUtil;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() throws Exception {
		//BCryptPasswordEncoder util = new BCryptPasswordEncoder();
		//String password = util.encode("123" );
		//System.out.println(password);

		long time = System.currentTimeMillis();
		stampToDate(time);
		String source = "s67|j索隆f";
		String key = "A1B2C3D4E5F60708";
		String encryptData = AESUtil.AESEncode(key,source + "^" + time);
		System.out.println("加密后: " + encryptData);
		String decryptData = AESUtil.AESDncode(key, encryptData);
		System.out.println("解密后: " + decryptData);

		long endTime = System.currentTimeMillis();
		stampToDate(endTime);
	}

	private static void stampToDate(long s){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date(s);
		System.out.println(simpleDateFormat.format(date));
	}

	@Test
	public void mailTest() throws Exception {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("kfdlzpsjf@163.com");
		helper.setTo("1522192292@qq.com");
		helper.setSubject("主题：注册验证");
		helper.setText("<html><body><a href=\"http://154079m3d2.iok.la:33810/confirm?username=sjf\" >点击激活(http://154079m3d2.iok.la:33810/confirm?username=sjf)</body></html>", true);

		mailSender.send(mimeMessage);
	}

	@Test
	public void gravatar() throws Exception {
		Gravatar gravatar = new Gravatar();
		gravatar.setSize(50);
		gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
		gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
		String url = gravatar.getUrl("1522192292@qq.com");
		byte[] jpg = gravatar.download("1522192292@qq.com");
		System.out.println("url: " + url);
	}
}
