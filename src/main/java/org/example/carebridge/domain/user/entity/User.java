package org.example.carebridge.domain.user.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.carebridge.domain.user.dto.signup.UserPatientSignupRequestDto;
import org.example.carebridge.domain.user.dto.update.UserUpdateRequestDto;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.enums.UserStatus;
import org.example.carebridge.global.entity.BaseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public User() {
    }

    @Builder
    public User(String email, String password, String userName, String phoneNum, String address, Date birthday, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.birthday = birthday;
        this.profileImageUrl = profileImageUrl;
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



}
