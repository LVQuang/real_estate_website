package edu.hqh.real_estate_website.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import edu.hqh.real_estate_website.dto.request.AuthenticationRequest;
import edu.hqh.real_estate_website.dto.request.ForgotPasswordRequest;
import edu.hqh.real_estate_website.dto.request.RegisterRequest;
import edu.hqh.real_estate_website.dto.response.AuthenticationResponse;
import edu.hqh.real_estate_website.dto.response.ForgotPasswordResponse;
import edu.hqh.real_estate_website.dto.response.RegisterResponse;
import edu.hqh.real_estate_website.entity.User;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.enums.RoleName;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.ForgotPasswordMapper;
import edu.hqh.real_estate_website.mapper.RegisterMapper;
import edu.hqh.real_estate_website.repository.RoleRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    RegisterMapper registerMapper;
    PasswordEncoder passwordEncoder;
    ForgotPasswordMapper forgotPasswordMapper;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ITEM_EXISTS);

        var user = registerMapper.convertEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var role = roleRepository.findById(RoleName.USER.getName()).orElse(null);
        user.setRoles(Collections.singleton(role));

        return registerMapper.toResponse(userRepository.save(user));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByName(request.getName()).orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        boolean authenticated = passwordEncoder
                .matches(request.getPass(), user.getPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet =
                new JWTClaimsSet.Builder()
                        .subject(user.getName())
                        .issuer("lvpq.com")
                        .issueTime(new Date())
                        .expirationTime(new Date(
                                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                        ))
                        .claim("scope", buildScope(user))
                        .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(User user) {
        var stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        return stringJoiner.toString();
    }

    public ForgotPasswordResponse resetPassword(ForgotPasswordRequest request) {

        if(!userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.ITEM_DONT_EXISTS);

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.OTHER_EXCEPTION);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return forgotPasswordMapper.toResponse(userRepository.save(user));
    }
}
