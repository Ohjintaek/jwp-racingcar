package racingcar.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Validator {

    public static final int MIN_TRY_COUNT = 1;
    public static final int MAX_TRY_COUNT = 100;
    public static final int MIN_NAME_LENGTH = 0;

    public static void checkRange(int tryCount) {
        if (tryCount < MIN_TRY_COUNT || tryCount > MAX_TRY_COUNT) {
            throw new IllegalArgumentException("[ERROR] : 시도 횟수는 1 이상 100 이하의 양의 정수만 가능합니다.");
        }
    }

    public static void checkBlank(String carName) {
        if (carName.isBlank()) {
            throw new IllegalArgumentException("[ERROR] : 빈 이름은 들어올 수 없습니다");
        }
    }

    public static void checkLength(String carName) {
        if (carName.length() < 1 || carName.length() > 5) {
            throw new IllegalArgumentException("[ERROR] : 이름 길이는 1~5자 이하여야 합니다");
        }
    }

    public static void checkDuplication(String[] carNameArr) {
        Set<String> set = new HashSet<>(Arrays.asList(carNameArr));
        if (set.size() != carNameArr.length) {
            throw new IllegalArgumentException("[ERROR] : 자동차 이름이 중복되었습니다");
        }
    }

    public static void checkEmpty(String[] carNameArr) {
        if (carNameArr.length == MIN_NAME_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 빈 이름들은 입력할 수 없습니다.");
        }
    }
}
