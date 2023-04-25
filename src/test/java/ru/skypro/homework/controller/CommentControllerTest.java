package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.en.Role;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.MyUserDetailsService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private Authentication authentication;
    private final UserEntity userEntity = new UserEntity();
    private final Ad ad = new Ad();
    private final CommentEntity commentEntity = new CommentEntity();
    private final Comment comment = new Comment();
    private final JSONObject jsonObject = new JSONObject();

    @BeforeEach
    void setUp() throws JSONException {
        userEntity.setEmail("asd@sd.ru");
        userEntity.setPassword(passwordEncoder.encode("qwert12345"));
        userEntity.setFirstName("Ant");
        userEntity.setLastName("Cont");
        userEntity.setPhone("+79999990888");
        userEntity.setRole(Role.USER);
        userEntity.setEnabled(true);
        userRepository.save(userEntity);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEntity.getEmail());
        authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        ad.setTitle("asdfg");
        ad.setDescription("asdfgh");
        ad.setPrice(1200);
        ad.setAuthor(userEntity);
        adRepository.save(ad);

        commentEntity.setAuthor(userEntity);
        commentEntity.setAd(ad);
        commentEntity.setText("qawsed");
        commentEntity.setCreatedAt(Instant.now());

        Collection<CommentEntity> commentEntities = new ArrayList<>();
        commentEntities.add(commentEntity);
        ad.setCommentEntities(commentEntities);

        comment.setText("azsxdcfv");

        jsonObject.put("text", "text");

    }

    @AfterEach
    void clear() {
        commentRepository.deleteAll();
        adRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void addComment() throws Exception {
        mockMvc.perform(post("/ads/{id}/comments", ad.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("text"));
    }

    @Test
    void getComments() throws Exception {
        mockMvc.perform(get("/ads/{id}/comments", ad.getId())
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray());
    }
}