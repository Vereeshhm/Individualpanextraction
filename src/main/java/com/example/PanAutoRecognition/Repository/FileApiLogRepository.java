package com.example.PanAutoRecognition.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PanAutoRecognition.Utils.FileApiLog;

public interface FileApiLogRepository  extends JpaRepository<FileApiLog, String>{

}
