package ma.projet.restorant.services;

import ma.projet.restorant.dto.UserDto;
import ma.projet.restorant.entities.User;

public interface UserService {
    User findByUsername(String username);
    User save (UserDto userDto);
}
