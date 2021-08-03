package uz.pdp.appcardtransfer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.appcardtransfer.service.MyAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //Requestdan token olish
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            // Tokendan Bearerni olib tashladik
            token = token.substring(7);

            // Tokenni validatsiya qildik(u buzilganmi yo'qmi. muddatini tekshiradi)
            if (jwtProvider.validateToken(token)) {

                // Tokendan usernameni oldik
                String username = jwtProvider.getUsernameFromToken(token);

                // userdetailsni oldik
                UserDetails userDetails = myAuthService.loadUserByUsername(username);

                //Authentication yaratdik
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //Sistemaga kim kirganini bildik
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }


        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
