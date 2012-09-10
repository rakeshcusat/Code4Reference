package com.code4reference.webapp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletResponse;

import java.util.Properties; 
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorldController {
	private final static String PARAMETER_MESSAGE = "msg";

    /** The below method handles the 
     * url localhost:8080/HelloWorldWebApp/greeting?msg=Hello 
     * here 'Hello' could be any word which can be passed 
     * as msg variable value. 
     * url parameter msg is mapped to message variable here.
     **/ 
    @RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String greeting(@RequestParam(value = PARAMETER_MESSAGE ) String message,
			           HttpServletResponse response) throws Exception {
			           
      System.out.println("Received param msg : "+ message);
       //The below string will appear on the brwoser window.
      return "<h3><i> Your greeting "+message + " </i></h3>";
	}

    /** The below method will handle the 
     *url localhost:8080/HelloWorldWebApp/hi 
     *
     **/ 
    @RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String hi(HttpServletResponse response) throws Exception {
      //The below string will appear on the brwoser window.
      return "<h3><i> Hi!! This is your first simple webapp </i></h3>";
	}
}
