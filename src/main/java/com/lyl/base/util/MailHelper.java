package com.lyl.base.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;


import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 发送邮件辅助类
 * @author liuyl
 *
 */
public class MailHelper {

	//发送人类型
	private static final String		TO			= "to";
	//抄送人类型
	private static final String		CC			= "cc";
	//暗里抄送类型
	private static final String		BC			= "bc";

	//freemarker配置对象
	private FreeMarkerConfigurer	freemarker;

	//邮件发送对象
	private MailSender				mailSender;

	//默认邮件编码
	private String					encoding	= "utf-8";

	//发送人邮箱地址
	private String					from;

	//发件人
	private String					personal;

	//简单邮件类型
	private SimpleMailMessage		simpleMailMessage;

	//邮件内容
	private String					mailContent;

	//复杂邮件辅助类
	MimeMessageHelper				messageHelper;

	/**
	 * 复杂邮件发送方法
	 * 
	 * @param sends 发送（to），抄送(cc)，暗送人(bc)列表
	 * @param subject 邮件的标题
	 * @param template 模板文件名称，默认位置在freemarkerconfig文件中指定
	 * @param model 填充模板的数据模型
	 * @param attachs 附件列表
	 * @return 是否发送成功
	 */
	public boolean sendByTemplate(Map<String, String[]> sends, String subject, String template,
			Map<String, Object> model, List<File> attachs) {

		try {

			JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) mailSender;
			MimeMessage msg = javaMailSender.createMimeMessage();
			messageHelper = new MimeMessageHelper(msg, true, encoding); //由于是html邮件，不是mulitpart类型       
			messageHelper.setFrom(from, personal);
			if (sends != null && sends.size() > 0) {
				Iterator<Entry<String, String[]>> it = sends.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String[]> entry = it.next();
					if (entry.getKey().equals(CC)) {
						messageHelper.setCc(entry.getValue());
					} else if (entry.getKey().equals(BC)) {
						messageHelper.setBcc(entry.getValue());
					} else {
						messageHelper.setTo(entry.getValue());
					}
				}
				messageHelper.setSubject(subject);
				messageHelper.setSentDate(new Date());
				messageHelper.setText(getHtmlText(template, model), true);

				if (attachs != null && attachs.size() > 0) {
					for (File file : attachs) {
						FileSystemResource fs = new FileSystemResource(file);
						messageHelper.addAttachment(MimeUtility.encodeWord(fs.getFilename()), file);
					}
				}
				// 指定邮件优先级 1：紧急 3：普通 5：缓慢
				msg.setHeader("X-Priority", "3");
				javaMailSender.send(msg);

			}
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * 简单邮件发送方法
	 * @param sends  发送（to），抄送(cc)，暗送人(bc)列表
	 * @param subject 邮件标题
	 * @param mailContent 邮件内容
	 * @return 是否发送成功
	 */
	public boolean send(Map<String, String[]> sends, String subject, String mailContent) {

		if (sends != null && sends.size() > 0) {
			Iterator<Entry<String, String[]>> it = sends.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String[]> entry = it.next();
				if (entry.getKey().equals(CC)) {
					simpleMailMessage.setCc(entry.getValue());
				} else if (entry.getKey().equals(BC)) {
					simpleMailMessage.setBcc(entry.getValue());
				} else {
					simpleMailMessage.setTo(entry.getValue());
				}
			}
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setSentDate(new Date());
			simpleMailMessage.setText(mailContent);
			mailSender.send(simpleMailMessage);
		}
		return true;

	}

	private String getHtmlText(String template, Map<String, Object> model) {

		try {
			Template tpl = freemarker.getConfiguration().getTemplate("mailTemplate/" + template);
			model.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return "";
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public FreeMarkerConfigurer getFreemarker() {
		return freemarker;
	}

	public void setFreemarker(FreeMarkerConfigurer freemarker) {
		this.freemarker = freemarker;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

}
