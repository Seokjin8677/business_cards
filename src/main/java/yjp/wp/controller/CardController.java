package yjp.wp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yjp.wp.domain.Card;
import yjp.wp.domain.Role;
import yjp.wp.dto.CardInfoDto;
import yjp.wp.dto.CardSaveForm;
import yjp.wp.dto.MemberInfoDto;
import yjp.wp.dto.PageableList;
import yjp.wp.exception.CardNotFoundException;
import yjp.wp.service.CardService;
import yjp.wp.service.S3Service;
import yjp.wp.service.UserDetailsImpl;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final S3Service s3Service;

    @GetMapping("/cards")
    public String cards(Model model, @RequestParam(defaultValue = "1") Integer page) {
        if (page < 1) {
            page = 1;
        }
        Page<CardInfoDto> productInfo = cardService.findCardInfoPages(page);
        PageableList<CardInfoDto> pageableList = new PageableList<>(productInfo);
        model.addAttribute("pageableList", pageableList);
        return "cardList";
    }

    @GetMapping("/cards/create")
    @PreAuthorize("hasAnyAuthority('MEMBER')")
    public String createCardForm(Model model) {
        model.addAttribute("cardSaveForm", new CardSaveForm());
        return "cardCreateForm";
    }

    @PostMapping("/cards/create")
    @PreAuthorize("hasAnyAuthority('MEMBER')")
    public String createCard(@Valid CardSaveForm cardSaveForm, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResult.hasErrors()) {
            return "cardCreateForm";
        }
        Long cardId = cardService.saveCard(cardSaveForm, userDetails.getMember());
        return "redirect:/cards/" + cardId;
    }

    @GetMapping("/cards/{cardId}")
    public String cardDetail(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            CardInfoDto card = cardService.findCardInfo(cardId);
            MemberInfoDto memberInfoDto = cardService.findCardOwnerInfo(cardId);

            model.addAttribute("nickname", memberInfoDto.getNickname());
            model.addAttribute("card", card);
            // 로그인한 유저가 카드의 주인과 같을 때
            if (userDetails != null) {
                model.addAttribute("isOwner", userDetails.getUsername().equals(memberInfoDto.getUserId()));
            }
        } catch (CardNotFoundException e) {
            return "redirect:/cards";
        }
        return "cardDetail";
    }

    @DeleteMapping("/cards/{cardId}")
    @PreAuthorize("hasAnyAuthority('MEMBER')")
    public String deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 카드의 주인이 같은지 검사
        if (cardService.deleteCard(cardId, userDetails.getMember())) {
            return "redirect:/cards";
        }
        return "redirect:/cards/" + cardId;
    }

    @GetMapping("/cards/edit/{cardId}")
    @PreAuthorize("hasAnyAuthority('MEMBER')")
    public String editCardForm(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            Card card = cardService.findCardById(cardId);
            // 카드의 주인과 요청한 사람이 같은지 검사
            if (card.getMember().equals(userDetails.getMember())) {
                model.addAttribute("cardSaveForm", new CardSaveForm(card));
                // 같으면 수정폼 이동
                return "cardEditForm";
            }
        } catch (CardNotFoundException e) {
            // 해당되는 카드가 없으면 리다이렉트
            return "redirect:/cards/" + cardId;
        }
        // 카드의 주인이 아니면 리다이렉트
        return "redirect:/cards/" + cardId;
    }

    @PostMapping("/cards/edit/{cardId}")
    @PreAuthorize("hasAnyAuthority('MEMBER')")
    public String editCard(@PathVariable Long cardId, @Valid CardSaveForm cardSaveForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cardEditForm";
        }
        try {
            cardService.editCard(cardId, cardSaveForm);
        } catch (CardNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/cards/" + cardId;
    }
}
