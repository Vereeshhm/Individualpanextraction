package com.example.PanAutoRecognition.Utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "file_api_logs")
public class FileApiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_api_logs_seq")
	@SequenceGenerator(name = "file_api_logs_seq", sequenceName = "file_api_logs_seq", allocationSize = 1)
	private Long id;

	private String url;

	@Column(name="requestBody",length=1000)
	private String requestBody;

	@Column(name="responseBody",length=1000)
	private String responseBody;

	private int statusCode;

	private String status;

	private LocalDateTime timestamp = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public FileApiLog(){
		this.timestamp=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

}
