package yjp.wp.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import yjp.wp.domain.Card;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardInfoDto {
    private Long id;

    private String title;

    private String name;

    private String jobTitle;

    private String company;

    private String address;

    private String tel;

    private String email;

    private String imageUrl;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public CardInfoDto(Card card) {
        this(
                card.getId(),
                card.getTitle(),
                card.getName(),
                card.getJobTitle(),
                card.getCompany(),
                card.getAddress().getAddress(),
                card.getAddress().getTel(),
                card.getAddress().getEmail(),
                card.getImageUrl(),
                card.getCreatedDate(),
                card.getModifiedDate()
        );
    }

    @QueryProjection
    public CardInfoDto(Long id, String title, String name, String jobTitle, String company, String address, String tel, String email,String imageUrl, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.jobTitle = jobTitle;
        this.company = company;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
