package com.example.david.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


public class ExplicitActivity extends Activity {

    private static final String EXTRA_TEXT = "mpip.finki.ukim.mk.lab01.text";

    private Calculator mCalculator;
    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
        mCalculator = new Calculator();
        mOperandOneEditText = findViewById(R.id.operand_one_edit_text);
        mOperandTwoEditText = findViewById(R.id.operand_two_edit_text);
    }


    public void onAdd(View view) {
        compute(Calculator.Operator.ADD);
    }


    public void onSub(View view) {
        compute(Calculator.Operator.SUB);
    }


    public void onDiv(View view) {
        try {
            compute(Calculator.Operator.DIV);
        } catch (IllegalArgumentException iae) {
            closeActivity(getString(R.string.computationError), RESULT_OK);
        }
    }


    public void onMul(View view) {
        compute(Calculator.Operator.MUL);
    }

    private void compute(Calculator.Operator operator) {
        double operandOne;
        double operandTwo;
        try {
            operandOne = getOperand(mOperandOneEditText);
            operandTwo = getOperand(mOperandTwoEditText);
        } catch (NumberFormatException nfe) {
            closeActivity(getString(R.string.computationError), RESULT_OK);
            return;
        }

        String result;
        switch (operator) {
            case ADD:
                result = String.valueOf(mCalculator.add(operandOne, operandTwo));
                break;
            case SUB:
                result = String.valueOf(mCalculator.sub(operandOne, operandTwo));
                break;
            case DIV:
                result = String.valueOf(mCalculator.div(operandOne, operandTwo));
                break;
            case MUL:
                result = String.valueOf(mCalculator.mul(operandOne, operandTwo));
                break;
            default:
                result = getString(R.string.computationError);
                break;
        }
        closeActivity(result, RESULT_OK);
    }


    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }


    private static String getOperandText(EditText operandEditText) {
        String operandText = operandEditText.getText().toString();
        if (TextUtils.isEmpty(operandText)) {
            throw new NumberFormatException("operand cannot be empty!");
        }
        return operandText;
    }

    private void closeActivity(String output, int result){
        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, output);
        setResult(result, data);
        finish();
    }

    public static String getWrittenText(Intent data){
        return data.getStringExtra(EXTRA_TEXT);
    }

    @VisibleForTesting
    static Intent createResultData(String number) {
        final Intent resultData = new Intent();
        resultData.putExtra(EXTRA_TEXT, number);
        return resultData;
    }
}

