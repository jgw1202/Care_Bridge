package org.example.carebridge.global.oauth2;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.OAuth;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.exception.BadRequestException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        String userEmail = user.getAttribute("email");
        String userName = user.getAttribute("name");
        Map<String, Object> attribute = new HashMap<>();

            Optional<User> findUser = userRepository.findByEmail(userEmail);
        if (findUser.isEmpty()) {
            attribute.put("type", "register");
            //OAuth2 에서 제공하는 정보를 attribute 에 기입
            attribute.put("name", userName);
            attribute.put("email", userEmail);

            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("USER")),
                    attribute,
                    "email");
        } else {
            //회원가입 방식이 구글이라면, 로그인
            if (findUser.get().getOAuth().equals(OAuth.GOOGLE)) {
                attribute.put("type", "login");
            }
            //일반 회원가입으로 진행했으나, 구글 로그인을 시도할 경우 예외처리
            else {
                throw new AuthenticationServiceException("로그인 방식 오류");
            }

        }
        /**
         * 타입을 기준으로 구분
         * 사용자의 로그인 방식이 google 인지 검증
         **/
        boolean checkOAuth = findUser.get().isGoogleUser();
        if (!checkOAuth) {
            throw new BadRequestException(ExceptionType.OAUTH_GOOGLE);
        }

        //사용자 권한이 User 인지 확인
        boolean checkRole = findUser.get().isPatient();
        if (!checkRole) {
            throw new BadRequestException(ExceptionType.ROLE_NOT_SUPPORT);
        }
        attribute.put("id", findUser.get().getId());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attribute,
                "id");
    }
}
