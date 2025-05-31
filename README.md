# Yatzy Java Simulation

- This project simulates the classic Yatzy dice game in Java using clean code principles, object-oriented design, and a testable structure.
---

# Stack

- Java 21
- Gradle 8.13
- AssertJ
- Junit5

---

## Problem Statement

- Score a **given roll** in a **given category** based on Yatzy rules. The game is **not** about choosing the best category automatically — it's about scoring a roll in a specified category.
---

## Features Implemented

- Java-based scoring logic for all official Yatzy categories.
- Clean and extensible domain model using enums, strategies, and output abstractions.
- Support for Console and File outputs using Strategy Pattern.
- Full simulation of a Yatzy game session with **3 predefined rolls**.
- Scoring logic follows official Yatzy rules strictly.
- Unit tests using JUnit 5 and AssertJ

---

## Recent Enhancements

-  Refactored `Category` enum to centralize all scoring logic.
-  Added new `GameSimulator` class for full game simulation.
-  Added `FileOutput` and `OutputStrategy` for file-based output support.
-  Integrated tests for:
    - Dice logic (`DiceRoll`)
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
