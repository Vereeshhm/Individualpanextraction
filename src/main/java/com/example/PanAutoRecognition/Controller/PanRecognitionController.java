package com.example.PanAutoRecognition.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PanAutoRecognition.Entity.PanFileData;
import com.example.PanAutoRecognition.Service.PanRecognitionService;
import com.example.PanAutoRecognition.Utils.FileResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/saswat")
public class PanRecognitionController {

	@Autowired
	PanRecognitionService panRecognitionService;

	@PostMapping(value = "file/upload")
	public FileResponse getFiledata(HttpServletRequest request, HttpServletResponse response1) {

		FileResponse response = panRecognitionService.getFileData(request,response1);
		return response;
	}

	@PostMapping("pan/extraction")
	public String getPanExtractedData(@RequestBody PanFileData pandto,HttpServletRequest request, HttpServletResponse response1) {
		return panRecognitionService.getPanExtractedData(pandto,request,response1);
	}

}
