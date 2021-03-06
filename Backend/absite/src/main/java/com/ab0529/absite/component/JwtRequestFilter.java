package com.ab0529.absite.component;

import com.ab0529.absite.entity.TokenBlacklist;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.CustomUserDetails;
import com.ab0529.absite.service.JwtUserDetailsService;
import com.ab0529.absite.service.TokenBlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private TokenBlacklistService tokenBlacklistService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private final ApiResponse ERR_BLACKLSITED_TOKEN = new ApiResponse(HttpStatus.UNAUTHORIZED, "error: cannot use this jwt token");
	private final ApiResponse ERR_IP_AND_HEADER_MISMATCH = new ApiResponse(HttpStatus.UNAUTHORIZED, "error: ip and header mismatch");
	private final ApiResponse ERR_TOKEN_EXPIRED = new ApiResponse(HttpStatus.UNAUTHORIZED, "error: jwt token expired");
	private final ApiResponse ERR_TOKEN_NOT_BEARER = new ApiResponse(HttpStatus.UNAUTHORIZED, "error: jwt token does not begin with Bearer string");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException
	{
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;

		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (ExpiredJwtException e) {
				printJsonResponseFromApiResponse(response, ERR_TOKEN_EXPIRED);
			}
			catch (Exception e) {
				printJsonResponseFromApiResponse(response, new ApiResponse(
						HttpStatus.INTERNAL_SERVER_ERROR,
						"error: " + e.getMessage()
				));
			}
		} else if (requestTokenHeader != null) {
			printJsonResponseFromApiResponse(response, ERR_TOKEN_NOT_BEARER);
			return;
		}

		// Once we get the token validate it
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// Make sure ip and user-agent match
			String tokenIpAndAgent = (String) jwtTokenUtil.getClaimFromToken(jwtToken, c -> c.get("ip-and-agent"));
			String ipAndAgent = request.getRemoteAddr() + request.getHeader("User-Agent");

			// Make sure token is not in blacklist
			Boolean blToken = tokenBlacklistService.existsByToken(jwtToken);

			if (blToken)
				printJsonResponseFromApiResponse(response, ERR_BLACKLSITED_TOKEN);
			else if (tokenIpAndAgent.equals(ipAndAgent)) {
				// if token is valid configure Spring Security to manually set authentication
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			else
				printJsonResponseFromApiResponse(response, ERR_IP_AND_HEADER_MISMATCH);
		}

		try {
			filterChain.doFilter(request, response);
		} catch (ServletException e) {
			printJsonResponseFromApiResponse(response, new ApiResponse(
					HttpStatus.INTERNAL_SERVER_ERROR,
					"error: " + e.getMessage()
			));
		}
	}

	private void printJsonResponseFromApiResponse(HttpServletResponse response, ApiResponse apiResponse) throws IOException {
		PrintWriter out = response.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String respStr = ow.writeValueAsString(apiResponse);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		out.print(respStr);
		out.flush();
	}
}
