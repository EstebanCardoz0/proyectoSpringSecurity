package com.example.springsecurity.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurity.utils.JwtUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

//mediante el extend establecemos que es un filtro que se tiene que ejecutar siempre
public class JwtTokenValidator extends OncePerRequestFilter {
    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null) {
            //en el encabezado antes del token viene la palabra bearer (esquema de autenticacion)
            //por lo que debemos sacarlo
            jwtToken = jwtToken.substring(7); //son 7 letras más 1 espacio
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            //si el token es valido, le concedemos el acceso
            String username = jwtUtils.extracUsername(decodedJWT);
            //me devuelve el claim, necesito pasarlo a String
            String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            //si todo está ok, hay que setearlo en el context holder
            //para eso tengo que convertirlos a grantedauthority
            Collection authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            //si se valida el token, le damos acceso al user en el context holder
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        //si no viene token, va al siguiente filtro
        //si no viene el token, esto arroja error
        filterChain.doFilter(request, response);

    }


}
