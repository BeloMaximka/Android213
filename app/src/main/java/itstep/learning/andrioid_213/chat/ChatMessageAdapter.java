package itstep.learning.andrioid_213.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import itstep.learning.andrioid_213.R;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageHolder> {
    private final List<ChatMessage> chatMessages;

    public ChatMessageAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int view) {
        View chatMessageLayourView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_message_layout, parent, false);

        return new ChatMessageHolder(chatMessageLayourView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        holder.getTvAuthor().setText(chatMessage.getAuthor());
        holder.getTvText().setText(chatMessage.getText());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }
}
