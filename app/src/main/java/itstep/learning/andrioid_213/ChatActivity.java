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
import java.util.ArrayList;
import java.util.List;

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
            for (int i = 0; i < len; i ++) {
                chatMessages.add(ChatMessage.fromJsonObject(arr.getJSONObject(i)));
            }
            runOnUiThread(() -> chatMessageAdapter.notifyItemRangeInserted(0, len));
        } catch (JSONException | IOException e) {
            Log.d("loadChat", e.getCause() + e.getMessage());
        }
    }

    private void sendMessage(View view) {
        String author = etAuthor.getText().toString();
        if(author.isBlank()) {
            Toast.makeText(this, R.string.chat_msg_empty_auth, Toast.LENGTH_SHORT).show();
            return;
        }
        String message = etMessage.getText().toString();
        if(message.isBlank()) {
            Toast.makeText(this, R.string.chat_msg_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }
    }

}