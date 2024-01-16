package ma.projet.restorant.controllers;

import ma.projet.restorant.dto.UserDto;
import ma.projet.restorant.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public  String  index(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail",userDetails);
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", new UserDto());  // Corrected attribute name
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto()); // Assuming User is your form backing bean
        return "register";
    }
    @PostMapping("/register")
    public  String registerSave(@ModelAttribute("user")UserDto userDto){
        userService.save(userDto);
        return "redirect:/register?success";
    }
}
