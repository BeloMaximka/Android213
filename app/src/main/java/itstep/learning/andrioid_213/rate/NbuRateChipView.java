package itstep.learning.andrioid_213.rate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import itstep.learning.andrioid_213.R;

public class NbuRateChipView extends androidx.appcompat.widget.AppCompatTextView {
    public NbuRateChipView(Context context, NbuRate nbuRate) {
        super(context);
        setPadding(24, 24, 24, 24);
        setText(nbuRate.getText());

        Drawable rateBg = AppCompatResources.getDrawable(context.getApplicationContext(),  R.drawable.rate_shape);
        setBackground(rateBg);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 16,16,16);
        setLayoutParams(layoutParams);;

    }
}
