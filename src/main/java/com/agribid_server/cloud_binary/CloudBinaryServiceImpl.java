package com.agribid_server.cloud_binary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudBinaryServiceImpl implements CloudBinaryService {

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public String uploadFile(MultipartFile file) {
		try {
			if (file != null && file.getBytes().length > 0) {
				String url = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url")
						.toString();
				if (url != null) {
					return url;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
