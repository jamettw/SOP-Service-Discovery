package com.microservices.servicediscovery.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.servicediscovery.bean.Student;

@RestController
public class TeacherService {
	
	@Autowired
	private ServiceDiscoveryClient serviceDiscoveryClient;

	@RequestMapping("/v1/teacher/studentReport")
	public List<Student> getStudentReport() {
		List<String> lookups = Arrays.asList("61070003", "61070004");
		List<Student> l = new ArrayList<Student>();
		lookups.forEach(obj -> {
			l.add(serviceDiscoveryClient.getStudent(obj));
		});
		return l;
	}
}
