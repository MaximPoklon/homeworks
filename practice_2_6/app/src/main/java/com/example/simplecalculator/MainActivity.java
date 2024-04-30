package com.example.simplecalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText number1, number2, number3;
    Button calculate;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Activity создана");

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        calculate = findViewById(R.id.calculate);
        result = findViewById(R.id.result);

        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Вычисление начато");
                if (validateInput()) {
                    BigInteger num1 = new BigInteger(number1.getText().toString().replaceFirst("^0+(?!$)", ""));
                    BigInteger num2 = new BigInteger(number2.getText().toString().replaceFirst("^0+(?!$)", ""));
                    BigInteger num3 = new BigInteger(number3.getText().toString().replaceFirst("^0+(?!$)", ""));
                    BigInteger substractionResult = num1.subtract(num2).subtract(num3);
                    Log.d(TAG, "Вычисление выполнено: " + substractionResult);
                    result.setText("Результат: " + substractionResult);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity стартовала");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity возобновлена");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity уничтожена");
    }

    private boolean validateInput() {
        // Check for empty input fields
        if (number1.getText().toString().isEmpty() ||
                number2.getText().toString().isEmpty() ||
                number3.getText().toString().isEmpty()) {
            Log.d(TAG, "Ошибка: не заполнены некоторые поля. Вычисление невозможно");
            result.setText("Ошибка: Все поля должны быть заполнены числами.");
            return false;
        }

        // Check for numbers starting with zero followed by other digits
        if (number1.getText().toString().matches("^0[0-9]+") ||
                number2.getText().toString().matches("^0[0-9]+") ||
                number3.getText().toString().matches("^0[0-9]+")) {
            Log.d(TAG, "Ошибка: Недопустимый ввод чисел, начинающихся с нуля.");
            result.setText("Ошибка: Числа не должны начинаться с нуля, за исключением самого числа '0'.");
            return false;
        }

        // Try to parse each input as a BigInteger
        try {
            new BigInteger(number1.getText().toString().replaceFirst("^0+(?!$)", ""));
            new BigInteger(number2.getText().toString().replaceFirst("^0+(?!$)", ""));
            new BigInteger(number3.getText().toString().replaceFirst("^0+(?!$)", ""));
        } catch (NumberFormatException e) {
            Log.d(TAG, "Ошибка: Некоторые поля не соответствуют типу чисел большой точности");
            result.setText("Ошибка: Все поля должны содержать только числовые значения.");
            return false;
        }
        return true;
    }
}