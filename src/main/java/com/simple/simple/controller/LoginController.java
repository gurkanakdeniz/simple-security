package com.simple.simple.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.simple.simple.entity.UserEntity;
import com.simple.simple.model.ResponseModel;
import com.simple.simple.model.UserModel;
import com.simple.simple.service.UserService;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;

	@GetMapping(value = { "/", "/login" })
	public ResponseModel login() {
		UserEntity currentUser = userService.getCurrentUser();

		if (currentUser.isAdmin()) {
			return new ResponseModel("redirect:/admin/index");
		} else if (!currentUser.isGuest()) {
			return new ResponseModel("redirect:/index");
		}

		ResponseModel modelAndView = new ResponseModel();
		UserModel userModel = new UserModel();
		modelAndView.addObject("user", userModel); // registration icin

		modelAndView.setViewName("login");

		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ResponseModel registration(@Valid UserModel usermodel, BindingResult bindingResult) {
		ResponseModel model = new ResponseModel();
		UserEntity userExists = userService.findUserByEmail(usermodel.getEmail());
		HashMap<String, String> errors = new HashMap<String, String>();
		HashMap<String, String> notification = new HashMap<String, String>();

		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
			errors.put("email", "There is already a user registered with the email provided");
		} else {
			if (StringUtils.isNotBlank(usermodel.getPassword())) {
				//TODO: check strong pass validate
			} else {
				bindingResult.rejectValue("password", "error.user", "Password not correct");
				errors.put("password", "Password not correct");
			}
		}

		if (bindingResult.hasErrors()) {
			// TODO:
		} else {
			UserEntity user = new UserEntity();

			user.setEmail(usermodel.getEmail());
			user.setName(usermodel.getName());
			user.setLastName(usermodel.getLastName());
			user.setPassword(usermodel.getPassword());
			userService.saveUser(user);
			notification.put("success", "User has been registered successfully!");
		}
		usermodel.setErrors(errors);
		usermodel.setNotification(notification);
		model.addObject("user", usermodel);

		model.setViewName("redirect:/login");
		return model;
	}

}
