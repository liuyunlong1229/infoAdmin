package com.lyl.base.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;


@Component
public class KaptchaUtil {

	
	private Properties props = new Properties();
	private Producer kaptchaProducer = null;
	private String sessionKeyValue = null;
	private String sessionKeyDateValue = null;
	

	public KaptchaUtil() {
		init();
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}


	

	public void init() {

		// Switch off disk based caching.
		ImageIO.setUseCache(false);

		Config config = new Config(this.props);
		this.kaptchaProducer = config.getProducerImpl();
		this.sessionKeyValue = config.getSessionKey();
		this.sessionKeyDateValue = config.getSessionDate();
	}

	public void generate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Set standard HTTP/1.1 no-cache headers.
		resp.setHeader("Cache-Control", "no-store, no-cache");

		// return a jpeg
		resp.setContentType("image/jpeg");

		// create the text for the image
		String capText = this.kaptchaProducer.createText();
		
		// fixes issue #69: set the attributes after we write the image in case
		// the image writing fails.

		// store the text in the session
		req.getSession().setAttribute(this.sessionKeyValue, capText);

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		req.getSession().setAttribute(this.sessionKeyDateValue, new Date());

		// create the image with the text
		BufferedImage bi = this.kaptchaProducer.createImage(capText);

		ServletOutputStream out = resp.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);

	
	}

}
