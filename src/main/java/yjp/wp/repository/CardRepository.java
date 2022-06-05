package yjp.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjp.wp.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

}

