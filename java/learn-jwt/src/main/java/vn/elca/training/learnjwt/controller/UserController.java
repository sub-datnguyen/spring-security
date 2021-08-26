package vn.elca.training.learnjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.learnjwt.model.JwtRequest;
import vn.elca.training.learnjwt.model.JwtResponse;
import vn.elca.training.learnjwt.security.JwtTokenService;

/**
 *
 * @author vlp
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/User")
public class UserController {

    @Value("spring.security.user.name")
    private String curUsername;

    @Autowired
    private JwtTokenService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (!userDetails.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body(new JwtResponse("invalid-credentials"));
        }

        return ResponseEntity.ok(new JwtResponse(jwtService.generateJwtToken(userDetails)));
    }

    @GetMapping (value = "/getCurentUserName")
    public ResponseEntity<String> getCurentUserName() {
        return  ResponseEntity.ok(curUsername);
    }

    @GetMapping (value = "/revokeToken/{tokenId}")
    public ResponseEntity<Boolean> revokeToken(@PathVariable String tokenId) {
        jwtService.invalidaToken(tokenId);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
