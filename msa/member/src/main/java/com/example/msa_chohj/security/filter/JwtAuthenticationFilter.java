package com.example.msa_chohj.security.filter;

import com.example.msa_chohj.security.jwt.AccessTokenDecoder;
import com.example.msa_chohj.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenDecoder accessTokenDecoder;
    private final CustomUserDetailsService userDetailsService;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    // 이 경로들은 JWT 검사를 아예 하지 않음
    private static final List<String> EXCLUDE_PATHS = List.of(
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE_PATHS.stream()
                .anyMatch(pattern -> PATH_MATCHER.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = AccessTokenDecoder.stripBearer(bearerToken);

            if (StringUtils.hasText(token) && accessTokenDecoder.validateToken(token)) {
                Jwt jwt = accessTokenDecoder.decode(token);
                String sub = AccessTokenDecoder.requireSubject(jwt);
                Long id = Long.parseLong(sub);
                UserDetails userDetails = userDetailsService.loadUserById(id);

                userDetailsChecker.check(userDetails);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}