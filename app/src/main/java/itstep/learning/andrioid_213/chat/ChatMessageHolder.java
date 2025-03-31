package itstep.learning.andrioid_213.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import itstep.learning.andrioid_213.R;

public class ChatMessageHolder extends RecyclerView.ViewHolder {
    private final TextView tvAuthor;
    private final TextView tvText;

    public ChatMessageHolder(@NonNull View itemView) {
        super(itemView);
        tvAuthor = itemView.findViewById(R.id.chat_msg_author);
        tvText = itemView.findViewById(R.id.chat_msg_text);
    }

    public TextView getTvAuthor() {
        return tvAuthor;
    }

    public TextView getTvText() {
        return tvText;
    }
}
