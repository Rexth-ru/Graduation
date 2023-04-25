package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
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
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.en.Role;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.MyUserDetailsService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdRepository adRepository;
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
    private final CreateAds createAds = new CreateAds();
    private final Ad ad = new Ad();
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

        createAds.setTitle("asdfg");
        createAds.setDescription("asdfgh");
        createAds.setPrice(1200);

        ad.setTitle("asdfg");
        ad.setDescription("asdfgh");
        ad.setPrice(1200);
        ad.setAuthor(userEntity);
        adRepository.save(ad);
    }

    @AfterEach
    void clear() {
        adRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllAds() throws Exception {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    void addAd() throws Exception {
        MockPart mockPart = new MockPart("properties", objectMapper.writeValueAsBytes(createAds));

        mockMvc.perform(multipart("/ads")
                        .part(file)
                        .part(mockPart)
                        .with(authentication(authentication)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(createAds.getTitle()));
    }

    @Test
    void getAds() throws Exception {
        mockMvc.perform(get("/ads/{id}", ad.getId())
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ad.getId()))
                .andExpect(jsonPath("$.title").value(ad.getTitle()));

    }

    @Test
    void updateAds() throws Exception {
        String title = "qwert";
        String description = "qwerty";
        Integer price = 1500;
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setPrice(price);
        adRepository.save(ad);

        mockMvc.perform(patch("/ads/{id}", ad.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ad))
                        .with((authentication(authentication))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.price").value(price));
    }

    @Test
    void getAdsMe() throws Exception {
        mockMvc.perform(get("/ads/me")
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    void deleteAd() throws Exception {
        mockMvc.perform(delete("/ads/{id}", ad.getId())
                        .with(authentication(authentication)))
                .andExpect(status().isOk());
    }

}