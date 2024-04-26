package edu.hqh.real_estate_website.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class SessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        if(session != null) {
            CustomHttpRequest customRequest = new CustomHttpRequest(httpRequest);
            var token =(String) session.getAttribute("token");
            customRequest.addHeader("Authorization", "Bearer " + token);
            chain.doFilter(customRequest, response);
            log.info("Check filter: " + customRequest.getHeader("Authorization"));
            return;
        }
        chain.doFilter(request, response);
    }
}