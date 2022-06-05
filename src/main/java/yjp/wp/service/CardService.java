package yjp.wp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjp.wp.domain.Card;
import yjp.wp.domain.Member;
import yjp.wp.dto.CardInfoDto;
import yjp.wp.dto.CardSaveForm;
import yjp.wp.dto.MemberInfoDto;
import yjp.wp.exception.CardNotFoundException;
import yjp.wp.repository.CardRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final S3Service s3Service;

    public Page<CardInfoDto> findCardInfoPages(Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 6);
        return cardRepository.findCardInfo(pageRequest);
    }

    public Long saveCard(CardSaveForm cardSaveForm, Member member) {
        Card card = Card.builder()
                .cardSaveForm(cardSaveForm)
                .member(member)
                .imageUrl(s3Service.upload(cardSaveForm.getImage()))
                .build();

        return cardRepository.save(card).getId();
    }

    public CardInfoDto findCardInfo(Long CardId) {
        Card card = findCardById(CardId);
        return new CardInfoDto(card);
    }

    public Card findCardById(Long CardId) {
        return cardRepository.findById(CardId).orElseThrow(() -> new CardNotFoundException("해당되는 카드가 없습니다."));
    }

    public boolean deleteCard(Long CardId, Member member) {
        Card card = findCardById(CardId);
        if (card.getMember().equals(member)) {
            cardRepository.delete(card);
            return true;
        }
        return false;
    }

    public void editCard(Long cardId, CardSaveForm cardSaveForm) {
        Card card = findCardById(cardId);
        card.updateCard(cardSaveForm);
        if (!cardSaveForm.getImage().isEmpty()) {
            s3Service.deleteFile(card.getImageUrl());
            String imageUrl = s3Service.upload(cardSaveForm.getImage());
            card.changeImage(imageUrl);
        }
    }

    public MemberInfoDto findCardOwnerInfo(Long CardId) {
        return cardRepository.findCardOwnerInfo(CardId);
    }
}
