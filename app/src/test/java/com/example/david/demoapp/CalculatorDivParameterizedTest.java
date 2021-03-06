package com.example.david.demoapp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import android.test.suitebuilder.annotation.SmallTest;

import java.lang.Iterable;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;


/**
 * JUnit4 tests for the calculator's add logic.
 *
 * <p> This test uses a Junit4s Parameterized tests features which uses annotations to pass
 * parameters into a unit test. The way this works is that you have to use the {@link Parameterized}
 * runner to run your tests.
 * </p>
 */
@RunWith(Parameterized.class)
@SmallTest
public class CalculatorDivParameterizedTest {

    /**
     * @return {@link Iterable} that contains the values that should be passed to the constructor.
     * In this example we are going to use three parameters: operand one, operand two and the
     * expected result.
     */
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {2, 2, 1},
                {2, -2, -1},
                {-2, 2, -1},
                {4, 2, 2},
                {8, 2, 4},
                {16, 2, 8},
                {128, 4, 32}});
    }

    private final double mOperandOne;
    private final double mOperandTwo;
    private final double mExpectedResult;

    private Calculator mCalculator;

    /**
     * Constructor that takes in the values specified in
     * {@link CalculatorAddParameterizedTest#data()}. The values need to be saved to fields in order
     * to reuse them in your tests.
     */
    public CalculatorDivParameterizedTest(double operandOne, double operandTwo,
                                          double expectedResult) {
        mOperandOne = operandOne;
        mOperandTwo = operandTwo;
        mExpectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    public void testDiv_TwoNumbers() {
        double resultDiv = mCalculator.div(mOperandOne, mOperandTwo);
        assertThat(resultDiv, is(equalTo(mExpectedResult)));
    }

}