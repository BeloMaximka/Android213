package itstep.learning.andrioid_213.rate;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import itstep.learning.andrioid_213.R;

public class RateHolder extends RecyclerView.ViewHolder {
    private final TextView tvRateCc;
    private final TextView tvRateValue;

    public RateHolder(@NonNull View itemView) {
        super(itemView);
        tvRateCc = itemView.findViewById(R.id.nbu_rate_name);
        tvRateValue = itemView.findViewById(R.id.nbu_rate_value);
    }

    public TextView getTvCc() {
        return tvRateCc;
    }

    public TextView getTvValue() {
        return tvRateValue;
    }
}