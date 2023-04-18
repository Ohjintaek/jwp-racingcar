package racingcar.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import racingcar.dto.PlayerResultDto;
import racingcar.entity.PlayerResultEntity;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlayersResultDao {
    private final SimpleJdbcInsert insertActor;
    private final JdbcTemplate jdbcTemplate;

    public PlayersResultDao(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("players_result")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PlayerResultEntity> playersResultRowMapper = (resultSet, rowNum) -> {
        PlayerResultEntity playerResultEntity = new PlayerResultEntity(
                resultSet.getInt("id"),
                resultSet.getInt("result_id"),
                resultSet.getString("name"),
                resultSet.getInt("position")
        );
        return playerResultEntity;
    };

    public void insertResult(final List<PlayerResultDto> playerResultDtos, final int resultId) {
        for (final PlayerResultDto playerResultDto : playerResultDtos) {
            final Map<String, Object> params = new HashMap<>();
            params.put("name", playerResultDto.getName());
            params.put("position", playerResultDto.getPosition());
            params.put("result_id", resultId);
            insertActor.execute(params);
        }
    }

    public List<PlayerResultEntity> getResultByResultId(final int resultId) {
        String sql = "select * from players_result where result_id = ?";
        return jdbcTemplate.query(sql, playersResultRowMapper, resultId);
    }
}