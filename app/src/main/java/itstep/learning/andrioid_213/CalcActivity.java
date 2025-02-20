package itstep.learning.andrioid_213;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.calc_btn_0).setOnClickListener(this::onDigitClick);
    }

    private void onDigitClick(View view) {

    }
}
