package com.hello.spring.service;

import com.hello.spring.dto.ReplyRequestDto;
import com.hello.spring.dto.ReplyResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.entity.Blog;
import com.hello.spring.entity.Reply;
import com.hello.spring.entity.User;
import com.hello.spring.entity.UserRoleEnum;
import com.hello.spring.exception.NotFoundContentsException;
import com.hello.spring.exception.PermissionException;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public ReplyResponseDto addReply(Long id, ReplyRequestDto dto, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(NotFoundContentsException::new);
        Reply reply = replyRepository.save(new Reply(dto, blog, user));

        return new ReplyResponseDto(reply, user);
    }

    @Transactional
    public ReplyResponseDto update(Long id, ReplyRequestDto dto, User user) {
        Reply reply = getReply(id, user);
        reply.update(dto);

        return new ReplyResponseDto(reply);
    }

    @Transactional
    public StatusResponseDto delete(Long id, User user) {
        Reply reply = getReply(id, user);
        replyRepository.deleteById(reply.getId());

        return new StatusResponseDto("댓글 삭제 성공", 200);
    }

    private Reply getReply(Long id, User user) {
        return (user.getRole() == UserRoleEnum.USER) ?
                replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(PermissionException::new) :
                replyRepository.findById(id).orElseThrow(NotFoundContentsException::new);
    }
}
