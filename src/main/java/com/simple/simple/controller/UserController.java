package com.simple.simple.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.simple.simple.model.ResponseModel;
import com.simple.simple.model.UserModel;

@Controller
public class UserController extends BaseController {
	
	@GetMapping(value = { "/profile", "/admin/profile" })
	public ResponseModel index() {

		ResponseModel model = new ResponseModel();

		// TODO

		model.setViewName("user/profile");
		return model;
	}

	@PostMapping(value = { "/profile", "/admin/profile" })
	public ResponseModel update(@Valid UserModel usermodel, BindingResult bindingResult) {
		ResponseModel model = new ResponseModel();

		// TODO:

		model.setViewName("user/profile");
		return model;

	}
}
