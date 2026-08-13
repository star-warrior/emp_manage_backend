package ai.prama.empmanagement.security;

import ai.prama.empmanagement.entity.Role;
import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.enums.LoginMethod;
import ai.prama.empmanagement.enums.Roles;
import ai.prama.empmanagement.exception.custom.ResourceNotFoundException;
import ai.prama.empmanagement.repository.RoleRepository;
import ai.prama.empmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String accountId = request.getClientRegistration().getRegistrationId();

        Map<String, Object> attrs = oAuth2User.getAttributes();
        String email;
        String username;
        String providerId;

        if(accountId.equalsIgnoreCase("google")) {
            email = (String) attrs.get("email");
            username = (String) attrs.get("name");
            providerId = (String) attrs.get("sub");
        } else if (accountId.equalsIgnoreCase("microsoft")) {
            email = (String) attrs.getOrDefault("email", attrs.get("preferred_username"));
            username = (String) attrs.get("displayName");
            providerId = (String) attrs.get("oid");
        } else {
            throw new OAuth2AuthenticationException("Login method is invalid");
        }


        LoginMethod method = LoginMethod.valueOf(accountId.toUpperCase());

        User user = userRepository.findByEmail(email).orElseThrow(() -> new OAuth2AuthenticationException("User does not exist"));

        if (user.getLoginMethod() != method) {
            throw new OAuth2AuthenticationException("Login method is invalid for email: " + email);
        }

        if (!user.isActive()) {
            throw new OAuth2AuthenticationException("Account is disabled!");
        }

        return oAuth2User;
    }
}
