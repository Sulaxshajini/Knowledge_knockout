package com.example.knowledgeknockout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView scoreText, guessText, hintText, messageText;
    private EditText inputGuess;
    private Button submitButton;
    private int score;
    private int remainingGuesses;
    private List<String> words;
    private String currentWord;
    private int correctGuesses; // Track the number of correct guesses
    private static final int MAX_SCORE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Retrieve user name from the intent
        String userName = getIntent().getStringExtra("userName");
        if (userName == null) userName = "Player";

        // Initialize views
        scoreText = findViewById(R.id.score_text);
        guessText = findViewById(R.id.guess_text);
        hintText = findViewById(R.id.hint_text);
        messageText = findViewById(R.id.message_text);
        inputGuess = findViewById(R.id.input_guess);
        submitButton = findViewById(R.id.submit_button);

        // Initialize game variables
        resetGame();

        submitButton.setOnClickListener(view -> submitGuess());
    }

    private void resetGame() {
        score = MAX_SCORE;
        remainingGuesses = 4;
        correctGuesses = 0;
        words = Arrays.asList("lepored", "cat", "lion", "donkey");
        currentWord = getNextWord();

        scoreText.setText("Score: " + score);
        guessText.setText("Guess the word:");
        hintText.setText("Hint: The word has " + currentWord.length() + " letters.");
        messageText.setText("");
        inputGuess.setEnabled(true);
        submitButton.setEnabled(true);
    }

    private String getNextWord() {
        // Return the next word based on the number of correct guesses
        if (correctGuesses < words.size()) {
            return words.get(correctGuesses);
        }
        return null; // No more words left
    }

    private void submitGuess() {
        String guess = inputGuess.getText().toString().toLowerCase();

        if (guess.isEmpty()) {
            Toast.makeText(this, "Please enter a guess.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (guess.equals(currentWord)) {
            score += 10; // Increase score for correct guess
            if (score > MAX_SCORE) {
                score = MAX_SCORE; // Ensure score does not exceed 100
            }
            correctGuesses++; // Increment correct guesses
            messageText.setText("Correct! The word was " + currentWord);
            currentWord = getNextWord(); // Get the next word
            if (currentWord != null) {
                hintText.setText("Hint: The word has " + currentWord.length() + " letters."); // Update hint
            } else {
                // All words guessed
                messageText.setText("Congratulations! You've guessed all words! Your score: " + score);
                showFinalScore(score); // Show final score
                return; // Exit from the method to avoid resetting
            }
        } else {
            score -= 5; // Decrease score for incorrect guess
            remainingGuesses--;

            if (remainingGuesses <= 0) {
                messageText.setText("Game Over! The word was " + currentWord);
                resetGame(); // Restart the game
                return;
            } else {
                messageText.setText("Wrong! Try again. Remaining guesses: " + remainingGuesses);
            }
        }

        // Ensure score does not drop below 0
        if (score < 0) {
            score = 0;
            messageText.setText("Your score is 0! Game Over!");
            resetGame(); // Restart the game
        }

        scoreText.setText("Score: " + score);
        inputGuess.setText("");
    }

    private void showFinalScore(int finalScore) {
        Toast.makeText(this, "Your final score is: " + finalScore, Toast.LENGTH_LONG).show();

        // Redirect to the start page after showing the score
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
