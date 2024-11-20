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