package org.example.carebridge.domain.user.dto.update;

import lombok.Getter;
import org.example.carebridge.domain.user.entity.User;

@Getter
public class UserUpdateResponseDto {

    private Long id;

    private String address;

    private String phone;


    public UserUpdateResponseDto(User user) {
        this.id = user.getId();
        this.address = user.getAddress();
        this.phone = user.getPhoneNum();
    }
}
