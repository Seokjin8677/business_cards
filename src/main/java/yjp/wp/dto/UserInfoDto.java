package yjp.wp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfoDto {
    private String nickname;
    private LocalDateTime createdDate;
}
