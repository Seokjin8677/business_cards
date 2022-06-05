package yjp.wp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjp.wp.dto.CardInfoDto;
import yjp.wp.dto.CardSaveForm;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address;
    private String tel;
    private String email;

    public Address(CardSaveForm cardSaveForm) {
        this(cardSaveForm.getAddress(), cardSaveForm.getTel(), cardSaveForm.getEmail());
    }

    public Address(CardInfoDto cardInfoDto) {
        this(cardInfoDto.getAddress(), cardInfoDto.getTel(), cardInfoDto.getEmail());
    }

    public Address(String address, String tel, String email) {
        this.address = address;
        this.tel = tel;
        this.email = email;
    }
}
