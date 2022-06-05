package yjp.wp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yjp.wp.domain.Card;
import yjp.wp.domain.Member;
import yjp.wp.domain.MemberRole;
import yjp.wp.domain.Role;
import yjp.wp.dto.CardSaveForm;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class InitConfig {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    static class InitService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
            /*Member member = new Member("admin", "$2a$10$MRp.NI7rXfZYgqfpZgcMNO.JZEYvd8rXVBAE85JyDQCE.Yf1yn9PW", "admin");
            MemberRole role = new MemberRole(Role.ADMIN, member);
            member.getRoles().add(role);
            em.persist(member);

            Card card = Card.builder()
                    .cardSaveForm(new CardSaveForm("TEST", "홍길동", "CEO", "회사", "서울특별시 강남구 테헤란로 123", "010-1234-1234", "test@test.com"))
                    .member(member)
                    .build();
            em.persist(card);*/
        }
    }
}