package com.example.msa_chohj.security.jwt;

import com.example.msa_chohj.security.config.AuthProperties;
import com.example.msa_chohj.security.exception.AccessTokenRejectedException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessTokenDecoder {

    private final AuthProperties authProperties;
    private JwtDecoder jwtDecoder;

    private static final String BEARER_PREFIX = "Bearer ";

    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }

    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String requireSubject(Jwt jwt) {
        String subject = jwt.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new AccessTokenRejectedException("access token has no subject");
        }
        return subject;
    }

    public static List<String> rolesOf(Jwt jwt) {
        Object raw = jwt.getClaim("roles");
        return switch (raw) {
            case null -> List.of();
            case String role -> List.of(role);
            case Collection<?> roles -> roles.stream().map(String::valueOf).toList();
            default -> throw new AccessTokenRejectedException("roles claim must be a string or a list");
        };
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static String stripBearer(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private ECPublicKey jwtPublicKey() throws Exception {
        String pem = authProperties.accessTokenPublic();
        String body = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(body));
        return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(spec);
    }

    JwtDecoder jwtDecoder(ECPublicKey publicKey) {
        ECKey jwk = new ECKey.Builder(Curve.P_256, publicKey)
                .keyID(authProperties.accessTokenKeyId())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.ES256)
                .build();

        return NimbusJwtDecoder.withJwkSource(new ImmutableJWKSet<SecurityContext>(new JWKSet(jwk)))
                .jwsAlgorithm(SignatureAlgorithm.ES256)
                .build();
    }

    @PostConstruct
    public void init() throws Exception {
        jwtDecoder = jwtDecoder(jwtPublicKey());
    }
}
