package org.example.carebridge.domain.user.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.user.enums.OAuth;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.enums.UserStatus;
import org.example.carebridge.global.entity.BaseEntity;

import java.util.Date;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private OAuth oAuth = OAuth.LOCAL;

    public User() {
    }

    @Builder
    public User(String email, String password, String userName, String phoneNum, String address, Date birthday) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.birthday = birthday;
    }

    public void updateStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void updateUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void updateProfile(String address, String phoneNum, String profileImageUrl) {
        this.address = address;
        this.phoneNum = phoneNum;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void inputGoogleOAuth(OAuth oAuth) {
        this.oAuth = OAuth.GOOGLE;
    }

    public Boolean isGoogleUser() {
        return this.oAuth.equals(OAuth.GOOGLE);
    }

    public Boolean isPatient() {
        return this.userRole == UserRole.USER;
    }

    public Boolean isDoctor() {
        return this.userRole == UserRole.DOCTOR;
    }

    //


}
