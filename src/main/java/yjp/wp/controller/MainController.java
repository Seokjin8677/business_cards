package yjp.wp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import yjp.wp.dto.SigninForm;
import yjp.wp.dto.SignupForm;
import yjp.wp.service.MemberService;
import yjp.wp.service.UserDetailsImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String signinPage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        model.addAttribute("signinForm", new SigninForm());
        return "signinPage";
    }

    @PostMapping("/signin")
    public String signin(@Valid SigninForm signinForm, BindingResult bindingResult) {
        return "signinPage";
    }

    @GetMapping("/signup")
    public String signupPage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        model.addAttribute("signupForm", new SignupForm());
        return "signupPage";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult bindingResult) {
        signupValidate(signupForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signupPage";
        }
        memberService.signup(signupForm);
        return "redirect:/signin";
    }

    private void signupValidate(SignupForm signupForm, BindingResult bindingResult) {
        if (!signupForm.getPassword().equals(signupForm.getConfirmPassword())) {
            bindingResult.rejectValue("password","notMatch");
        }
        if (memberService.duplicateUseridCheck(signupForm.getUserId())) {
            bindingResult.rejectValue("userId", "duplicate");
        }
        if (memberService.duplicateNicknameCheck(signupForm.getNickname())) {
            bindingResult.rejectValue("nickname", "duplicate");
        }
    }
}
