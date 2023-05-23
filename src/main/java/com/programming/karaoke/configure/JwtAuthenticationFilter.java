package com.programming.karaoke.configure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//todo Có thể được sử dụng trong toàn bộ ứng dụng
@Component
//Todo  tạo một constructor với tất cả các trường dữ liệu final hoặc được đánh dấu bằng @NonNull. Annotation này giúp rút
// todo ngắn mã và giảm thiểu việc phải viết constructor thủ công.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
//        Trong mô hình xác thực JWT, thông tin xác thực được truyền qua tiêu đề "Authorization" của yêu cầu HTTP.
//        Chuỗi JWT được gắn vào tiêu đề này theo định dạng "Bearer <jwt>".
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;
        if (authHeader == null || authHeader.startsWith("Bearer ") == false) {
            filterChain.doFilter(request, response);

        }
        jwt = authHeader.substring(7);
        email = jwtService.extractUserName(jwt);
    }
}
