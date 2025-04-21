package itstep.learning.andrioid_213.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import itstep.learning.andrioid_213.R;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder> {
    private final List<ChatMessage> messages;
    private final String author;

    public ChatMessageAdapter(List<ChatMessage> messages, String author) {
        this.messages = messages;
        this.author = author;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.chat_message_layout, parent, false );

        return new ChatMessageViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        holder.setChatMessage( messages.get( position ), author );
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
