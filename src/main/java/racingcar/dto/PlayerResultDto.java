package racingcar.dto;

import racingcar.domain.Car;
import racingcar.entity.PlayerResultEntity;

public class PlayerResultDto {
    String name;
    int position;

    public PlayerResultDto(final Car car) {
        this.name = car.getName();
        this.position = car.getPosition();
    }

    public PlayerResultDto(final String name, final int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public PlayerResultEntity toEntity(final int resultId) {
        return new PlayerResultEntity(null, resultId, name, position);
    }
}
