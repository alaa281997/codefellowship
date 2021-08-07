package com.example.demo.Infrastructure;

import com.example.demo.Model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
ApplicationUser findApplicationUserById(String username);
}
