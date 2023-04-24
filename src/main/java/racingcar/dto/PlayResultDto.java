package racingcar.dto;

import racingcar.domain.Car;
import racingcar.domain.RacingGame;
import racingcar.entity.PlayResultEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PlayResultDto {
    private final String winners;

    private final int playCount;

    private final List<PlayerResultDto> racingCars;

    public PlayResultDto(final RacingGame racingGame) {
        this.winners = racingGame.getWinners()
                .stream()
                .map(Car::getName)
                .collect(Collectors.joining(","));

        this.playCount = racingGame.getPlayCount();

        this.racingCars = racingGame.getCars()
                .stream()
                .map(PlayerResultDto::new)
                .collect(Collectors.toList());
    }

    public PlayResultDto(final String winners, final int playCount, final List<PlayerResultDto> playerResultDtos) {
        this.winners = winners;
        this.playCount = playCount;
        this.racingCars = playerResultDtos;
    }

    public String getWinners() {
        return winners;
    }

    public int getPlayCount() {
        return playCount;
    }

    public List<PlayerResultDto> getRacingCars() {
        return racingCars;
    }

    public PlayResultEntity toEntity() {
        return new PlayResultEntity(null, winners, playCount, null);
    }
}
