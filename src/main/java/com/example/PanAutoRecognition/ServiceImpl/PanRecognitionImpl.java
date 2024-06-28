package com.example.PanAutoRecognition.ServiceImpl;

import java.io.File;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.PanAutoRecognition.Entity.PanFileData;
import com.example.PanAutoRecognition.Repository.FileApiLogRepository;
import com.example.PanAutoRecognition.Repository.PanApiLogRepository;
import com.example.PanAutoRecognition.Service.PanRecognitionService;
import com.example.PanAutoRecognition.Utils.FileApiLog;
import com.example.PanAutoRecognition.Utils.FileResponse;
import com.example.PanAutoRecognition.Utils.PanApiLog;
import com.example.PanAutoRecognition.Utils.PropertiesConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PanRecognitionImpl implements PanRecognitionService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PropertiesConfig config;

	@Autowired
	FileApiLogRepository apiLogRepository;

	@Autowired
	PanApiLogRepository apiLogRepository2;

	private String directURL;

	@Override
	public FileResponse getFileData(HttpServletRequest request, HttpServletResponse response1) {

		FileResponse response = null;

		FileApiLog apiLog = new FileApiLog();
		String url = config.getFileurl();
		try {

			String filePath = "C:/Users/VereeshHereemata/Pictures/WhatsApp Image 2024-06-20 at 5.21.00 PM.jpeg";
			String ttl = "3 years";

			String requestURL = request.getRequestURI().toString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			Gson gson = new GsonBuilder().create();
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			File file = new File(filePath);
			body.add("file", new FileSystemResource(file));
			body.add("ttl", ttl);

			String requestBodyJson = "{ \"file\": \"" + file.getName() + "\", \"ttl\": \"" + ttl + "\" }";
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(body, headers);

			apiLog.setUrl(requestURL);
			apiLog.setRequestBody(requestBodyJson);
			response = restTemplate.postForObject(url, requestEntity, FileResponse.class);
			apiLog.setResponseBody(gson.toJson(response));

			apiLog.setStatusCode(HttpStatus.OK.value());
			apiLog.setStatus("SUCCESS");
			this.directURL = response.getFile().getDirectURL();
			System.out.println("Direct URL: " + directURL);
			return response;
		} catch (HttpClientErrorException e) {
			apiLog.setStatusCode(e.getStatusCode().value());
			apiLog.setStatus("FAILURE");
			String responseBody = e.getResponseBodyAsString();
			System.out.println(responseBody + " Response ");
			apiLog.setResponseBody(responseBody);

			response1.setStatus(e.getStatusCode().value());
			response1.setContentType("application/json");
			return new FileResponse();

		}

		catch (Exception e) {
			apiLog.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiLog.setStatus("ERROR");
			String responseBody = e.getMessage();
			apiLog.setResponseBody(responseBody);
			response1.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response1.setContentType("application/json");
			return new FileResponse();

		} finally {
			apiLogRepository.save(apiLog);
		}
		// return response;
	}

	@Override
	public String getPanExtractedData(PanFileData pandto, HttpServletRequest request, HttpServletResponse response1) {

		PanApiLog apiLog = new PanApiLog();
		String response = null;
		try {


			String requestURl = request.getRequestURI().toString();

			pandto.setFiles(Collections.singletonList(this.directURL));

			System.out.println("Direct URL set in files parameter: " + pandto.getFiles().get(0));

			// Optional: Retrieve other properties from pandto (type, getRelativeData)
			pandto.getType();
			pandto.isGetRelativeData();

			Gson gson = new Gson();

			String requestBodyJson = gson.toJson(pandto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", config.getToken());

			HttpEntity<String> entity = new HttpEntity(requestBodyJson, headers);

			apiLog.setUrl(requestURl);
			apiLog.setRequestBody(requestBodyJson);
			response = restTemplate.postForObject(config.getPanextractionurl(), entity, String.class);
			apiLog.setResponseBody(response);
			apiLog.setStatusCode(HttpStatus.OK.value());
			apiLog.setStatus("SUCCESS");
			return response;

		} catch (HttpClientErrorException.TooManyRequests e) {

			apiLog.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
			apiLog.setStatus("FAILURE");
			response = e.getResponseBodyAsString();
			System.out.println(response + "Response");
			apiLog.setResponseBodyAsJson("API rate limit exceeded");
		} catch (HttpClientErrorException.Unauthorized e) {

			apiLog.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			apiLog.setStatus("FAILURE");
			response = e.getResponseBodyAsString();
			System.out.println(response1 + "Response");
			apiLog.setResponseBodyAsJson("No API key found in request");

		}

		catch (HttpClientErrorException e) {
			apiLog.setStatusCode(e.getStatusCode().value());
			apiLog.setStatus("FAILURE");
			response = e.getResponseBodyAsString();
			System.out.println(response + " Response ");
			apiLog.setResponseBody(response);
		}

		catch (Exception e) {
			apiLog.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiLog.setStatus("ERROR");
			response = e.getMessage();
			apiLog.setResponseBody(response);
		} finally {
			apiLogRepository2.save(apiLog);
		}
		return response;
	}

}
