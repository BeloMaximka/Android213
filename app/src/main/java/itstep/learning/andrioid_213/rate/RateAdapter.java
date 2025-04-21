package itstep.learning.andrioid_213.rate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import itstep.learning.andrioid_213.R;

public class RateAdapter extends RecyclerView.Adapter<RateHolder> {
    private final List<NbuRate> nbuRates;

    public RateAdapter(List<NbuRate> nbuRates) {
        this.nbuRates = nbuRates;
    }

    @NonNull
    @Override
    public RateHolder onCreateViewHolder(@NonNull ViewGroup parent, int view) {
        View chatMessageLayourView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.nbu_rate_layout, parent, false);

        return new RateHolder(chatMessageLayourView);
    }

    @Override
    public void onBindViewHolder(@NonNull RateHolder holder, int position) {
        NbuRate nbuRate = nbuRates.get(position);
        holder.getTvCc().setText(nbuRate.getCc());
        holder.getTvValue().setText(nbuRate.getRate() + "");

    }

    @Override
    public int getItemCount() {
        return nbuRates.size();
    }
}