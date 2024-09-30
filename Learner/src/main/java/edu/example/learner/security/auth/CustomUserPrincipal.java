package edu.example.learner.security.auth;

import edu.example.learner.entity.Member;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserPrincipal implements UserDetails {
    private final String memberId;
    private final String role;

    public CustomUserPrincipal(Member member) {
        this.memberId = member.getMemberId().toString();
        this.role = member.getRole().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return memberId;
    }
}
