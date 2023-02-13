package com.hello.spring.controller;

import com.hello.spring.dto.StatusResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StatusResponseDto> illegalArgumentException() {
        return new ResponseEntity<>(new StatusResponseDto("회원을 찾을 수 없습니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StatusResponseDto> illegalStatementException() {
        return new ResponseEntity<>(new StatusResponseDto("토큰이 유효하지 않습니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StatusResponseDto> nullPointerException() {
        return new ResponseEntity<>(new StatusResponseDto("작성자만 삭제/수정 할 수 있습니다.", 400), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<StatusResponseDto> illegalCallerException() {
        return new ResponseEntity<>(new StatusResponseDto("중복된 username 입니다.", 400), HttpStatus.BAD_GATEWAY);
    }
}
