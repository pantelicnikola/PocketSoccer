package com.etf.nikolapantelic.pocketsoccer.scores.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etf.nikolapantelic.pocketsoccer.R;
import com.etf.nikolapantelic.pocketsoccer.common.RecyclerViewClickListener;
import com.etf.nikolapantelic.pocketsoccer.common.db.model.ResultModel;
import com.etf.nikolapantelic.pocketsoccer.scores.MutualScoresActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.etf.nikolapantelic.pocketsoccer.common.db.ResultsContract.ResultsEntry;

public class AllScoresAdapter extends RecyclerView.Adapter<AllScoresViewHolder> {

    private List<ResultModel> allScores;
    private Context context;

    public AllScoresAdapter(Context context, @NotNull List<ResultModel> allScores) {
        this.allScores = allScores;
        this.context = context;
    }

    @NonNull
    @Override
    public AllScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.scores_row, viewGroup, false);
        return new AllScoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllScoresViewHolder allScoresViewHolder, int i) {
        allScoresViewHolder.getTextViewScore().setText(allScores.get(i).toString());
        allScoresViewHolder.setRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, MutualScoresActivity.class);
                intent.putExtra("playersId", allScores.get(position).getPlayersId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allScores.size();
    }
}
