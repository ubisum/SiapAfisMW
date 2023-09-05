package it.mgg.siapafismw.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


//public class JWTAuthorizationFilter extends BasicAuthenticationFilter implements Filter
public class JWTAuthorizationFilter implements javax.servlet.Filter
{
//	public JWTAuthorizationFilter(AuthenticationManager authManager) {
//        super(authManager);
//    }
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
		String header = request.getParameter(SecurityConstants.HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) 
        {
            chain.doFilter(request, response);
            return;
        }
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain) throws IOException, ServletException 
//    {
//        String header = req.getHeader(SecurityConstants.HEADER_STRING);
//
//        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) 
//        {
//            chain.doFilter(req, res);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(req, res);
//    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
//	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    private UsernamePasswordAuthenticationToken getAuthentication(ServletRequest request) {
//    	String token = request.getHeader(SecurityConstants.HEADER_STRING);
        String token = request.getParameter(SecurityConstants.HEADER_STRING);

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
}
