package com.mint.project.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
    @Value("${origin.urls:*}")
    private String allowedOrigins;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTION, DELETE");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with, x-auth-username,x-auth-password,x-auth-token"
                + "origin,accept,content-type,access-control-request-method,access-control-request-headers,authorization,Access-Control-Allow-Origin");
        if (!req.getMethod().equals("OPTIONS")) {
            try {
                chain.doFilter(req, res);
            } catch (IOException | ServletException e) {
                //logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void destroy() {

    }

}

