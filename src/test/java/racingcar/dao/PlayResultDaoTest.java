package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.dto.PlayerResultDto;
import racingcar.dto.RacingGameDto;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlayResultDaoTest {
    @Autowired
    private PlayResultDao playResultDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE PLAYERS_RESULT IF EXISTS");
        jdbcTemplate.execute("DROP TABLE PLAY_RESULT IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE PLAY_RESULT\n"
                + "(\n"
                + "    id         INT         NOT NULL AUTO_INCREMENT,\n"
                + "    winners    VARCHAR(50) NOT NULL,\n"
                + "    play_count INT         NOT NULL,\n"
                + "    created_at DATETIME    NOT NULL default current_timestamp,\n"
                + "    PRIMARY KEY (id)\n"
                + ");");
    }

    @Test
    void insertWithMapAndCount() {
        final RacingGameDto racingGameDto = new RacingGameDto("포비", 10,
                List.of(new PlayerResultDto("포비", 10),
                        new PlayerResultDto("브라운", 5), new PlayerResultDto("구구", 8)));

        playResultDao.insertResult(racingGameDto);

        final String sql = "select count (*) from play_result";
        assertThat(jdbcTemplate.queryForObject(sql, Integer.class)).isEqualTo(1);
    }

    @Test
    void insertWithMapAndFindWinner() {
        final RacingGameDto racingGameDto = new RacingGameDto("토미", 10,
                List.of(new PlayerResultDto("토미", 10),
                        new PlayerResultDto("브라운", 5), new PlayerResultDto("구구", 8)));

        playResultDao.insertResult(racingGameDto);

        final String sql = "select winners from play_result";
        assertThat(jdbcTemplate.queryForObject(sql, String.class)).isEqualTo("토미");
    }
}
