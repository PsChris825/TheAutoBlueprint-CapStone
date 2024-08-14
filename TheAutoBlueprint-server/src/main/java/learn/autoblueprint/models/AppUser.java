package learn.autoblueprint.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppUser implements UserDetails {
    private int appUserId;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Collection<GrantedAuthority> authorities;

    // Constructor to initialize the AppUser
    public AppUser(int appUserId, String username, String password, boolean enabled, List<String> roles) {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    // Getter for appUserId
    public int getAppUserId() {
        return appUserId;
    }

    // Setter for appUserId
    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    // Getter for username
    @Override
    public String getUsername() {
        return username;
    }

    // Getter for password
    @Override
    public String getPassword() {
        return password;
    }

    // Check if the account is enabled
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Check if the account is non-expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Check if the account is non-locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Check if credentials are non-expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    // Override equals method for comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return appUserId == appUser.appUserId && enabled == appUser.enabled &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, username, password, enabled);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }
}
