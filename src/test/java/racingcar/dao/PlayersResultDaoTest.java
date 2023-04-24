package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.dto.PlayerResultDto;
import racingcar.dto.PlayResultDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
public class PlayersResultDaoTest {
    private PlayResultDao playResultDao;
    private PlayersResultDao playersResultDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        playResultDao = new PlayResultDao(jdbcTemplate);
        playersResultDao = new PlayersResultDao(jdbcTemplate);
    }

    @Test
    void insertWithMapAndCount() {
        final PlayResultDto playResultDto = new PlayResultDto("포비", 10,
                List.of(new PlayerResultDto("포비", 10),
                        new PlayerResultDto("브라운", 5), new PlayerResultDto("구구", 8)));

        final int newId = playResultDao.insertResult(playResultDto.toEntity());
        playersResultDao.insertResult(playResultDto.getRacingCars()
                .stream()
                .map(carDto -> carDto.toEntity(newId))
                .collect(Collectors.toList()));

        final String sql = "select count (*) from players_result";
        assertThat(jdbcTemplate.queryForObject(sql, Integer.class)).isEqualTo(3);
    }
}
