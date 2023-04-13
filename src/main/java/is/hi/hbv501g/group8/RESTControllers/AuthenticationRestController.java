package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Configuration.JwtTokenUtil;
import is.hi.hbv501g.group8.Persistence.Entities.AuthRequest;
import is.hi.hbv501g.group8.Persistence.Entities.AuthResponse;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            User loggedInCheck = userService.login(user);
            if(loggedInCheck == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);
            response.setSsn(loggedInCheck.getSSN());
            response.setEmail(loggedInCheck.getEmail());
            response.setAccountType(loggedInCheck.getEmail()+"");

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
