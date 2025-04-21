package itstep.learning.andrioid_213.chat;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itstep.learning.andrioid_213.R;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {
    private static final SimpleDateFormat momentFormat =
            new SimpleDateFormat("dd.MM HH:mm", Locale.ROOT);
    private static final SimpleDateFormat timeFormat =
            new SimpleDateFormat("HH:mm", Locale.ROOT);

    private final TextView tvAuthor;
    private final TextView tvText;
    private final TextView tvMoment;
    private final LinearLayout msgContainer;

    public ChatMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAuthor = itemView.findViewById(R.id.chat_msg_author);
        tvText   = itemView.findViewById(R.id.chat_msg_text);
        tvMoment = itemView.findViewById(R.id.chat_msg_moment);
        msgContainer = itemView.findViewById(R.id.chat_msg_layout);
    }

    public void setChatMessage(ChatMessage chatMessage, String author) {
        tvAuthor.setText(chatMessage.getAuthor());
        tvText.setText(chatMessage.getText());
        if(chatMessage.getAuthor().equals(author)) {
            msgContainer.setGravity(Gravity.END);
        }

        Date messageDate = chatMessage.getMoment();

        Calendar msgCal = Calendar.getInstance();
        msgCal.setTime(messageDate);
        Calendar todayCal = Calendar.getInstance();

        Calendar msgDateOnly = (Calendar) msgCal.clone();
        msgDateOnly.set(Calendar.HOUR_OF_DAY, 0);
        msgDateOnly.set(Calendar.MINUTE,      0);
        msgDateOnly.set(Calendar.SECOND,      0);
        msgDateOnly.set(Calendar.MILLISECOND, 0);

        Calendar todayDateOnly = (Calendar) todayCal.clone();
        todayDateOnly.set(Calendar.HOUR_OF_DAY, 0);
        todayDateOnly.set(Calendar.MINUTE,      0);
        todayDateOnly.set(Calendar.SECOND,      0);
        todayDateOnly.set(Calendar.MILLISECOND, 0);

        long diffMillis = todayDateOnly.getTimeInMillis() - msgDateOnly.getTimeInMillis();
        long daysDiff = diffMillis / (24L * 60L * 60L * 1000L);

        String display;
        if (daysDiff == 0) {
            display = timeFormat.format(messageDate);

        } else if (daysDiff == 1) {
            display = "вчора, " + timeFormat.format(messageDate);

        } else if (daysDiff > 1 && daysDiff < 7) {
            display = getDaysAgoString((int) daysDiff) + ", " + timeFormat.format(messageDate);

        } else {
            display = momentFormat.format(messageDate);
        }

        tvMoment.setText(display);
    }

    private String getDaysAgoString(int days) {
        String unit;
        int mod10  = days % 10;
        int mod100 = days % 100;

        if (mod10 == 1 && mod100 != 11) {
            unit = "день";
        } else if (mod10 >= 2 && mod10 <= 4 && !(mod100 >= 12 && mod100 <= 14)) {
            unit = "дні";
        } else {
            unit = "днів";
        }
        return days + " " + unit + " тому";
    }
}
