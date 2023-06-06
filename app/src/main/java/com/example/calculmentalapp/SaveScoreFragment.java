package com.example.calculmentalapp;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.calculmentalapp.databinding.FragmentSaveScoreBinding;
import com.example.calculmentalapp.db.database.ScoreBaseHelper;
import com.example.calculmentalapp.db.database.ScoreDao;
import com.example.calculmentalapp.db.entities.Score;

public class SaveScoreFragment extends Fragment {

    FragmentSaveScoreBinding binding;

    private ScoreDao scoreDao;

    @Override
    public View onCreateView(
            android.view.LayoutInflater inflater, android.view.ViewGroup container,
            android.os.Bundle savedInstanceState
    ) {

        binding = FragmentSaveScoreBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(android.view.View view, android.os.Bundle savedInstanceState) {

        scoreDao = new ScoreDao(new ScoreBaseHelper(getContext(),"BDD",1));

        int currentScore = ScoreManager.getScore();
        super.onViewCreated(view, savedInstanceState);
        binding.textScore.setText(String.valueOf(currentScore));

        binding.btnSaveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.editName.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.username_error, Toast.LENGTH_SHORT).show();
                } else {
                    Score score = new Score();
                    score.setScore(currentScore);
                    score.setUserName(username);
                    scoreDao.create(score);

                    NavHostFragment.findNavController(SaveScoreFragment.this)
                            .navigate(R.id.action_SaveScoreFragment_to_HighscoreFragment);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
