package com.example.demo.Infrastructure;

import com.example.demo.Model.ApplicationUser;
import com.example.demo.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAppUserIn(Set<ApplicationUser> appUserList);

}
