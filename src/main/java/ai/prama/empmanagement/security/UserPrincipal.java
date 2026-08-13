package ai.prama.empmanagement.security;

import ai.prama.empmanagement.entity.User;
import ai.prama.empmanagement.enums.LoginMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final User user;
    private final GrantedAuthority authority;

    public UserPrincipal(User user) {
        this.user = user;
        String roleName = user.getRole() != null ? "ROLE_"+ user.getRole().getRoleName().name().toUpperCase() : "ROLE_EMPLOYEE";
        this.authority = new SimpleGrantedAuthority(roleName);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isActive();
    }

    public LoginMethod getLoginMethod() {return  user.getLoginMethod();}

    public User getUser() {
        return user;
    }

    public long getId() {
        return user.getId();
    }

    public long getDepartmentId() {
        return user.getDepartment().getId();
    }
}