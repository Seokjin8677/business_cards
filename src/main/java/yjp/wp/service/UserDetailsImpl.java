package yjp.wp.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yjp.wp.domain.Member;
import yjp.wp.domain.MemberRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {

    private Member member;
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    public UserDetailsImpl(Member member) {
        this.member = member;
        setAuthorities(member.getRoles());
    }

    private void setAuthorities(List<MemberRole> roles) {
        for (MemberRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
