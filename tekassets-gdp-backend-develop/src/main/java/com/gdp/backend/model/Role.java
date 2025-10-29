package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role extends Base{

    @Column(name = "Role")
    private String role;

    @JsonBackReference

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.ALL)
    private List<GDPUser> users;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<GDPUser> getUsers() {
        return users;
    }

    public void setUsers(List<GDPUser> users) {
        this.users = users;
    }
}
