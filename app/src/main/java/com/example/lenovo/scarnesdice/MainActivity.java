package com.example.lenovo.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int userOverScore = 0;
    private static int userTurnScore = 0;
    private static int compOverScore = 0;
    private static int compTurnScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = (ImageView) findViewById(R.id.imageView);
        final TextView text = (TextView) findViewById(R.id.textView);

        final Button rollButton = (Button) findViewById(R.id.roll);
        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int die = rollDie();

                changeDie(die, image);

                if(die == 1) {
                    userTurnScore = 0;
                    setTextScore(text);
                    computerTurn();
                }
                else {
                    userTurnScore += die;
                    setTextScore(text);
                }


            }
        });

        final Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userOverScore = 0;
                compOverScore = 0;
                userTurnScore = 0;
                compTurnScore = 0;

                setTextScore(text);
            }
        });

        final Button holdButton = (Button) findViewById(R.id.hold);
        holdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userOverScore = userTurnScore;
                userTurnScore = 0;

                setTextScore(text);

                computerTurn();
            }
        });

    }

    public void changeDie(int i, ImageView image) {
        switch (i) {
            case 1: image.setImageResource(R.drawable.dice1);
                break;
            case 2: image.setImageResource(R.drawable.dice2);
                break;
            case 3: image.setImageResource(R.drawable.dice3);
                break;
            case 4: image.setImageResource(R.drawable.dice4);
                break;
            case 5: image.setImageResource(R.drawable.dice5);
                break;
            case 6: image.setImageResource(R.drawable.dice6);
                break;
        }
    }

    public static int rollDie() {
        Random dieRoll = new Random();

        int dieResult = dieRoll.nextInt(6) + 1;

        return dieResult;
    }

    public void setTextScore(TextView msg) {
        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Your turn score: "+userTurnScore;
        msg.setText(scoreText);
    }

    public void setCompScore(TextView msg) {
        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Computer turn score: "+compTurnScore;
        msg.setText(scoreText);
    }

  /*  public void endComputerTurn() {
        compTurnScore = 0;

        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Computer turn score: "+compTurnScore;
        msg.setText(scoreText);
    }*/

    public void alterButtons(Boolean boo) {
        Button rButton = (Button) findViewById(R.id.roll);
        rButton.setEnabled(boo);

        Button hButton = (Button) findViewById(R.id.hold);
        hButton.setEnabled(boo);
    }

    public void computerTurn() {
        alterButtons(false);

        TextView text = (TextView) findViewById(R.id.textView);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        int cDie = 0;

        while(cDie < 20) {

            cDie = rollDie();

            changeDie(cDie, image);

            setCompScore(text);

            if(cDie == 1) {
                //endComputerTurn();
                compTurnScore = 0;
                break;
            }

            compTurnScore += cDie;

        }

        compOverScore += compTurnScore;

        setTextScore(text);

        alterButtons(true);
    }
}
