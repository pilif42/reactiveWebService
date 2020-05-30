package com.sample.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private String role;
}
