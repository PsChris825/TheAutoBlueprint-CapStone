package learn.autoblueprint.data;

import learn.autoblueprint.models.AppUser;

public interface AppUserRepository {
    AppUser findByUsername(String username);

    AppUser create(AppUser user);

    boolean update(AppUser user);
}
