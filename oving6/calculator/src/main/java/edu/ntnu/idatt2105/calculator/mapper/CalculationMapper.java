package edu.ntnu.idatt2105.calculator.mapper;

import org.springframework.stereotype.Component;
import edu.ntnu.idatt2105.calculator.model.User;
import java.util.Arrays;
import edu.ntnu.idatt2105.calculator.dto.EquationDTO;
import edu.ntnu.idatt2105.calculator.dto.ExpressionDTO;
import edu.ntnu.idatt2105.calculator.model.Equation;

@Component
public class CalculationMapper {

    public Equation toEquation(ExpressionDTO expression, double result, User user) {
        String expressionString = String.join(" ", expression.elements());
        return Equation
                .builder()
                .expression(expressionString)
                .result(result)
                .user(user)
                .build();
    }

    public ExpressionDTO toExpression(Equation equation) {
        String equationString = equation.getExpression() + " " + equation.getResult();
        return new ExpressionDTO(Arrays.stream(equationString.split(" ")).toList());
    }

    public static EquationDTO toEquationDTO(Equation equation) {
        return EquationDTO
                .builder()
                .expression(equation.getExpression())
                .result(equation.getResult())
                .build();
    }

}
