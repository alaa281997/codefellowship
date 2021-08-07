package com.example.demo.Web;

import com.example.demo.Infrastructure.ApplicationUserRepository;
import com.example.demo.Model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/usercreate")
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
        m.addAttribute("appUser",appUser);
        m.addAttribute("principal", p.getName());
        return "myprofile";
    }


    @GetMapping("/users/{id}")
    public String getSingleAppUserPage(Model m, Principal p, @PathVariable String id) {
        long ID = Long.parseLong(id);
        ApplicationUser appUser = applicationUserRepository.findById(ID);
        m.addAttribute("appUser", appUser);
        m.addAttribute("principal", p.getName());

        return "singleappuser";
    }

    @GetMapping("/users")
    public String getUsersPage(Principal p, Model m) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        Iterable<ApplicationUser> users = applicationUserRepository.findAll();
        m.addAttribute("appUser",appUser);
        m.addAttribute("principal", p.getName());
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/following")
    public String getFollowingPage(Principal p, Model m) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        Iterable<ApplicationUser> users = appUser.following;
        m.addAttribute("appUser",appUser);
        m.addAttribute("principal", p.getName());
        m.addAttribute("users", users);
        return "following";
    }

    @PostMapping("/follow/{id}")
    public RedirectView followUser(Principal p, @PathVariable long id) throws ParseException {

        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser userToFollow = applicationUserRepository.findById(id);

        loggedInUser.following.add(userToFollow);
        userToFollow.followers.add(loggedInUser);
        applicationUserRepository.save(loggedInUser);
        applicationUserRepository.save(userToFollow);

        return new RedirectView("/users");
    }
    public class CodefellowshipController {

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
}
