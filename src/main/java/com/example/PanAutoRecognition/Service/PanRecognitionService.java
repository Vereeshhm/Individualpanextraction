package com.example.PanAutoRecognition.Service;

import com.example.PanAutoRecognition.Entity.PanFileData;
import com.example.PanAutoRecognition.Utils.FileResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface PanRecognitionService {

	FileResponse getFileData(HttpServletRequest request, HttpServletResponse response1);

	String getPanExtractedData(PanFileData pandto,HttpServletRequest request, HttpServletResponse response1);

}
