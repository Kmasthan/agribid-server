package com.agribid_server.cloud_binary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudBinaryService {

	String uploadFile(MultipartFile file);
}
