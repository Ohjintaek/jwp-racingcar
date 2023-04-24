package racingcar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import racingcar.dto.PlayerResultDto;
import racingcar.dto.RacingGameResultDto;
import racingcar.service.RacingGameService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RacingGameController.class)
public class RacingGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RacingGameService racingGameService;

    @Test
    void playGame() throws Exception {
        when(racingGameService.play(any())).thenReturn(new RacingGameResultDto("포비", List.of(new PlayerResultDto("포비", 10), new PlayerResultDto("브라운", 4), new PlayerResultDto("구구", 6))));

        this.mockMvc
                .perform(post("/plays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.winners").value("포비"))
                .andExpect(jsonPath("$.racingCars").isArray());
    }

    @Test
    void exceptionHandler() throws Exception {
        when(racingGameService.play(any())).thenThrow(new IllegalArgumentException("ERROR"));

        this.mockMvc
                .perform(post("/plays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"names\" : \"포비, , 구구\", \"count\": \"10\"}"))
                .andExpect(status().isBadRequest());
    }
}
