package itstep.learning.andrioid_213;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import itstep.learning.andrioid_213.rate.NbuRate;

public class RateActivity extends AppCompatActivity {
    private final String nbuUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private TextView tvTmp;
    private LinearLayout ratesContainer;
    private List<NbuRate> nbuRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTmp = findViewById(R.id.temp);
        ratesContainer = findViewById(R.id.rates_container);
        new Thread(this::loadRates).start();
    }

    private void loadRates() {
        try  {
            String jsonText = fetchUrlText(nbuUrl);
            JSONArray arr = new JSONArray(jsonText);
            int len = arr.length();
            nbuRates = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                nbuRates.add(NbuRate.fromJsonObject((arr.getJSONObject(i))));
            }
            runOnUiThread(this::showRates);
        }
        catch (RuntimeException | JSONException e) {
            Log.e("loadRates", "loadRates: " + e.getMessage());
            runOnUiThread(() -> tvTmp.setText(R.string.rate_tv_load_fail));
        }
    }

    private void showRates() {
        for (NbuRate rate: nbuRates) {
            TextView tv = new TextView(this);
            tv.setText(rate.getText());
            tv.setOnClickListener(v -> openRateInfoDialog(rate));
            ratesContainer.addView(tv);
        }
    }

    private void openRateInfoDialog(NbuRate rate) {
        new AlertDialog
                .Builder(this, android.R.style.Theme_Material_Dialog)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(rate.getText())
                .setMessage(getString(R.string.rate_detailed_info, rate.getText(), rate.getCc(), rate.getR030(), rate.getExchangeDate(), rate.getRate()))
                .setCancelable(true)
                .show();
    }

    private String fetchUrlText(String urlPath) {
        URL url;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }

        try (InputStream urlStream = url.openStream()) {
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
            int receivedBytes;
            while ((receivedBytes = urlStream.read(buffer)) > 0) {
                byteBuilder.write(buffer, 0 ,receivedBytes);
            }
            return byteBuilder.toString();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }
}