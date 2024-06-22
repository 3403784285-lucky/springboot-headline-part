package com.atguigu.interceptor;

import com.atguigu.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    // 这里你直接用注解注入就行,  不改也没事
    @Autowired
    private JwtHelper jwtHelper;

/*    public JwtInterceptor(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        String token = request.getHeader("Authorization");
        System.out.println("Authorization header: " + token);

        if (token == null ) {
            System.out.println("无权限");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"无权限\"}");
            return false;
        }

        System.out.println("Checking token: " + token);

        if (jwtHelper.isExpiration(token)) {
            System.out.println("token无效或已过期");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"token无效或已过期\"}");
            return false;
        }

        System.out.println("有效token");
        return true;
    }
}