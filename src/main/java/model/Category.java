package model;

import java.util.function.Function;

public enum Category {
    CHANCE(DiceRoll::sum),
    YATZY(dice -> dice.allSame() ? 50 : 0),
    ONES(dice -> dice.sumOf(1)),
    TWOS(dice -> dice.sumOf(2)),
    THREES(dice -> dice.sumOf(3)),
    FOURS(dice -> dice.sumOf(4)),
    FIVES(dice -> dice.sumOf(5)),
    SIXES(dice -> dice.sumOf(6)),
    PAIR(dice -> dice.highestNOfAKind(2)),
    TWO_PAIRS(DiceRoll::twoPairs),
    THREE_OF_A_KIND(dice -> dice.nOfAKind(3)),
    FOUR_OF_A_KIND(dice -> dice.nOfAKind(4)),
    SMALL_STRAIGHT(dice -> dice.matchesExact(1, 2, 3, 4, 5) ? 15 : 0),
    LARGE_STRAIGHT(dice -> dice.matchesExact(2, 3, 4, 5, 6) ? 20 : 0),
    FULL_HOUSE(DiceRoll::fullHouse);

    private final Function<DiceRoll, Integer> scorer;

    Category(Function<DiceRoll, Integer> scorer) {
        this.scorer = scorer;
    }

    public int score(DiceRoll dice) {
        return scorer.apply(dice);
    }
}
