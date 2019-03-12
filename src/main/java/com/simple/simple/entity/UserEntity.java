package com.simple.simple.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.simple.simple.model.UserModel;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Length(
        min     = 5,
        message = "*Your password must have at least 5 characters"
    )

    @Transient
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active", columnDefinition = "BIT", length = 1)
    private boolean active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name                     = "user_role",
        joinColumns              = @JoinColumn(
            name                 = "user_id",
            referencedColumnName = "user_id"
        ) ,
        inverseJoinColumns       = @JoinColumn(
            name                 = "role_id",
            referencedColumnName = "role_id"
        )
    )
    private Set<RoleEntity> roles;

    public UserModel toModel() {
        UserModel result = new UserModel();

        result.setId(this.id);
        result.setEmail(this.email);
        result.setName(this.name);
        result.setLastName(this.lastName);
        result.setStatus(this.active);

        String roleString = "";

        for (RoleEntity role : roles) {
            roleString += role.getRole() + ",";
        }

        roleString = roleString.replaceAll("\\,$", "");
        result.setRoles(roleString);

        return result;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin() {
        if (roles != null) {
            for (RoleEntity role : roles) {
                if (role.getRole().equals("ADMIN")) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGuest() {
        if (this.id <= 0) {
            return true;
        }

        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

}
