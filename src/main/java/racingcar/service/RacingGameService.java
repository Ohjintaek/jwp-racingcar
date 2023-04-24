package racingcar.service;

import org.springframework.stereotype.Service;
import racingcar.dao.PlayResultDao;
import racingcar.dao.PlayersResultDao;
import racingcar.domain.RacingGame;
import racingcar.dto.PlayResultDto;
import racingcar.dto.PlayerResultDto;
import racingcar.dto.RacingGameInputDto;
import racingcar.dto.RacingGameResultDto;
import racingcar.utils.InputUtil;
import racingcar.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RacingGameService {
    private final PlayResultDao playResultDao;

    private final PlayersResultDao playersResultDao;

    public RacingGameService(final PlayResultDao playResultDao, final PlayersResultDao playersResultDao) {
        this.playResultDao = playResultDao;
        this.playersResultDao = playersResultDao;
    }

    public RacingGameResultDto play(final RacingGameInputDto racingGameInputDto) {
        RacingGame racingGame = new RacingGame(InputUtil.splitNames(racingGameInputDto.getNames()),
                racingGameInputDto.getCount());
        racingGame.start();

        PlayResultDto playResultDto = new PlayResultDto(racingGame);
        int resultId = playResultDao.insertResult(playResultDto.toEntity());
        playersResultDao.insertResult(playResultDto.getRacingCars()
                .stream()
                .map(carDto -> carDto.toEntity(resultId))
                .collect(Collectors.toList()));

        RacingGameResultDto racingGameResultDto = new RacingGameResultDto(racingGame);
        OutputView.printResult(racingGameResultDto);
        return racingGameResultDto;
    }

    public List<RacingGameResultDto> requestAllRacingGameResult() {
        return playResultDao.getResult()
                .stream()
                .map(playResultEntity -> requestRacingGameResult(playResultEntity.getId(), playResultEntity.getWinners()))
                .collect(Collectors.toList());
    }

    private RacingGameResultDto requestRacingGameResult(final int resultId, final String winners) {
        return new RacingGameResultDto(winners, playersResultDao.getResultByResultId(resultId)
                .stream()
                .map(playerResultEntity -> new PlayerResultDto(playerResultEntity.getName(), playerResultEntity.getPosition()))
                .collect(Collectors.toList()));
    }
}
