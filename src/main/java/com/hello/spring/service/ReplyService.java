package com.hello.spring.service;

import com.hello.spring.dto.ReplyRequestDto;
import com.hello.spring.dto.ReplyResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.entity.Blog;
import com.hello.spring.entity.Reply;
import com.hello.spring.entity.User;
import com.hello.spring.entity.UserRoleEnum;
import com.hello.spring.jwt.JwtUtil;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.repository.ReplyRepository;
import com.hello.spring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ReplyResponseDto addReply(Long id,ReplyRequestDto dto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            Claims claims = getClaims(token);

            User user = validateUser(claims);

            Blog blog = blogRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            Reply reply = replyRepository.saveAndFlush(new Reply(dto, blog, user));

            blog.addReply(reply);

            return new ReplyResponseDto(reply, user);
        } else{
            return null;
        }
    }

    @Transactional
    public ReplyResponseDto update(Long id, ReplyRequestDto dto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            Claims claims = getClaims(token);

            User user = validateUser(claims);

            UserRoleEnum role = user.getRole();
            Reply reply;

            if (role == UserRoleEnum.USER) {
                reply = replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new NullPointerException("수정 권한이 없습니다.")
                );
            } else {
                reply = replyRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
                );
            }

            reply.update(dto, user);

            return new ReplyResponseDto(reply);
        } else {
            return null;
        }
    }

    @Transactional
    public StatusResponseDto delete(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            Claims claims = getClaims(token);

            User user = validateUser(claims);

            UserRoleEnum role = user.getRole();

            if (role == UserRoleEnum.USER) {
                Reply reply = replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new IllegalArgumentException("삭제 권한이 없습니다.")
                );
                replyRepository.deleteById(reply.getId());
            } else {
                replyRepository.findById(id);
            }

            return new StatusResponseDto("댓글 삭제 성공", 200);
        } else {
            return null;
        }
    }

    private User validateUser(Claims claims) {
        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
    }

    private Claims getClaims(String token) {
        Claims claims;
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalStateException("Token Error");
        }
        return claims;
    }
}
