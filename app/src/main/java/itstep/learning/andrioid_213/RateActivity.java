package itstep.learning.andrioid_213;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import itstep.learning.andrioid_213.rate.NbuRate;
import itstep.learning.andrioid_213.rate.NbuRateChipView;

public class RateActivity extends AppCompatActivity {
    private final String nbuUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final String cacheFilename = "nbu_rates_cache.json";
    private static List<NbuRate> cachedNbuRates;
    private TextView tvTmp;

    private EditText etSearch;
    private LinearLayout ratesContainer;
    private List<NbuRate> nbuRates;

    private Handler handler = new Handler();

    private String searchString = "";

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
        etSearch = findViewById(R.id.rate_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchString = charSequence.toString().toLowerCase();
                showRates();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ratesContainer = findViewById(R.id.rates_container);
        if(cachedNbuRates == null) {
            try(FileInputStream fis = openFileInput(cacheFilename)) {
                Log.d("onCreate","Nbu got cache from file.");
                String json = readAllText(fis);
                loadRates(json);
            }
            catch(IOException ignored) {
                Log.d("onCreate","Nbu not cached.");
                new Thread(this::loadRatesFromUrl).start();
            }
        }
        else {
            Log.d("onCreate", "Getting nbu from cache.");
            nbuRates = cachedNbuRates;
        }

        if(nbuRates != null && isRatesActual()) {
            showRates();
        }
        else {
            Log.d("onCreate", "Cache is stale.");
            new Thread(this::loadRatesFromUrl).start();
        }

        handler.postDelayed(this::periodicAction, 5000);
    }

    private void periodicAction() {
        if(nbuRates != null && isRatesActual()) {
            showRates();
        }
        else {
            Log.d("periodicAction", "Cache is stale.");
            new Thread(this::loadRatesFromUrl).start();
        }
        handler.postDelayed(this::periodicAction, 5000);
    }
    private void loadRatesFromUrl() {
        try  {
            String jsonText = fetchUrlText(nbuUrl);
            try(FileOutputStream fos = openFileOutput(cacheFilename, Context.MODE_PRIVATE)) {
                fos.write(jsonText.getBytes(StandardCharsets.UTF_8));
                Log.d("loadRatesFromUrl", "File cache saved");
            }
            catch (IOException ex) {
                Log.d("loadRatesFromUrl", ex.getCause() + ex.getMessage());
            }
            loadRates(jsonText);
            runOnUiThread(this::showRates);
        }
        catch (RuntimeException e) {
            Log.e("loadRates", "loadRates: " + e.getMessage());
            runOnUiThread(() -> tvTmp.setText(R.string.rate_tv_load_fail));
        }
    }

    private void loadRates(String jsonText) {
        try  {
            JSONArray arr = new JSONArray(jsonText);
            int len = arr.length();
            nbuRates = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                nbuRates.add(NbuRate.fromJsonObject((arr.getJSONObject(i))));
            }
            cachedNbuRates = nbuRates;
        }
        catch (RuntimeException | JSONException e) {
            Log.e("loadRates", "loadRates: " + e.getMessage());
            runOnUiThread(() -> tvTmp.setText(R.string.rate_tv_load_fail));
        }
    }

    private void showRates() {
        ratesContainer.removeAllViews();
        for (NbuRate rate: nbuRates) {
            if(!rate.search(searchString)) {
                continue;
            }
            NbuRateChipView tv = new NbuRateChipView(this, rate);
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
            return readAllText(urlStream);
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private boolean isRatesActual() {
        try {
            return nbuRates.get(0).getExchangeDate().before(
                    NbuRate.dateFormat.parse(NbuRate.dateFormat.format(new Date()))
            );
        } catch (ParseException e) {
            Log.d("isRatesActual", e.getCause() + e.getMessage());
        }
        return false;
    }

    private String readAllText(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        int receivedBytes;
        while ((receivedBytes = inputStream.read(buffer)) > 0) {
            byteBuilder.write(buffer, 0 ,receivedBytes);
        }
        return byteBuilder.toString();
    }
}