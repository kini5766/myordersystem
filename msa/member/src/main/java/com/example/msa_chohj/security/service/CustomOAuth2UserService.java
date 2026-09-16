package com.example.msa_chohj.security.service;

import com.example.msa_chohj.member.domain.Member;
import com.example.msa_chohj.member.domain.Role;
import com.example.msa_chohj.member.domain.SocialAccount;
import com.example.msa_chohj.member.domain.SocialProviderType;
import com.example.msa_chohj.security.entity.CustomOAuth2User;
import com.example.msa_chohj.member.dto.SocialDTO;
import com.example.msa_chohj.member.repository.MemberRepository;
import com.example.msa_chohj.member.repository.SocialAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final SocialAccountRepository socialAccountRepository;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        SocialProviderType providerType;
        Map<String, Object> attributes;
        String providerId;
        String email;
        String nickname;

        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        if (SocialProviderType.NAVER.name().equals(registrationId)) {
            providerType = SocialProviderType.NAVER;
            attributes = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            providerId = attributes.get("id").toString();
            email = attributes.get("email").toString();
            nickname = attributes.get("nickname").toString();
        } else if (SocialProviderType.GOOGLE.name().equals(registrationId)) {
            providerType = SocialProviderType.GOOGLE;
            attributes = oAuth2User.getAttributes();
            providerId = attributes.get("sub").toString();
            email = attributes.get("email").toString();
            nickname = attributes.get("name").toString();
        } else {
            throw new OAuth2AuthenticationException("지원하지 않은 소셜 로그인입니다.");
        }
        Member member = findOrCreateMember(email, nickname);
        SocialAccount socialAccount = new SocialAccount(registrationId + "_" + providerId, providerType, email, member.getId());
        socialAccountRepository.save(socialAccount);
        return new CustomOAuth2User(attributes, member, socialAccount);
    }

    private Member findOrCreateMember(String email, String nickname) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            // 기존 유저
            return optionalMember.get();
        } else {
            // 신규 유저 추가
            Member newMember = Member
                    .builder()
                    .email(email)
                    .password(null)
                    .name(nickname)
                    .roles(Set.of(Role.USER))
                    .build();
            return memberRepository.save(newMember);
        }
    }
}
