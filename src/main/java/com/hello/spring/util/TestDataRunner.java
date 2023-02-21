package com.hello.spring.util;

import com.hello.spring.entity.Blog;
import com.hello.spring.entity.Reply;
import com.hello.spring.entity.User;
import com.hello.spring.entity.UserRoleEnum;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.repository.ReplyRepository;
import com.hello.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ReplyRepository replyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User("user1", passwordEncoder.encode("abcdefgH1!"), UserRoleEnum.USER);
        User user2 = new User("user2", passwordEncoder.encode("abcdefgH1!"), UserRoleEnum.USER);
        User admin = new User("Admin", passwordEncoder.encode("abcdefgH1!"), UserRoleEnum.ADMIN);
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        admin = userRepository.save(admin);

        createBlog("blog1", "content1", user1);
        createBlog("blog2", "content2", user2);

        createReply(1L, "reply1", user1);
        createReply(2L, "reply2", user2);

    }

    private void createBlog(String title, String contents, User user) {
        blogRepository.save(new Blog(title, contents, user));
    }

    private void createReply(Long id, String contents, User user) {
        Blog blog = blogRepository.findById(id).get();
        replyRepository.save(new Reply(contents, blog, user));
    }
}
