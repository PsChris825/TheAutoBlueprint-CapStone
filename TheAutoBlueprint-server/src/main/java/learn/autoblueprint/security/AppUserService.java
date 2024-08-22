package learn.autoblueprint.security;

import learn.autoblueprint.data.AppUserRepository;
import learn.autoblueprint.domain.Result;
import learn.autoblueprint.models.AppUser;
import learn.autoblueprint.models.Credentials;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;
    private final Validator validator;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder, Validator validator) {
        this.repository = repository;
        this.encoder = encoder;
        this.validator = validator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    public Result<AppUser> register(Credentials credentials) {
        Result<AppUser> result = validate(credentials);

        if (!result.isSuccess()) {
            return result;
        }

        AppUser appUser = new AppUser(0,
                credentials.getUsername(), encoder.encode(credentials.getPassword()),
                true, List.of("USER"));

        appUser = repository.create(appUser);
        result.setPayload(appUser);

        return result;
    }

    private Result<AppUser> validate(Credentials credentials) {
        Result<AppUser> result = new Result<>();

        if (credentials == null) {
            result.addMessage("Credentials cannot be null.");
            return result;
        }

        var errors = validator.validate(credentials);
        for (ConstraintViolation<Credentials> violation : errors) {
            result.addMessage(violation.getMessage());
        }

        if (!result.isSuccess()) {
            return result;
        }

        if (!isValidPassword(credentials.getPassword())) {
            result.addMessage("Password must contain a digit, a letter, and a non-digit/non-letter.");
            return result;
        }

        AppUser existing = repository.findByUsername(credentials.getUsername());
        if (existing != null) {
            result.addMessage("Username is already in use.");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }
}
