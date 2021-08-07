package com.example.demo.Web;

import com.example.demo.Infrastructure.ApplicationUserRepository;
import com.example.demo.Infrastructure.PostRepository;
import com.example.demo.Model.ApplicationUser;
import com.example.demo.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository appUserRepository;

    @PostMapping("/posts")
    public RedirectView createPost(Principal p, Model m, String body){
        ApplicationUser user = appUserRepository.findByUsername(p.getName());

        if(user != null){
            LocalDate timestamp =  LocalDate.now();
            Post post = new Post(body, timestamp, user);
            postRepository.save(post);
        }

        m.addAttribute("principal", p.getName());
        m.addAttribute("appUser", user);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/post")
    public String getPostPage() {
        return "postpage";
    }

}
