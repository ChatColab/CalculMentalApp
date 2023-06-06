package com.example.calculmentalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calculmentalapp.db.entities.Score;

import java.util.List;

public class HighscoreAdapter extends ArrayAdapter<Score> {
    private final LayoutInflater inflater;

    public HighscoreAdapter(@NonNull Context context, @NonNull List<Score> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_highscore, parent, false);
        }

        Score score = getItem(position);
        TextView usernameView = convertView.findViewById(R.id.text_username);
        TextView scoreView = convertView.findViewById(R.id.text_score);

        usernameView.setText(score.getUserName());
        scoreView.setText(String.valueOf(score.getScore()));

        return convertView;
    }
}
