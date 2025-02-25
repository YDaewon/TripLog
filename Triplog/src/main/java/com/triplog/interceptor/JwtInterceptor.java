package com.triplog.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.triplog.exception.UnAuthorizedException;
import com.triplog.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("accessToken");
		// 요청 정보 출력
        System.out.println("\u001B[36m" + "=== Incoming Request ===");
        System.out.println("\u001B[32m" + "Method: " + "\u001B[0m" + request.getMethod());
        System.out.println("\u001B[32m" + "URI: " + "\u001B[0m" + request.getRequestURI());
        System.out.println("\u001B[32m" + "Query String: " + "\u001B[0m" + request.getQueryString());
        System.out.println("\u001B[36m" + "========================"+ "\u001B[0m");
//        System.out.println("=== Headers ===");
//        request.getHeaderNames().asIterator().forEachRemaining(header -> 
//            System.out.println(header + ": " + request.getHeader(header))
//        );
        System.out.println("token: " + token);
	    if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
	        response.setStatus(HttpServletResponse.SC_OK); // OPTIONS 요청에 200 OK 반환
	        return false; // 이후 로직 실행하지 않음
	    }
		// JWT 토큰이 없거나 형식이 올바르지 않으면 로그인 페이지로 리다이렉트
		if (token == null || token.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

        try {
            // JWT 토큰 검증
            if (!jwtUtil.checkToken(token)) {
                throw new UnAuthorizedException();
            }
            Map<String, Object> value = jwtUtil.getUserInfo(token);
            String userId = (String) value.get("userId");
            int userNo = (Integer) value.get("userNo");
            int role = (Integer) value.get("role");
            // 사용자 정보를 요청에 저장
            request.setAttribute("userId", userId);
            request.setAttribute("userNo", userNo);
            request.setAttribute("role", role);
        } catch (UnAuthorizedException e) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }

		return true;
	}
}