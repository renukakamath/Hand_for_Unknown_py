package com.example.hfu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Guesstheword extends AppCompatActivity {

    private List<String> words;
    private Random random;
    private String currentWord;
    private int currentWordIndex;
    private int score;
    private int maxRounds = 10;
    private int currentRound = 0;

    private TextView wordTextView;
    private EditText guessEditText;
    private Button submitButton;
    private TextView scoreTextView;
    private TextView roundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesstheword);

        // Initialize views
        wordTextView = findViewById(R.id.wordTextView);
        guessEditText = findViewById(R.id.guessEditText);
        submitButton = findViewById(R.id.submitButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        roundTextView = findViewById(R.id.roundTextView);

        // Initialize word list
        words = new ArrayList<>();
//        words.add("C__e");
        words.add("C__fee");
//        words.add("__nana");
        words.add("B__ana");
        words.add("P__eapple");
        words.add("_pple");
        words.add("W__ermelon");
        words.add("B__ry");
        words.add("__ange");
        words.add("G__pe");
        words.add("L__on");
//        words.add("__berry");
//        words.add("P__melo");
//        words.add("__nberry");
//        words.add("S__berry");
        words.add("M__go");
//        words.add("__icot");
        words.add("R__pberry");
        words.add("P_ch");
        words.add("K_wi");
//        words.add("__ple");
//        words.add("Str__wberry");
//        words.add("R__isins");
        words.add("Ch__ry");
//        words.add("Bl__ckberry");
//        words.add("C__ntaloupe");
//        words.add("H__neydew");
//        words.add("C__rambola");
//        words.add("L__chis");
//        words.add("F__ijoa");

        random = new Random();

        // Start the game
        startGame();

        // Submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
    }

    private void startGame() {
        currentRound = 0;
        score = 0;
        updateScore();
        nextRound();
    }

    private void nextRound() {
        if (currentRound <= maxRounds) {
            currentWordIndex = random.nextInt(words.size());
            currentWord = words.get(currentWordIndex);
            wordTextView.setText(currentWord);
            guessEditText.setText("");
            currentRound++;
            updateRound();
        } else {
            // Game over
            Toast.makeText(this, "Game over! Final score: " + score, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Guesstheword.class));
        }
    }

    private void checkGuess() {
        String guess = guessEditText.getText().toString().toLowerCase().trim();
        String originalWord = words.get(currentWordIndex).toLowerCase();
        String guesss = guessEditText.getText().toString().toLowerCase().trim();
        String originalWords = words.get(currentWordIndex).toLowerCase();

        if (TextUtils.isEmpty(guess)) {
            Toast.makeText(this, "Please enter a guess", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the guess matches the original word while allowing for missing letters
        boolean isCorrect = true;
        StringBuilder displayWord = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char originalChar = originalWord.charAt(i);
            if (originalChar != '_' && guessChar != originalChar) {
                isCorrect = false;
            }
            displayWord.append(originalChar != '_' ? originalChar : '_');
        }

        if (isCorrect) {
            score++;
            updateScore();
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect! The correct word is " + originalWords, Toast.LENGTH_SHORT).show();
        }

        nextRound();
    }


    private void updateScore() {
        scoreTextView.setText("Score: " + score);
    }

    private void updateRound() {
        roundTextView.setText("Round: " + currentRound + " / " + maxRounds);
    }
}
