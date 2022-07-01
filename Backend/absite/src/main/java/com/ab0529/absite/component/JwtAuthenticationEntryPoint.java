package com.ab0529.absite.component;

import com.ab0529.absite.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		ApiResponse re = new ApiResponse(HttpStatus.UNAUTHORIZED, "error: unauthorized access");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String respStr = ow.writeValueAsString(re);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		out.print(respStr);
		out.flush();
	}
}
