package edu.example.learner.config;

import edu.example.learner.member.dto.Oauth2.SocialClientRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class CustomClientRegistrationRepo {
    private final SocialClientRegistration socialClientRegistration;

    public CustomClientRegistrationRepo(SocialClientRegistration socialClientRegistration) {

        this.socialClientRegistration = socialClientRegistration;
    }

    public ClientRegistrationRepository clientRegistrationRepository() {
        //인메모리 형식으로 naver 및 google 클라이언트 정보 저장
        return new InMemoryClientRegistrationRepository(socialClientRegistration.naverClientRegistration(),
                                                        socialClientRegistration.googleClientRegistration());
    }
}
