import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class ExpressionsTest {

    private static List<String> data;

    private boolean isNumeric(String num) {
        return num.matches("\\d+|-\\d+");
    }

    private boolean isOperator(String operator) {
        return operator.equals("+") | operator.equals("-") | operator.equals("*") | operator.equals("/");
    }

    private String getResult(String operand1, String operand2, String operator) {
        BigInteger bi1 = new BigInteger(operand1);
        BigInteger bi2 = new BigInteger(operand2);

        switch (operator) {
            case "+":
                return bi1.add(bi2).toString();
            case "-":
                return bi1.add(bi2.negate()).toString();

            case "*":
                return bi1.multiply(bi2).toString();
            case "/":
                return bi1.divide(bi2).toString();
        }
        return "";
    }

    @BeforeClass
    public static void readFile() {
        try {
            data = Files.readAllLines(Paths.get("src/main/resources/data.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void operand1Validate() {
        for (String aData : data) {
            String[] expression = aData.split(";");
            checkOperand(expression[0]);
        }
    }

    @Test
    public void operand2Validate() {
        for (String aData : data) {
            String[] expression = aData.split(";");
            checkOperand(expression[1]);
        }
    }

    @Test
    public void resultValidate() {
        for (String aData : data) {
            String[] expression = aData.split(";");
            checkOperand(expression[3]);
        }
    }

    @Test
    public void operatorValidate() {
        for (String aData : data) {
            String[] expression = aData.split(";");
            checkOperator(expression[2]);
        }
    }

    @Test
    public void result() {
        for (String aDate : data) {
            String[] expression = aDate.split(";");
            checkResult(expression[0], expression[1], expression[2], expression[3]);
        }
    }

    @Test
    public void notZeroSecondOperatorWhenDivision() {
        for (String aDate : data) {
            String[] expression = aDate.split(";");
            checkDivision(expression[1], expression[2]);
        }
    }

    @Step("operand2 {0}, operator {1}")
    public void checkDivision(String operand, String operator) {
        if (operator.equals("/"))
            Assert.assertFalse(operand.equals("0"));
    }

    @Step("operand1 {0}; operand2 {1}; operator {2}; result {3}")
    public void checkResult(String operand1, String operand2, String operator, String result) {
        Assert.assertEquals(result, getResult(operand1, operand2, operator));
    }

    @Step("operand {0}")
    public void checkOperand(String num) {
        Assert.assertTrue(isNumeric(num));
    }

    @Step("operator {0}")
    public void checkOperator(String operator) {
        Assert.assertTrue(isOperator(operator));
    }
}

