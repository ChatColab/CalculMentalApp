package com.example.calculmentalapp;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.calculmentalapp.databinding.FragmentSaveScoreBinding;

public class SaveScoreFragment extends Fragment {

    FragmentSaveScoreBinding binding;

    @Override
    public View onCreateView(
            android.view.LayoutInflater inflater, android.view.ViewGroup container,
            android.os.Bundle savedInstanceState
    ) {

        binding = FragmentSaveScoreBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(android.view.View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textScore.setText(String.valueOf(ScoreManager.getScore()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
