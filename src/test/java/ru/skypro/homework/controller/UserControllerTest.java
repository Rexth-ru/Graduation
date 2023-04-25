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
import org.springframework.mock.web.MockPart;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.en.Role;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.MyUserDetailsService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    private Authentication authentication;
    private final UserEntity userEntity = new UserEntity();
    private final JSONObject jsonObject = new JSONObject();
    private final MockPart file
            = new MockPart("image", "avatar", "avatar".getBytes());

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
    }

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    void setPassword() throws Exception {
        jsonObject.put("newPassword", "123456");
        jsonObject.put("currentPassword", "qwert12345");

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()).with(authentication(authentication)))
                .andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/users/me")
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(userEntity.getFirstName()));
    }

    @Test
    void updateUser() throws Exception {
        userEntity.setFirstName("Mike");
        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEntity)).with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mike"));
    }
}