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
                userOverScore += userTurnScore;
                userTurnScore = 0;

                setTextScore(text);

                computerTurn();
            }
        });

    }

    // Changes the image of the die based on the roll result.
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

    // Generates and returns a pseudo-randon number between 1 and 6.
    public static int rollDie() {
        Random dieRoll = new Random();

        int dieResult = dieRoll.nextInt(6) + 1;

        return dieResult;
    }

    // Modifies TextView object with score of user.
    public void setTextScore(TextView msg) {
        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Your turn score: "+userTurnScore;
        msg.setText(scoreText);
    }

    // Modifies TextView object with score of computer.
    public void setCompScore(TextView msg) {
        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Computer turn score: "+compTurnScore;
        msg.setText(scoreText);
    }

    // Ends the computer turn and sets appropriate text indicating a 1 has rolled.
    public void endComputerTurn(TextView msg) {
        compTurnScore = 0;

        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Computer turn score: "+compTurnScore+" Computer rolled a 1!";
        msg.setText(scoreText);
    }

  // Removes access to roll and hold buttons. Method to be used during computer turn.
    public void alterButtons(Boolean boo) {
        Button rButton = (Button) findViewById(R.id.roll);
        rButton.setEnabled(boo);

        Button hButton = (Button) findViewById(R.id.hold);
        hButton.setEnabled(boo);

    }

    // Sets up a turn where the computer rolls a maximum score. Computer generates die rolls until their turn score
    // is 20 or higher, or a 1 is rolled. Player buttons are disabled during the computer turn with the alterButton method.
    public void computerTurn() {
        alterButtons(false);

        TextView text = (TextView) findViewById(R.id.textView);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        int cDie = 0;

        while(compTurnScore < 20) {

            cDie = rollDie();

            changeDie(cDie, image);

            setCompScore(text);

            if(cDie == 1) {
                endComputerTurn(text);
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
