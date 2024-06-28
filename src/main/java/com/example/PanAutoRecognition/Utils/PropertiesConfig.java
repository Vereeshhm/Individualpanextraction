package com.example.PanAutoRecognition.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:src/main/resources/application.properties")
public class PropertiesConfig {

	
	
	@Value("${fileurl}")
	private String fileurl;
	@Value("${panextractionurl}")
	private String panextractionurl;
	
	@Value("${token}")
	private String token;
	
	

	public String getPanextractionurl() {
		return panextractionurl;
	}

	public void setPanextractionurl(String panextractionurl) {
		this.panextractionurl = panextractionurl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	
}
