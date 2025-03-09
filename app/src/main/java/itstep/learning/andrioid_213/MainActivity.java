package itstep.learning.andrioid_213;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
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
        findViewById(R.id.buttonGame).setOnClickListener(this::OnButtonGameClick);
        findViewById(R.id.buttonCalc).setOnClickListener(this::OnButtonCalcClick);
    }

    private void OnButtonCalcClick(View view) {
        startActivity(new Intent(MainActivity.this, CalcActivity.class));
    }

    private void OnButtonGameClick(View view) {
        startActivity(new Intent(MainActivity.this, GameActivity.class));
    }
}