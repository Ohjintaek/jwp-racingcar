package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.dto.CarDto;
import racingcar.dto.RacingGameDto;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
public class PlayersResultDaoTest {
    private PlayResultDao playResultDao;
    private PlayersResultDao playersResultDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        playResultDao = new PlayResultDao(dataSource, jdbcTemplate);
        playersResultDao = new PlayersResultDao(dataSource, jdbcTemplate);
    }

    @Test
    void insertWithMapAndCount() {
        final RacingGameDto racingGameDto = new RacingGameDto("포비", 10,
                List.of(new CarDto("포비", 10),
                        new CarDto("브라운", 5), new CarDto("구구", 8)));

        final int newId = playResultDao.insertResult(racingGameDto);
        playersResultDao.insertResult(racingGameDto.getRacingCars(), newId);

        final String sql = "select count (*) from players_result";
        assertThat(jdbcTemplate.queryForObject(sql, Integer.class)).isEqualTo(3);
    }
}
