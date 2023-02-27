package is.hi.hbv501g.group8.Configuration;

import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// https://www.codejava.net/frameworks/spring-boot/spring-security-jwt-authentication-tutorial

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenFilter(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!hasAuthorizationBearer(request)) {
            System.out.println("Does not have Auth Bearer!");
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);

        if (!jwtTokenUtil.validateAccessToken(token)) {
            System.out.println("test!");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Ætti að virka!");
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        System.out.println("Ætti að virka!");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        System.out.println(header);
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        User userDetails = getUserDetails(token);
        System.out.println("Ætti að3 virka!");
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private User getUserDetails(String token) {
        User userDetails = new User();
        System.out.println("Ætti að vir2ka!");
        String[] jwtSubject = jwtTokenUtil.getSubject(token).split(",");

        userDetails.setUsername(jwtSubject[0]);
        userDetails.setSSN(jwtSubject[1]);
        userDetails.setAccounttype(Integer.parseInt(jwtSubject[2]));

        return userDetails;
    }
}
