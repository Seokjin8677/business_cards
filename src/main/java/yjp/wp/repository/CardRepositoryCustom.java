package yjp.wp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yjp.wp.dto.CardInfoDto;
import yjp.wp.dto.MemberInfoDto;

public interface CardRepositoryCustom {
    Page<CardInfoDto> findCardInfo(Pageable pageable);

    MemberInfoDto findCardOwnerInfo(Long CardId);
}
