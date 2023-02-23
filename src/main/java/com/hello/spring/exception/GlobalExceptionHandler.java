package com.hello.spring.exception;

import com.hello.spring.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ResponseDto<?>> notFoundMember() {
        return new ResponseEntity<>(ResponseDto.fail("회원을 찾을 수 없습니다.", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatMemberException.class)
    public ResponseEntity<ResponseDto<?>> duplicateMember() {
        return new ResponseEntity<>(ResponseDto.fail("중복된 username 입니다.", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseDto<?>> invalidToken() {
        return new ResponseEntity<>(ResponseDto.fail("토큰이 유효하지 않습니다.", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ResponseDto<?>> permission() {
        return new ResponseEntity<>(ResponseDto.fail("작성자만 삭제/수정 할 수 있습니다.", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundContentsException.class)
    public ResponseEntity<ResponseDto<?>> notFoundContents() {
        return new ResponseEntity<>(ResponseDto.fail("해당 목록을 찾을 수 없습니다.", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> methodArgumentNotValid() {
        return new ResponseEntity<>(ResponseDto.fail("아이디와 비밀번호 형식이 잘못되었습니다.", null), HttpStatus.BAD_REQUEST);
    }
}
