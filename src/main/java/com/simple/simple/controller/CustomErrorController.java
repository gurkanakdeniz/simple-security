package com.simple.simple.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.simple.simple.model.ResponseModel;

@Controller
public class CustomErrorController {
	
  @GetMapping(value = "/error")
  public ResponseModel handleError(HttpServletRequest request) {
      Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

      if (status != null) {
      	Integer statusCode = Integer.valueOf(status.toString());

          if (statusCode == HttpStatus.NOT_FOUND.value()) {
              return new ResponseModel("redirect:/oops");
          } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
              return new ResponseModel("redirect:/oops");
          }
      }

      return new ResponseModel("redirect:/oops");
  }

}
