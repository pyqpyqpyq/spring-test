package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Trade;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.TradeDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.TradeRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RsService {
    final RsEventRepository rsEventRepository;
    final UserRepository userRepository;
    final VoteRepository voteRepository;
    final TradeRepository tradeRepository;

    public RsService(RsEventRepository rsEventRepository,
                     UserRepository userRepository,
                     VoteRepository voteRepository, TradeRepository tradeRepository
    ) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.tradeRepository = tradeRepository;
    }

    public List<RsEvent> getAllRsEvents() {
        List<RsEvent> rsEventList = rsEventRepository.findAll().stream().map(
                item -> RsEvent.builder()
                        .userId(item.getId())
                        .eventName(item.getEventName())
                        .keyword(item.getKeyword())
                        .voteNum(item.getVoteNum())
                        .build()
        ).collect(Collectors.toList());
        return sortRsEventList(rsEventList);
    }

//------------------
  //TODO
//------------------
    public void vote(Vote vote, int rsEventId) {
        Optional<RsEventDto> rsEventDto = rsEventRepository.findById(rsEventId);
        Optional<UserDto> userDto = userRepository.findById(vote.getUserId());
        if (!rsEventDto.isPresent()
                || !userDto.isPresent()
                || vote.getVoteNum() > userDto.get().getVoteNum()) {
            throw new RuntimeException();
        }
        VoteDto voteDto =
                VoteDto.builder()
                        .localDateTime(vote.getTime())
                        .num(vote.getVoteNum())
                        .rsEvent(rsEventDto.get())
                        .user(userDto.get())
                        .build();
        voteRepository.save(voteDto);
        UserDto user = userDto.get();
        user.setVoteNum(user.getVoteNum() - vote.getVoteNum());
        userRepository.save(user);
        RsEventDto rsEvent = rsEventDto.get();
        rsEvent.setVoteNum(rsEvent.getVoteNum() + vote.getVoteNum());
        rsEventRepository.save(rsEvent);
    }

    public void buy(Trade trade, int id) {
        Optional<RsEventDto> rsEventDto = rsEventRepository.findById(id);
        Optional<UserDto> userDto = userRepository.findById(trade.getUserId());
        Optional<TradeDto> tradeDtos = tradeRepository.findAllByRank(trade.getRank());
        if (tradeDtos.isPresent() && tradeDtos.get().getAmount() >= trade.getAmount()) {
            throw new RuntimeException();
        } else if (!tradeDtos.isPresent()) {
            TradeDto tradeDto = TradeDto
                    .builder()
                    .amount(trade.getAmount())
                    .rsEvent(rsEventDto.get())
                    .user(userDto.get())
                    .rank(trade.getRank())
                    .build();
            userRepository.save(userDto.get());
            tradeRepository.save(tradeDto);
            rsEventRepository.save(rsEventDto.get());
        } else {
            TradeDto tradeDto = TradeDto
                    .builder()
                    .amount(trade.getAmount())
                    .rsEvent(rsEventDto.get())
                    .user(userDto.get())
                    .rank(trade.getRank()).id(tradeDtos.get().getId())
                    .build();
            userRepository.save(userDto.get());
            tradeRepository.save(tradeDto);
            rsEventRepository.save(rsEventDto.get());
        }

    }
}
