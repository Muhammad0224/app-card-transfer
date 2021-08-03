package uz.pdp.appcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcardtransfer.model.LoginDto;
import uz.pdp.appcardtransfer.security.JwtProvider;
import uz.pdp.appcardtransfer.service.CardService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CardService cardService;

    @PostMapping("/login")
    public ResponseEntity<?> loginToSystem(@RequestBody LoginDto dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            String token = jwtProvider.generateToken(dto.getUsername());
            cardService.eliminateCard(dto.getUsername());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Username or password is incorrect");
        }
    }
}
