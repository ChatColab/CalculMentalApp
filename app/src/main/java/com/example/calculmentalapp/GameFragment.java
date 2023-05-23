package com.example.calculmentalapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calculmentalapp.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;

    private TypeOperationEnum typeOperation;
    private Integer firstNumber;
    private Integer secondNumber;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private Integer doOperation() {
        Integer result;
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

    private void handleWrongAnswer() {
        //faire perdre une vie
        //check if game is over
        //sinon load new question
    }

    private void handleCorrectAnswer() {
        //faire gagner des points
        loadNewQuestion();
    }

    private void generateNewCalcul() {
        firstNumber = (int) (Math.random() * 10);
        secondNumber = (int) (Math.random() * 10);
        typeOperation = TypeOperationEnum.values()[(int) (Math.random() * 4)];
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

        loadNewQuestion();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswer();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
