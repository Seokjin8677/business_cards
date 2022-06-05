package yjp.wp.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjp.wp.dto.CardSaveForm;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Card extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String name;
    private String jobTitle;
    private String company;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Address address;

    @Builder
    public Card(CardSaveForm cardSaveForm,String imageUrl, Member member) {
        this.title = cardSaveForm.getTitle();
        this.name = cardSaveForm.getName();
        this.jobTitle = cardSaveForm.getJobTitle();
        this.company = cardSaveForm.getCompany();
        this.address = new Address(cardSaveForm);
        this.imageUrl = imageUrl;
        this.member = member;
    }

    public void updateCard(CardSaveForm cardSaveForm) {
        this.title = cardSaveForm.getTitle();
        this.name = cardSaveForm.getName();
        this.jobTitle = cardSaveForm.getJobTitle();
        this.company = cardSaveForm.getCompany();
        this.address = new Address(cardSaveForm);
    }

    public void changeImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
