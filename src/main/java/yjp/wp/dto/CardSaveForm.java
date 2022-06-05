package yjp.wp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import yjp.wp.domain.Card;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CardSaveForm {
    @NotBlank
    @Length(min = 3, max = 20)
    private String title;

    @NotBlank
    @Length(min = 2, max = 10)
    private String name;

    @NotBlank
    @Length(min = 2, max = 10)
    private String jobTitle;

    @NotBlank
    @Length(min = 2, max = 15)
    private String company;

    @NotBlank
    @Length(min = 2, max = 35)
    private String address;

    @NotBlank
    @Length(min = 2, max = 14)
    private String tel;

    @Email
    @Length(min = 2, max = 35)
    private String email;

    private MultipartFile image;

    public CardSaveForm() {
    }

    public CardSaveForm(Card card) {
        this(
                card.getTitle(),
                card.getName(),
                card.getJobTitle(),
                card.getCompany(),
                card.getAddress().getAddress(),
                card.getAddress().getTel(),
                card.getAddress().getEmail()
        );
    }

    public CardSaveForm(String title, String name, String jobTitle, String company, String address, String tel, String email) {
        this.title = title;
        this.name = name;
        this.jobTitle = jobTitle;
        this.company = company;
        this.address = address;
        this.tel = tel;
        this.email = email;
    }
}
