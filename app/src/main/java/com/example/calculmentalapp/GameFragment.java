package com.example.calculmentalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.calculmentalapp.databinding.FragmentGameBinding;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class GameFragment extends Fragment {

    private Toolbar toolbar;

    private FragmentGameBinding binding;

    private TypeOperationEnum typeOperation;
    private Integer firstNumber;
    private Integer secondNumber;
    private int score = 0;
    private int lives = 3;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private int doOperation() {
        int result;
        switch (typeOperation) {
            case ADD:
                result = firstNumber + secondNumber;
                break;
            case SUBSTRACT:
                result = firstNumber - secondNumber;
                break;
            case MULTIPLY:
                result = firstNumber * secondNumber;
                break;
            case DIVIDE:
                result = firstNumber / secondNumber;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeOperation);
        }
        return result;
    }

    private Integer getAnswer() {
        return Integer.parseInt(String.valueOf(binding.etAnswer.getText()));
    }

    private boolean isAnswerCorrect() {
        return getAnswer().equals(doOperation());
    }

    private void modifyLifeTextView() {
        binding.tvLives.setText(String.valueOf(lives));
    }

    private void restartGame() {
        lives = 3;
        score = 0;
        modifyLifeTextView();
        loadNewQuestion();
    }

    private void showGameOverScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Game Over");
        builder.setMessage("Your final score: " + score);
        builder.setPositiveButton(R.string.save_score, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ScoreManager.setScore(score);
                score = 0;
                NavHostFragment.findNavController(GameFragment.this)
                        .navigate(R.id.action_GameFragment_to_SaveScoreFragment);
            }
        });
        builder.setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartGame();
            }
        });
        builder.setCancelable(false); // Disable canceling the dialog by pressing outside of it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handleWrongAnswer() {
        lives--;
        modifyLifeTextView();
        if (lives == 0) {
            showGameOverScreen();
        } else {
            loadNewQuestion();
        }
    }

    private void handleCorrectAnswer() {
        score++;
        loadNewQuestion();
    }

    private void generateNewCalcul() {
        Random random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        int plage = 100;

        firstNumber = random.nextInt(plage);

        typeOperation = TypeOperationEnum.values()[random.nextInt(4)];

        if (typeOperation == TypeOperationEnum.DIVIDE) {
            //cas de division par 0
            do {
                secondNumber = random.nextInt(plage);
            } while (secondNumber == 0);

            //pour s'assurer que le résultat est entier
            firstNumber = firstNumber - (firstNumber % secondNumber);
        } else {
            secondNumber = random.nextInt(plage);
        }
    }

    private void loadNewQuestion() {
        binding.etAnswer.setText("");

        generateNewCalcul();
        binding.tvOperation.setText(firstNumber + " " + typeOperation.getSymbol() + " " + secondNumber);
        //clear text view and input field
    }

    private void handleAnswer() {
        boolean answerCorrect = isAnswerCorrect();
        if (!answerCorrect) {
            handleWrongAnswer();
        } else {
            handleCorrectAnswer();
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Score: ");


        lives = 3;
        modifyLifeTextView();
        loadNewQuestion();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.etAnswer.getText().toString().isEmpty() && lives > 0 && binding.etAnswer.getText().toString().length() < 10) {
                    handleAnswer();
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
