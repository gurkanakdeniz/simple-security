package com.simple.simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.simple.simple.model.ResponseModel;

@Controller
public class CommonController {

	@GetMapping(value = "/index")
	public ResponseModel index() {
		return new ResponseModel("index");
	}
	
	@GetMapping(value = "/admin/index")
	public ResponseModel adminIndex() {
		return new ResponseModel("/admin/index");
	}

	@GetMapping(value = "/oops")
	public ResponseModel oops() {
		ResponseModel modelAndView = new ResponseModel();

		modelAndView.addObject("errorMessage", "something wrong");
		modelAndView.setViewName("oops");

		return modelAndView;
	}

}
