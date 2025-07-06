package com.agribid_server.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudBinaryConfig {

	@Value("${cloud.api.name}")
	String cloudName;

	@Value("${cloud.api.key}")
	String cloudApiKey;

	@Value("${cloud.api.secret}")
	String cloudApiSecret;

	@Bean
	public Cloudinary cloudBinary() {
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", cloudName);
		config.put("api_key", cloudApiKey);
		config.put("api_secret", cloudApiSecret);	
		return new Cloudinary(config);
	}
}
