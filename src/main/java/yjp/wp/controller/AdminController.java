package yjp.wp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yjp.wp.domain.Card;
import yjp.wp.dto.CardSaveForm;
import yjp.wp.service.CardService;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    private final CardService cardService;

    @GetMapping("/admin/cards/edit/{cardId}")
    String adminEditCard(@PathVariable Long cardId, Model model) {
        Card card = cardService.findCardById(cardId);
        model.addAttribute("cardSaveForm", new CardSaveForm(card));
        return "cardEditForm";
    }

    @DeleteMapping("/admin/cards/{cardId}")
    public String deleteCard(@PathVariable Long cardId) {
        cardService.forceDeleteCard(cardId);
        return "redirect:/cards/" + cardId;
    }
}
