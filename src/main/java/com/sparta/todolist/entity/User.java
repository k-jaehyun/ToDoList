package com.sparta.todolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4,max = 10)
    @Pattern(regexp="[a-z0-9]+")
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(min = 8,max = 15)
    @Pattern(regexp="[a-zA-Z0-9]+")
    @Column
    private String password;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
