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
./gradlew bootRun --args=path_to_tour_file
./gradlew bootRun --args="./src/main/resources/test.txt"
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

### Running test
```sh
./gradlew test
```

### Extending the Project to Handle More Operators
To extend the project to handle more operators, follow these steps:

1. Define the new token that need to parse:
```java
public enum TokenType {
    PLUS("+"),
    MINUS("-"),
    ...
    // add new token here
    PRECENT("%") // Example new token
}
```

2. Define the new operator in the `Operator` enum:

```java
public enum Operator {
    BINARY_ADDITION(OperatorType.BinaryOperator, TokenType.PLUS, 1),
    BINARY_SUBTRACTION(OperatorType.BinaryOperator, TokenType.MINUS, 2),
    ....
      // Add new operators here
    BINARY_MODULUS(OperatorType.BinaryOperator, TokenType.PRECENT, 2) // Example new operator
  
}
```

3. create the interpeter for this operator

```java

@Component
public class DivisionExpressionIntr extends NumberExprIntr {
    @Override
    public Operator getInterpOperator() {
        return Operator.BINARY_MODULUS; // retun the type that this class intrepert
    }

    @Override
    public Value interpert(Expression expression, BiFunction<Expression, CalcTypes, Value> recusiveInterp) {
        var binary = (BinaryOperator) expression;
        var left = (float) recusiveInterp.apply(binary.getLeft(), getInterperterType()).getValue();
        var right = (float) recusiveInterp.apply(binary.getRight(), getInterperterType()).getValue();
        return new Value(CalcTypes.NUMBER, left % right); // actual logic here
    }    
}
```