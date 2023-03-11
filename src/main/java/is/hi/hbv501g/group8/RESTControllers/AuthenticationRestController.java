package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Configuration.JwtTokenUtil;
import is.hi.hbv501g.group8.Persistence.Entities.AuthRequest;
import is.hi.hbv501g.group8.Persistence.Entities.AuthResponse;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationRestController {
    @Autowired
    JwtTokenUtil jwtUtil;

    UserService userService;

    public AuthenticationRestController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            if(userService.login(user) == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}