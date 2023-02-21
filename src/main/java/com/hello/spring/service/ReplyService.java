package com.hello.spring.service;

import com.hello.spring.dto.ReplyRequestDto;
import com.hello.spring.dto.ReplyResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.entity.*;
import com.hello.spring.exception.NotFoundContentsException;
import com.hello.spring.exception.PermissionException;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.repository.ReplyLikeRepository;
import com.hello.spring.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BlogRepository blogRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Transactional
    public ReplyResponseDto addReply(Long id, ReplyRequestDto dto, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(NotFoundContentsException::new);
        Reply reply = replyRepository.save(new Reply(dto.getContents(), blog, user));

        return new ReplyResponseDto(reply);
    }

    @Transactional
    public ReplyResponseDto update(Long id, ReplyRequestDto dto, User user) {
        Reply reply = getReply(id, user);
        reply.update(dto.getContents());

        return new ReplyResponseDto(reply);
    }

    @Transactional
    public StatusResponseDto delete(Long id, User user) {
        Reply reply = getReply(id, user);
        replyRepository.deleteById(reply.getId());

        return new StatusResponseDto("댓글 삭제 성공", 200);
    }

    @Transactional
    public StatusResponseDto like(Long id, User user) {
        Reply reply = replyRepository.findById(id).orElseThrow(NotFoundContentsException::new);

        return getStatusResponseDto(user, reply);
    }

    //메서드 추출
    private Reply getReply(Long id, User user) {
        return (user.getRole() == UserRoleEnum.USER) ?
                replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(PermissionException::new) :
                replyRepository.findById(id).orElseThrow(NotFoundContentsException::new);
    }

    private StatusResponseDto getStatusResponseDto(User user, Reply reply) {
        Optional<ReplyLike> found = replyLikeRepository.findByUserId(user.getId());
        if (found.isPresent()) {
            replyLikeRepository.deleteReplyLikeByUserId(user.getId());
            return new StatusResponseDto("좋아요 취소", 200);
        }
        replyLikeRepository.save(new ReplyLike(reply, user));
        return new StatusResponseDto("좋아요 성공", 200);
    }
}
