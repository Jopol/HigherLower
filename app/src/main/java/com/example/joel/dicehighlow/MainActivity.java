package com.example.joel.dicehighlow;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<String> previousThrowArray;
    private ArrayAdapter throwAdapter;

    private int currentDiceIndex = 0;
    private int[] allDices;
    private ListView previousThrowView;
    private ImageView diceNumber;
    private FloatingActionButton btnLow, btnHigh;
    private TextView score;
    private TextView highScore;
    private Random random;
    private int numberTries = 0;
    private int numberHighScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize

        previousThrowView = findViewById(R.id.previousThrowView);
        previousThrowArray = new ArrayList<>();

        allDices = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};
        diceNumber = findViewById(R.id.diceNumber);

        btnLow = findViewById(R.id.btnLow);
        btnHigh = findViewById(R.id.btnHigh);

        score = findViewById(R.id.score);
        highScore = findViewById(R.id.highScore);

        random = new Random();

        updateUI();

        //
        btnLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Roll dice and get random number check if low
                currentDiceIndex = random.nextInt(6);
                diceNumber.setImageResource(allDices[currentDiceIndex]);
                addToList();
                if(currentDiceIndex <= 2) {
                    win();
                } else {
                    lose();
                }

            }
        });

        btnHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Roll dice and get random number check if high
                currentDiceIndex = random.nextInt(5);
                diceNumber.setImageResource(allDices[currentDiceIndex]);
                addToList();
                if(currentDiceIndex > 2){
                    win();
                } else {
                    lose();
                }
            }
        });

        previousThrowView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Remove from array
                previousThrowArray.remove(position);
                updateUI();
                return true;

            }
        });
    }
    //Display win message and add to tries
    private void win() {
        numberTries++;
        score.setText("Score: "+ numberTries);
        Toast.makeText(this,"You win", Toast.LENGTH_SHORT).show();
    }
    //Display lose message, update highscore and score
    private void lose() {
        //Highscore
        if(numberTries > numberHighScore){
            numberHighScore = numberTries;
            String textHighscore = "Highscore: " + numberHighScore;
            highScore.setText(textHighscore);
        }
        //Reset tries
        numberTries = 0;
        //Display Score
        String textScore = "Score: "+ numberTries;
        score.setText(textScore);
        Toast.makeText(this, "You lost", Toast.LENGTH_SHORT).show();
    }
    //Add roll list to Listview
    private void addToList(){
        String text = "You rolled " + Integer.toString(currentDiceIndex + 1);
        previousThrowArray.add(0,text);
        updateUI();
    }
    //Update previousThrowArray(Listview)
    private void updateUI() {
        if (throwAdapter == null) {
            throwAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, previousThrowArray);
            previousThrowView.setAdapter(throwAdapter);
        } else {
            throwAdapter.notifyDataSetChanged();
        }
    }
}
