package yjp.wp.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {
    private String userId;
    private String nickname;

    public MemberInfoDto() {
    }

    @QueryProjection
    public MemberInfoDto(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
