package com.example.hfu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int GRID_SIZE = 3;
    private final int MAX_MOVES = GRID_SIZE * GRID_SIZE;

    private Button[][] gridButtons;
    private TextView playerTurnTextView;
    private TextView scoreTextView;

    private int playerScore;
    private int computerScore;
    private int movesCount;

    private boolean isPlayerTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerTurnTextView = findViewById(R.id.playerTurnTextView);
        scoreTextView = findViewById(R.id.scoreTextView);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(GRID_SIZE);
        gridLayout.setRowCount(GRID_SIZE);

        gridButtons = new Button[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Button button = new Button(this);
                button.setOnClickListener(this);
                button.setBackgroundColor(Color.WHITE);
                gridButtons[i][j] = button;
                gridLayout.addView(button);
            }
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        resetGame();
    }

    private void resetGame() {
        playerScore = 0;
        computerScore = 0;
        movesCount = 0;
        isPlayerTurn = true;

        updateScoreText();
        updatePlayerTurnText();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        button.setEnabled(false);

        if (isPlayerTurn) {
            button.setText("S");
            button.setTextColor(Color.BLUE);
        } else {
            button.setText("O");
            button.setTextColor(Color.RED);
        }

        movesCount++;

        if (checkForWin()) {
            if (isPlayerTurn)
                playerScore++;
            else
                computerScore++;

            updateScoreText();
            disableAllButtons();

            if (playerScore + computerScore == MAX_MOVES) {
                if (playerScore > computerScore)
                    playerTurnTextView.setText("Player wins!");
                else if (playerScore < computerScore)
                    playerTurnTextView.setText("Computer wins!");
                else
                    playerTurnTextView.setText("It's a draw!");
            } else {
                playerTurnTextView.setText("Player scores!");
            }
        } else if (movesCount == MAX_MOVES) {
            disableAllButtons();
            playerTurnTextView.setText("It's a draw!");
        } else {
            isPlayerTurn = !isPlayerTurn;
            updatePlayerTurnText();
        }
    }

    private boolean checkForWin() {
        String[][] gridValues = new String[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridValues[i][j] = gridButtons[i][j].getText().toString();
            }
        }

        // Check rows
        for (int i = 0; i < GRID_SIZE; i++) {
            if (gridValues[i][0].equals("S") && gridValues[i][1].equals("O") && gridValues[i][2].equals("S"))
                return true;
        }

        // Check columns
        for (int j = 0; j < GRID_SIZE; j++) {
            if (gridValues[0][j].equals("S") && gridValues[1][j].equals("O") && gridValues[2][j].equals("S"))
                return true;
        }

        // Check diagonal (top-left to bottom-right)
        if (gridValues[0][0].equals("S") && gridValues[1][1].equals("O") && gridValues[2][2].equals("S"))
            return true;

        // Check diagonal (top-right to bottom-left)
        if (gridValues[0][2].equals("S") && gridValues[1][1].equals("O") && gridValues[2][0].equals("S"))
            return true;

        return false;
    }

    private void updateScoreText() {
        scoreTextView.setText("Player: " + playerScore + "  Computer: " + computerScore);
    }

    private void updatePlayerTurnText() {
        if (isPlayerTurn)
            playerTurnTextView.setText("Player's Turn");
        else
            playerTurnTextView.setText("Computer's Turn");
    }

    private void disableAllButtons() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridButtons[i][j].setEnabled(false);
            }
        }
    }
}
