package itstep.learning.andrioid_213;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private Integer currentNumber = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //    return insets;
        //});
        tv1 = findViewById(R.id.text_view1);
        tv1.setText(currentNumber.toString());
        findViewById(R.id.buttonPlus).setOnClickListener(this::OnPlusButtonClick);
        findViewById(R.id.buttonMinus).setOnClickListener(this::OnMinusButtonClick);
    }

    private void OnPlusButtonClick(View view) {
        currentNumber += 1;
        tv1.setText(currentNumber.toString());
    }

    private void OnMinusButtonClick(View view) {
        currentNumber -= 1;
        tv1.setText(currentNumber.toString());
    }
}