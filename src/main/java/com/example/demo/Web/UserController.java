package com.example.demo.Web;

import com.example.demo.Infrastructure.ApplicationUserRepository;
import com.example.demo.Model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String dob, String firstname, String lastname, String bio) throws ParseException {
        String hashedpwd = bCryptPasswordEncoder.encode(password);
        Date DOB = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        ApplicationUser newUser = new ApplicationUser(username,hashedpwd, DOB, firstname, lastname, bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/myprofile")
    public String getMyProfilePage(Principal p, Model m) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("appUser", appUser);
        return "myprofile";
    }


    @GetMapping("/users/{id}")
    public String getSingleAppUserPage(Model m, @PathVariable String id) {
        long ID = Long.parseLong(id);
        ApplicationUser appUser = applicationUserRepository.findById(ID);
        m.addAttribute("appUser", appUser);
        return "singleappuser";
    }

    @GetMapping("/")
    public String getCodefellowship(Principal p, Model m) {
        if (p != null) {
            System.out.println(p.getName());
            m.addAttribute("principal", p.getName());
        } else {
            m.addAttribute("principal", "user");
        }

        return "index";
    }
}
