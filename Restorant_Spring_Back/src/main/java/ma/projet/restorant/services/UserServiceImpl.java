package ma.projet.restorant.services;

import ma.projet.restorant.dto.UserDto;
import ma.projet.restorant.entities.User;
import ma.projet.restorant.reposit.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
@Autowired
   private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public User findByUsername(String username){

        return userRepository.findByUsername(username);
    }
    @Override
    public User save(UserDto userDto){
        User user =new User(userDto.getUsername(),passwordEncoder.encode(userDto.getPassword()),userDto.getFullname());
        return userRepository.save(user);
    }
}
