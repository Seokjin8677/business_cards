package yjp.wp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninForm {
    @NotBlank(message = "계정을 입력해주세요.")
    @Length(min = 3, max = 20)
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 3, max = 20)
    private String password;
}
