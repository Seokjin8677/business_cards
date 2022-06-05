package yjp.wp.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import yjp.wp.dto.CardInfoDto;
import yjp.wp.dto.MemberInfoDto;
import yjp.wp.dto.QCardInfoDto;
import yjp.wp.dto.QMemberInfoDto;

import java.util.List;

import static yjp.wp.domain.QCard.*;
import static yjp.wp.domain.QMember.member;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CardInfoDto> findCardInfo(Pageable pageable) {
        List<CardInfoDto> content = queryFactory
                .select(new QCardInfoDto(
                        card.id,
                        card.title,
                        card.name,
                        card.jobTitle,
                        card.company,
                        card.address.address,
                        card.address.tel,
                        card.address.email,
                        card.imageUrl,
                        card.createdDate,
                        card.modifiedDate
                ))
                .from(card)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(card.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(card.count())
                .from(card);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public MemberInfoDto findCardOwnerInfo(Long CardId) {
        return queryFactory
                .select(new QMemberInfoDto(
                        member.userId,
                        member.nickname
                ))
                .from(card)
                .innerJoin(card.member, member)
                .where(card.id.eq(CardId))
                .fetchOne();
    }
}
