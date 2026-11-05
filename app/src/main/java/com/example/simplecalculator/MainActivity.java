package com.example.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {
    private EditText firstNumber, secondNumber;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        resultText = findViewById(R.id.resultText);

        findViewById(R.id.addButton).setOnClickListener(v -> calculate('+'));
        findViewById(R.id.subtractButton).setOnClickListener(v -> calculate('-'));
        findViewById(R.id.multiplyButton).setOnClickListener(v -> calculate('*'));
        findViewById(R.id.divideButton).setOnClickListener(v -> calculate('/'));
        findViewById(R.id.clearButton).setOnClickListener(v -> clearFields());
    }

    private void calculate(char operation) {
        String first = firstNumber.getText().toString().trim();
        String second = secondNumber.getText().toString().trim();

        if (first.isEmpty() || second.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            resultText.setText("Result: —");
            return;
        }

        try {
            double a = Double.parseDouble(first);
            double b = Double.parseDouble(second);
            double result;

            switch (operation) {
                case '+': result = a + b; break;
                case '-': result = a - b; break;
                case '*': result = a * b; break;
                case '/':
                    if (b == 0.0) {
                        resultText.setText("Cannot divide by zero");
                        Toast.makeText(this, "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = a / b;
                    break;
                default: return;
            }

            if (Double.isNaN(result) || Double.isInfinite(result)) {
                resultText.setText("Invalid result");
            } else {
                resultText.setText("Result: " + formatResult(result));
            }
        } catch (NumberFormatException e) {
            resultText.setText("Invalid input");
            Toast.makeText(this, "Enter valid numeric values", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatResult(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1.0E15) {
            return String.format(Locale.getDefault(), "%.0f", value);
        }
        return String.valueOf(value);
    }

    private void clearFields() {
        firstNumber.setText("");
        secondNumber.setText("");
        resultText.setText("Result: —");
        firstNumber.requestFocus();
    }
}
