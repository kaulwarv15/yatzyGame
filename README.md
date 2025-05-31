# Yatzy Game Simulator (Java)

A modular, test-driven simulation of the Yatzy dice game in Java, with support for various scoring categories, console and file output, and automated category selection based on optimal score.

---

# Stack

- Java 21
- Gradle 8.13
- AssertJ
- Junit5

---

## Features Implemented

-  **Dice Rolling**: Simulates rolling dice using Java's `Random` class.
-  **Score Calculation**: Supports all major Yatzy categories including:
    - YATZY, FULL_HOUSE, PAIR, TWO_PAIRS, THREE_OF_A_KIND, FOUR_OF_A_KIND
    - SMALL_STRAIGHT, LARGE_STRAIGHT
    - ONES to SIXES
    - CHANCE
-  **Automatic Category Selection**: Picks the best scoring category automatically for each roll.
-  **Flexible Output**: Choose between console output or file output using strategy pattern.
-  **Unit Testing**: JUnit 5 test coverage with over 0.80 code coverage achieved.

---

## Recent Enhancements

-  Refactored `Category` enum to centralize all scoring logic.
-  Added new `GameSimulator` class to automate full game simulation.
-  Added `FileOutput` and `OutputStrategy` for file-based output support.
-  Integrated tests for:
    - Dice logic (`DiceRoll`)
    - All category scoring
    - Category selection logic
    - File output validation
    - Simulation flow using mocked output

---

##  How to Run

1. Compile the project using any IDE or command line.


---



###  Run Tests

Run the following command based on your OS:

```bash
   ./gradlew test       # For Linux
    gradlew.bat test    # For Windows
```

---

### Author
  **Virbhadra Kaulwar**
- GitHub: https://github.com/kaulwarv15/yatzyGame
