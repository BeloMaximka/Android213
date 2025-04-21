package itstep.learning.andrioid_213;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import itstep.learning.andrioid_213.chat.ChatMessage;
import itstep.learning.andrioid_213.chat.ChatMessageAdapter;
import itstep.learning.andrioid_213.chat.ChatMessageHolder;
import itstep.learning.andrioid_213.chat.ChatServices;

public class ChatActivity extends AppCompatActivity {
    private final String chatUrl = "https://chat.momentfor.fun/";
    private final List<ChatMessage> chatMessages = new ArrayList<>();
    ChatMessageAdapter chatMessageAdapter;
    private EditText etAuthor;
    private EditText etMessage;
    private View ivBell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        RecyclerView rvContainer = findViewById(R.id.chat_rv_container);
        rvContainer.setLayoutManager(new LinearLayoutManager(this));
        chatMessageAdapter = new ChatMessageAdapter(chatMessages);
        rvContainer.setAdapter(chatMessageAdapter);
        findViewById(R.id.chat_btn_send).setOnClickListener(this::sendMessage);
        etAuthor = findViewById(R.id.chat_et_author);
        etMessage = findViewById(R.id.chat_et_message);
        ivBell = findViewById(R.id.chat_iv_bell);
        new Thread(this::loadChat).start();

    }

    private void loadChat() {
        try {
            String text = ChatServices.fetchUrlText(chatUrl);
            JSONObject jsonObject = new JSONObject(text);
            JSONArray arr = jsonObject.getJSONArray("data");
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                chatMessages.add(ChatMessage.fromJsonObject(arr.getJSONObject(i)));
            }
            runOnUiThread(() -> chatMessageAdapter.notifyItemRangeInserted(0, len));
        } catch (JSONException | IOException e) {
            Log.d("loadChat", e.getCause() + e.getMessage());
        }
    }

    private void sendMessage(View view) {
        String author = etAuthor.getText().toString();
        if (author.isBlank()) {
            Toast.makeText(this, R.string.chat_msg_empty_auth, Toast.LENGTH_SHORT).show();
            return;
        }
        String message = etMessage.getText().toString();
        if (message.isBlank()) {
            Toast.makeText(this, R.string.chat_msg_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public static boolean postForm(String url, Map<String, String> data) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String charsetName = StandardCharsets.UTF_8.name();
            StringBuilder stringBuilder = new StringBuilder();
            boolean isFirst = true;
            for (Map.Entry<String, String> entry : data.entrySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append('&');
                }
                stringBuilder.append(String.format(Locale.ROOT,

                        "%s=%s",

                        entry.getKey(),

                        URLEncoder.encode(entry.getValue(), charsetName)
                ));
            }
            String body = stringBuilder.toString();
            OutputStream bodyStream = connection.getOutputStream();
            bodyStream.write(body.getBytes(charsetName));
            bodyStream.flush();
            bodyStream.close();
            int statusCode = connection.getResponseCode();
            if (statusCode < 300) {
                return true;
            } else {
                Log.d("postForm",

                        ChatServices.readAllText(connection.getErrorStream())
                );
            }
            connection.disconnect();
        } catch (Exception ex) {
            Log.d("postForm", ex.getCause() + " " + ex.getMessage());
        }
        return false;
    }
}