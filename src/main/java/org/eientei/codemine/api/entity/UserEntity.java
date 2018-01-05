package org.eientei.codemine.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserEntity {
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @Column
    private String password;

    @Column(unique = true)
    private String token;

    @Lob
    @Column
    private byte[] skin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public byte[] getSkin() {
        return skin;
    }

    public void setSkin(byte[] skin) {
        this.skin = skin;
    }
}
