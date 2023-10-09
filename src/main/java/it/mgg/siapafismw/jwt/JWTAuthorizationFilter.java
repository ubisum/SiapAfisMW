package it.mgg.siapafismw.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter implements Filter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, javax.servlet.FilterChain chain)
			throws IOException, javax.servlet.ServletException {
		// TODO Auto-generated method stub
		
	}

}
