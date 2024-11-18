package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calculator.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private StringBuilder expression = new StringBuilder();
    private String operator = "";
    private int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setupListeners();
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void setupListeners() {
        binding.button0.setOnClickListener(v -> appendToExpression("0"));
        binding.button1.setOnClickListener(v -> appendToExpression("1"));
        binding.button2.setOnClickListener(v -> appendToExpression("2"));
        binding.button3.setOnClickListener(v -> appendToExpression("3"));
        binding.button4.setOnClickListener(v -> appendToExpression("4"));
        binding.button5.setOnClickListener(v -> appendToExpression("5"));
        binding.button6.setOnClickListener(v -> appendToExpression("6"));
        binding.button7.setOnClickListener(v -> appendToExpression("7"));
        binding.button8.setOnClickListener(v -> appendToExpression("8"));
        binding.button9.setOnClickListener(v -> appendToExpression("9"));

        binding.buttonAdd.setOnClickListener(v -> appendOperator("+"));
        binding.buttonSubtract.setOnClickListener(v -> appendOperator("-"));
        binding.buttonMultiply.setOnClickListener(v -> appendOperator("*"));
        binding.buttonDivide.setOnClickListener(v -> appendOperator("/"));
        binding.buttonPercent.setOnClickListener(v -> appendOperator("%"));

        binding.buttonEquals.setOnClickListener(v -> calculateResult());
        binding.buttonClear.setOnClickListener(v -> clearExpression());
    }

    private void appendToExpression(String value) {
        expression.append(value);
        binding.displayCalculation.setText(expression.toString());
    }

    private void appendOperator(String selectedOperator) {
        if (expression.length() > 0 && !isLastCharacterOperator()) {
            expression.append(" ").append(selectedOperator).append(" ");
            binding.displayCalculation.setText(expression.toString());
        }
    }

    private boolean isLastCharacterOperator() {
        char lastChar = expression.charAt(expression.length() - 1);
        return lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '%';
    }

    private void calculateResult() {
        try {
            String[] tokens = expression.toString().split(" ");
            result = Integer.parseInt(tokens[0]);

            for (int i = 1; i < tokens.length; i += 2) {
                String op = tokens[i];
                int operand = Integer.parseInt(tokens[i + 1]);

                switch (op) {
                    case "+":
                        result += operand;
                        break;
                    case "-":
                        result -= operand;
                        break;
                    case "*":
                        result *= operand;
                        break;
                    case "/":
                        if (operand != 0) {
                            result /= operand;
                        } else {
                            binding.displayResult.setText("Error");
                            return;
                        }
                        break;
                    case "%":
                        result %= operand;
                        break;
                }
            }

            binding.displayResult.setText(String.valueOf(result));
        } catch (Exception e) {
            binding.displayResult.setText("Error");
        }
    }

    private void clearExpression() {
        expression.setLength(0);
        binding.displayCalculation.setText("");
        binding.displayResult.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Clear binding when activity is destroyed
    }
}
