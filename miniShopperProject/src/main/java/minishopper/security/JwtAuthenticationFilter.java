package minishopper.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import minishopper.exception.InvalidInputException;
import minishopper.service.CustomUserDetailsService;

//@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private CustomUserDetailsService userDetailService;
	
	  private HandlerExceptionResolver exceptionResolver;
	  
	  @Autowired
	    public JwtAuthenticationFilter(HandlerExceptionResolver exceptionResolver) {
	        this.exceptionResolver = exceptionResolver;
	    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;
        try {
		if (requestHeader != null && requestHeader.trim().startsWith("Bearer ")) {
			token = requestHeader.substring(7);
				username = this.jwtHelper.getUsernameFromToken(token);
			} 
//			catch (IllegalArgumentException ex) {
//				ex.printStackTrace();
//			} 
//			catch (ExpiredJwtException ex) {
//				ex.printStackTrace();
//			} catch (MalformedJwtException ex) {
//				ex.printStackTrace();
//			}
//		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				logger.info("Validation failed");
				
			}
		}
		filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | SignatureException | InvalidInputException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
	}

}
