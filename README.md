# calculator-interpreter

## Overview
The `calculator-interpreter` is a Java-based interpreter for evaluating mathematical expressions. It supports various arithmetic operations, unary and binary operators, and variable assignments.

## Features
- Supports basic arithmetic operations: addition, subtraction, multiplication, and division.
- Handles unary operators like increment (`++`) and decrement (`--`).
- Supports variable assignments and expressions involving variables.
- Parses and evaluates complex expressions.

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/dordor12/calculator-interpreter.git
    cd calculator-interpreter
    ```

2. Build the project using Gradle:
    ```sh
    gradle build
    ```

### Running the Application
To run the application, use the following command:
```sh
gradlew bootRun --args=path_to_tour_file
gradlew bootRun --args="./src/main/resources/test.txt"
```

### Example Input File
```
i = 10
j = 20
x = -(i + --j) * 2 - --j
result = 4 + + - + - - 6 + - + - ++i - - - + 3 * 2
i = 7 * 4 / 3
c = (3 * (2 + (1 * (2 + 3))))
```
