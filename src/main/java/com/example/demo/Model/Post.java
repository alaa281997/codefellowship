package com.example.demo.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String body;
    LocalDate createdAt;

    @ManyToOne
    ApplicationUser appUser;

    public Post(){}

    public Post(String body, LocalDate createdAt, ApplicationUser appUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.appUser = appUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
    }
}