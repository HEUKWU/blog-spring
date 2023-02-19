package com.hello.spring.controller;

import com.hello.spring.dto.ReplyRequestDto;
import com.hello.spring.dto.ReplyResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.security.UserDetailsImpl;
import com.hello.spring.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/reply/{id}")
    public ReplyResponseDto addReply(@PathVariable Long id, @RequestBody ReplyRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.addReply(id, dto, userDetails.getUser());
    }

    @PutMapping("api/reply/{id}")
    public ReplyResponseDto updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.update(id, dto, userDetails.getUser());
    }

    @DeleteMapping("api/reply/{id}")
    public StatusResponseDto deleteReply(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.delete(id, userDetails.getUser());
    }

    @PostMapping("/api/reply/like/{id}")
    public StatusResponseDto replyLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.like(id, userDetails.getUser());
    }
}
