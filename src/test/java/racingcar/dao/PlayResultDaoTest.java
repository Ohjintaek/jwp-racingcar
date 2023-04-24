package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.dto.PlayResultDto;
import racingcar.dto.PlayerResultDto;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
public class PlayResultDaoTest {
    private PlayResultDao playResultDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        playResultDao = new PlayResultDao(jdbcTemplate);
    }

    @Test
    void insertWithMapAndCount() {
        final PlayResultDto playResultDto = new PlayResultDto("포비", 10,
                List.of(new PlayerResultDto("포비", 10),
                        new PlayerResultDto("브라운", 5), new PlayerResultDto("구구", 8)));

        playResultDao.insertResult(playResultDto.toEntity());

        final String sql = "select count (*) from play_result";
        assertThat(jdbcTemplate.queryForObject(sql, Integer.class)).isEqualTo(1);
    }

    @Test
    void insertWithMapAndFindWinner() {
        final PlayResultDto playResultDto = new PlayResultDto("토미", 10,
                List.of(new PlayerResultDto("토미", 10),
                        new PlayerResultDto("브라운", 5), new PlayerResultDto("구구", 8)));

        playResultDao.insertResult(playResultDto.toEntity());

        final String sql = "select winners from play_result";
        assertThat(jdbcTemplate.queryForObject(sql, String.class)).isEqualTo("토미");
    }
}
