package yjp.wp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yjp.wp.domain.Member;
import yjp.wp.dto.SignupForm;
import yjp.wp.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Member findOne(Long memberId) {
        return memberRepository.getById(memberId);
    }

    public boolean duplicateUseridCheck(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    public boolean duplicateNicknameCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public void signup(SignupForm signupForm) {
        signupForm.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        Member member = new Member(signupForm.getUserId(), signupForm.getPassword(), signupForm.getNickname());
        memberRepository.save(member);
    }
}
