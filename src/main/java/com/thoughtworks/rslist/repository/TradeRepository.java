package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.TradeDto;
import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends CrudRepository<TradeDto, Integer> {
    List<TradeDto> findAll();
    Optional<TradeDto> findAllByRank(int rank);
}
