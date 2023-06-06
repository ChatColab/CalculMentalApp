package com.example.calculmentalapp;// HighscoreFragment.java

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calculmentalapp.HighscoreAdapter;
import com.example.calculmentalapp.db.database.ScoreBaseHelper;
import com.example.calculmentalapp.db.database.ScoreDao;
import com.example.calculmentalapp.db.entities.Score;

import java.util.List;

public class HighscoreFragment extends Fragment {
    private ScoreDao scoreDao;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);
        listView = view.findViewById(R.id.list_high_scores);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Assurez-vous d'initialiser votre ScoreDao ici
        // scoreDao = ...
        Context context = requireContext();
        scoreDao = new ScoreDao(new ScoreBaseHelper(context,"BDD",1));

        List<Score> topScores = scoreDao.getTopScores(10);
        HighscoreAdapter adapter = new HighscoreAdapter(getContext(), topScores);
        listView.setAdapter(adapter);
    }
}
