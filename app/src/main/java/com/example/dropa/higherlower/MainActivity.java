package com.example.dropa.higherlower;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int currentImageIndex = 0;
    private int[] mImageNames;
    private ImageView mImageView;
    private FloatingActionButton mLowerFAB;
    private FloatingActionButton mHigherFAB;
    private ListView mListView;
    private List<String> mThrows;
    private ArrayAdapter<String> mAdapter;
    private int highScore;
    private int currentScore;
    private TextView mScoreText;
    private TextView mHighScoreText;
    int random;
    int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highScore = 0;
        currentScore = 0;
        mLowerFAB = findViewById(R.id.floatingActionButton_low);
        mHigherFAB = findViewById(R.id.floatingActionButton_high);
        mImageView = findViewById(R.id.imageView);
        mListView = findViewById(R.id.list_view);
        mThrows = new ArrayList<>();
        mImageNames = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};
        mScoreText = findViewById(R.id.textView_score);
        mHighScoreText = findViewById(R.id.textView_highScore);

        // Define what happens when the user clicks the "next image" button
        mHigherFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = throwDice();
                if (random > currentImageIndex)
                    correctScore();
                else
                    wrongScore();
                updateImage(random);
            }
        });

        // Define what happens when the user clicks the "next image" button
        mLowerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = throwDice();
                if (random < currentImageIndex)
                    correctScore();
                else
                    wrongScore();
                updateImage(random);
            }
        });
    }

    // Handles logic & output when user guesses correct
    void correctScore() {
        currentScore++;
        Toast.makeText(MainActivity.this, "Correct! Your score is " + currentScore, Toast.LENGTH_SHORT).show();
        mScoreText.setText(" Score: " + currentScore);
        mHighScoreText.setText(" HighScore: " + highScore);
    }

    // Handles logic & output when user guesses wrong
    void wrongScore() {
        Toast.makeText(MainActivity.this, "Wrong! You're back at 0!", Toast.LENGTH_SHORT).show();
        if (currentScore > highScore) {
            newHighScore();
            currentScore = 0;
        }
    }

    // Handles logic & output when user breaks the high score
    void newHighScore() {
        highScore = currentScore;
        Toast.makeText(MainActivity.this, "You improved your highscore! Your new highscore is " + highScore, Toast.LENGTH_SHORT).show();
        mScoreText.setText(" Score: " + currentScore);
        mHighScoreText.setText(" HighScore: " + highScore);
    }

    void updateImage(int random) {
        mImageView.setImageResource(mImageNames[random]);
        currentImageIndex = random;
    }

    void updateUI() {
        // If the list adapter is null, a new one will be instantiated and set on our list view.
        if (mAdapter == null) {
            // We can use ‘this’ for the context argument because an Activity is a subclass of the Context class.
            // The ‘android.R.layout.simple_list_item_1’ argument refers to the simple_list_item_1 layout of the Android layout directory.
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mThrows);
            mListView.setAdapter(mAdapter);
        } else {
            // When the adapter is not null, it has to know what to do when our dataset changes, when a change happens we need to call this method on the adapter so that it will update internally.
            mAdapter.notifyDataSetChanged();
        }
        mListView.setSelection(mThrows.size() - 1);
    }

    int throwDice() {
        Random rnd = new Random();
        n = rnd.nextInt(6);
        mThrows.add("Your throw is " + (n + 1));
        updateUI();
        return n;
    }

}
