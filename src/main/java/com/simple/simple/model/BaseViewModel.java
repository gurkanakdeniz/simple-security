package com.simple.simple.model;

import org.springframework.web.servlet.ModelAndView;

public class BaseViewModel extends ModelAndView {
	
	public BaseViewModel() {
		super();
	}

	public BaseViewModel(String string) {
		super(string);
	}

}
