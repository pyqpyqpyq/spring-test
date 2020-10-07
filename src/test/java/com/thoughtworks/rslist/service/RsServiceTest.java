package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class RsServiceTest {
  RsService rsService;

  @Mock RsEventRepository rsEventRepository;
  @Mock UserRepository userRepository;
  @Mock VoteRepository voteRepository;
  LocalDateTime voteTime;
  Vote vote;

  @BeforeEach
  void setUp() {
    initMocks(this);
    rsService = new RsService(rsEventRepository, userRepository, voteRepository);
    voteTime = LocalDateTime.now();
  }

  @Test
  void should_vote_auccess(){
    int userHasVOteNum=20;//15
    int rsEventHasVoteNum=30;//35
    int voteNum=5;
    int userId=12;
    int eventId=15;

    Vote vote= Vote.builder().voteNum(voteNum)
            .userId(userId)
            .rsEventId(eventId)
            .time(voteTime)
            .build();
    rsService.vote(vote,eventId);

    VoteDto voteDto=null;

    verify(voteRepository).save(voteDto);
  }
//  @BeforeEach
//  void setUp() {
//    initMocks(this);
//    rsService = new RsService(rsEventRepository, userRepository, voteRepository);
//    localDateTime = LocalDateTime.now();
//    vote = Vote.builder().voteNum(2).rsEventId(1).time(localDateTime).userId(1).build();
//  }
//
//  @Test
//  void shouldVoteSuccess() {
//    // given
//
//    UserDto userDto =
//        UserDto.builder()
//            .voteNum(5)
//            .phone("18888888888")
//            .gender("female")
//            .email("a@b.com")
//            .age(19)
//            .userName("xiaoli")
//            .id(2)
//            .build();
//    RsEventDto rsEventDto =
//        RsEventDto.builder()
//            .eventName("event name")
//            .id(1)
//            .keyword("keyword")
//            .voteNum(2)
//            .user(userDto)
//            .build();
//
//    when(rsEventRepository.findById(anyInt())).thenReturn(Optional.of(rsEventDto));
//    when(userRepository.findById(anyInt())).thenReturn(Optional.of(userDto));
//    // when
//    rsService.vote(vote, 1);
//    // then
//    verify(voteRepository)
//        .save(
//            VoteDto.builder()
//                .num(2)
//                .localDateTime(localDateTime)
//                .user(userDto)
//                .rsEvent(rsEventDto)
//                .build());
//    verify(userRepository).save(userDto);
//    verify(rsEventRepository).save(rsEventDto);
//  }
//
//  @Test
//  void shouldThrowExceptionWhenUserNotExist() {
//    // given
//    when(rsEventRepository.findById(anyInt())).thenReturn(Optional.empty());
//    when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
//    //when&then
//    assertThrows(
//        RuntimeException.class,
//        () -> {
//          rsService.vote(vote, 1);
//        });
//  }
}
