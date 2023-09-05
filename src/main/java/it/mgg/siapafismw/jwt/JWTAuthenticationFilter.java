package it.mgg.siapafismw.jwt;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter 
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements javax.servlet.Filter
{
	private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) 
    {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/api/services/controller/user/login"); 
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    {
    	try
    	{
    		User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
    		
    		Authentication authentication = authenticationManager.authenticate(
            		new UsernamePasswordAuthenticationToken(creds.getUsername(), 
            				                                creds.getPassword(),
                                                            new ArrayList<>()));
          String token = JWT.create()
          .withSubject(((User) authentication.getPrincipal()).getUsername())
          .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
          .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
          
          String body = ((User) authentication.getPrincipal()).getUsername() + " " + token;
          response.getWriter().write(body);
          response.getWriter().flush();
    		
    	}
    	
    	catch(Throwable ex)
    	{
    		
    	}
    }

	

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req,
//                                                HttpServletResponse res) throws AuthenticationException {
//        try 
//        {
//            User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
//
//            return authenticationManager.authenticate(
//            		new UsernamePasswordAuthenticationToken(creds.getUsername(), 
//            				                                creds.getPassword(),
//                                                            new ArrayList<>()));
//        } 
//        
//        catch (IOException e) 
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException {
//        String token = JWT.create()
//                .withSubject(((User) auth.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
//
//        String body = ((User) auth.getPrincipal()).getUsername() + " " + token;
//
//        res.getWriter().write(body);
//        res.getWriter().flush();
//    }
}
