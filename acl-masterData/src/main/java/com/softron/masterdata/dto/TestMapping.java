package com.softron.masterdata.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.softron.quality.repository.QualityTransactionRepository;

public class TestMapping {

	@Autowired
	QualityTransactionRepository qualityTransactionRepository;

	@Autowired
	ModelMapper modelMapper;

	public static void main(String[] args) {
		try {
//			MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
//			map.put("key1", "value1");
//			map.put("key1", "value2");
//			map.put("key1", "value2");
//			map.put("key2", "value1");
//			map.put("key2", "value2");
//			map.put("key2", "value2");
//
//			System.out.println(map);
			
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");  
			   LocalDateTime now = LocalDateTime.now();  
			   System.out.println(dtf.format(now));  

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
