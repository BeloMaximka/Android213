package itstep.learning.andrioid_213;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CalcActivity extends AppCompatActivity {

    private TextView tvExpression, tvResult;
    private StringBuilder expression = new StringBuilder();
    private boolean isResultCalculated = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EXPRESSION_KEY", tvExpression.getText().toString());
        outState.putString("RESULT_KEY", tvResult.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        tvExpression = findViewById(R.id.calc_tv_expression);
        tvResult = findViewById(R.id.calc_tv_result);

        findViewById(R.id.calc_btn_0).setOnClickListener(v -> appendDigit("0"));
        findViewById(R.id.calc_btn_1).setOnClickListener(v -> appendDigit("1"));
        findViewById(R.id.calc_btn_2).setOnClickListener(v -> appendDigit("2"));
        findViewById(R.id.calc_btn_3).setOnClickListener(v -> appendDigit("3"));
        findViewById(R.id.calc_btn_4).setOnClickListener(v -> appendDigit("4"));
        findViewById(R.id.calc_btn_5).setOnClickListener(v -> appendDigit("5"));
        findViewById(R.id.calc_btn_6).setOnClickListener(v -> appendDigit("6"));
        findViewById(R.id.calc_btn_7).setOnClickListener(v -> appendDigit("7"));
        findViewById(R.id.calc_btn_8).setOnClickListener(v -> appendDigit("8"));
        findViewById(R.id.calc_btn_9).setOnClickListener(v -> appendDigit("9"));

        findViewById(R.id.calc_btn_add).setOnClickListener(v -> appendOperator("+"));
        findViewById(R.id.calc_btn_sub).setOnClickListener(v -> appendOperator("-"));
        findViewById(R.id.calc_btn_mult).setOnClickListener(v -> appendOperator("*"));
        findViewById(R.id.calc_btn_div).setOnClickListener(v -> appendOperator("/"));

        findViewById(R.id.calc_btn_dot).setOnClickListener(v -> appendDot());

        findViewById(R.id.calc_btn_equal).setOnClickListener(v -> calculateResult());

        findViewById(R.id.calc_btn_c).setOnClickListener(v -> clearAll());
        findViewById(R.id.calc_btn_ce).setOnClickListener(v -> clearEntry());
        findViewById(R.id.calc_btn_backspace).setOnClickListener(v -> backspace());

        findViewById(R.id.calc_btn_pm).setOnClickListener(v -> toggleSign());

        findViewById(R.id.calc_btn_percent).setOnClickListener(v -> applyPercent());
        findViewById(R.id.calc_btn_inv).setOnClickListener(v -> applyInverse());
        findViewById(R.id.calc_btn_sqr).setOnClickListener(v -> applySquare());
        findViewById(R.id.calc_btn_sqrt).setOnClickListener(v -> applySqrt());

        if (savedInstanceState != null) {
            String savedExpression = savedInstanceState.getString("EXPRESSION_KEY");
            expression.append(savedExpression);
            tvExpression.setText(savedInstanceState.getString("EXPRESSION_KEY"));
            tvResult.setText(savedInstanceState.getString("RESULT_KEY"));
        }
    }

    private void appendDigit(String digit) {
        if (isResultCalculated) {
            expression.setLength(0);
            isResultCalculated = false;
            tvResult.setText("");
        }
        expression.append(digit);
        tvExpression.setText(expression.toString());
    }

    private void appendOperator(String operator) {
        if (expression.length() == 0 && tvResult.getText().length() > 0) {
            expression.append(tvResult.getText().toString());
        }
        expression.append(" " + operator + " ");
        tvExpression.setText(expression.toString());
        isResultCalculated = false;
    }

    private void appendDot() {
        if (isResultCalculated) {
            expression.setLength(0);
            isResultCalculated = false;
            tvResult.setText("");
        }
        expression.append(".");
        tvExpression.setText(expression.toString());
    }

    private void clearAll() {
        expression.setLength(0);
        tvExpression.setText("");
        tvResult.setText("");
    }

    private void clearEntry() {
        expression.setLength(0);
        tvExpression.setText("");
    }

    private void backspace() {
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            tvExpression.setText(expression.toString());
        }
    }

    private void toggleSign() {
        String exp = expression.toString();
        if (exp.isEmpty()) return;

        int lastSpace = exp.lastIndexOf(" ");
        if (lastSpace == -1) {
            try {
                double value = Double.parseDouble(exp);
                value = -value;
                expression = new StringBuilder(String.valueOf(value));
            } catch (NumberFormatException ignored) {
            }
        } else {
            String lastNumber = exp.substring(lastSpace + 1);
            try {
                double value = Double.parseDouble(lastNumber);
                value = -value;
                expression.replace(lastSpace + 1, exp.length(), String.valueOf(value));
            } catch (NumberFormatException ignored) {
            }
        }
        tvExpression.setText(expression.toString());
    }

    private void applyPercent() {
        String exp = expression.toString();
        if (exp.isEmpty()) return;

        int lastSpace = exp.lastIndexOf(" ");
        String lastNumber = (lastSpace == -1) ? exp : exp.substring(lastSpace + 1);
        try {
            double value = Double.parseDouble(lastNumber);
            value = value / 100;
            if (lastSpace == -1) {
                expression = new StringBuilder(String.valueOf(value));
            } else {
                expression.replace(lastSpace + 1, exp.length(), String.valueOf(value));
            }
            tvExpression.setText(expression.toString());
        } catch (NumberFormatException ignored) {
        }
    }

    private void applyInverse() {
        String exp = expression.toString();
        if (exp.isEmpty()) return;

        int lastSpace = exp.lastIndexOf(" ");
        String lastNumber = (lastSpace == -1) ? exp : exp.substring(lastSpace + 1);
        try {
            double value = Double.parseDouble(lastNumber);
            if (value != 0) {
                value = 1 / value;
                if (lastSpace == -1) {
                    expression = new StringBuilder(String.valueOf(value));
                } else {
                    expression.replace(lastSpace + 1, exp.length(), String.valueOf(value));
                }
                tvExpression.setText(expression.toString());
            }
        } catch (NumberFormatException ignored) {
        }
    }

    private void applySquare() {
        String exp = expression.toString();
        if (exp.isEmpty()) return;

        int lastSpace = exp.lastIndexOf(" ");
        String lastNumber = (lastSpace == -1) ? exp : exp.substring(lastSpace + 1);
        try {
            double value = Double.parseDouble(lastNumber);
            value = value * value;
            if (lastSpace == -1) {
                expression = new StringBuilder(String.valueOf(value));
            } else {
                expression.replace(lastSpace + 1, exp.length(), String.valueOf(value));
            }
            tvExpression.setText(expression.toString());
        } catch (NumberFormatException ignored) {
        }
    }

    private void applySqrt() {
        String exp = expression.toString();
        if (exp.isEmpty()) return;

        int lastSpace = exp.lastIndexOf(" ");
        String lastNumber = (lastSpace == -1) ? exp : exp.substring(lastSpace + 1);
        try {
            double value = Double.parseDouble(lastNumber);
            if (value >= 0) {
                value = Math.sqrt(value);
                if (lastSpace == -1) {
                    expression = new StringBuilder(String.valueOf(value));
                } else {
                    expression.replace(lastSpace + 1, exp.length(), String.valueOf(value));
                }
                tvExpression.setText(expression.toString());
            }
        } catch (NumberFormatException ignored) {
        }
    }

    private void calculateResult() {
        try {
            double resultValue = evaluate(expression.toString());
            tvResult.setText(String.valueOf(resultValue));
            isResultCalculated = true;
        } catch (Exception e) {
            tvResult.setText(R.string.calc_error);
        }
    }

    private double evaluate(String exp) {
        exp = exp.trim();
        if (exp.isEmpty()) return 0;

        String[] tokens = exp.split(" ");

        List<String> newTokens = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("*") || token.equals("/")) {
                double prevVal = Double.parseDouble(newTokens.remove(newTokens.size() - 1));
                double nextVal = Double.parseDouble(tokens[++i]);
                double intermediate = token.equals("*") ? prevVal * nextVal : prevVal / nextVal;
                newTokens.add(String.valueOf(intermediate));
            } else {
                newTokens.add(token);
            }
        }

        double result = Double.parseDouble(newTokens.get(0));
        for (int i = 1; i < newTokens.size(); i += 2) {
            String operator = newTokens.get(i);
            double operand = Double.parseDouble(newTokens.get(i + 1));
            if (operator.equals("+")) {
                result += operand;
            } else if (operator.equals("-")) {
                result -= operand;
            }
        }
        return result;
    }
}
