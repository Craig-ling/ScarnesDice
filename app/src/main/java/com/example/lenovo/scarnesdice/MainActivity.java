package com.example.lenovo.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/* The class variables keep track of the various scores determined by dice rolls.
 * There is a Boolean variable that will assist in ending the computer turn.
 * The handler object is used to delay calling the method that runs the computer turn,
 * it takes the runnable object as an argument.*/
public class MainActivity extends AppCompatActivity {

    private static Boolean compTurnCheck = false;
    private static int userOverScore = 0;
    private static int userTurnScore = 0;
    private static int compOverScore = 0;
    private static int compTurnScore = 0;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {

            // Returns and ends recursive call when computer turn ends.
            if(compTurnCheck) {
                alterButtons(true);
                return;
            }

            computerTurn();

            handler.postDelayed(this, 1000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = (ImageView) findViewById(R.id.imageView);
        final TextView text = (TextView) findViewById(R.id.textView);
        final TextView text2 = (TextView) findViewById(R.id.textView2);

        // Roll button is defined, on user click will call changeDie method to generate pseudo-random
        // number between 1 and 6 inclusive. On a roll of 1, the user turn ends and computer turn
        // is initiated.
        final Button rollButton = (Button) findViewById(R.id.roll);
        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                compTurnCheck = false;
                int die = rollDie();

                changeDie(die, image);

                if(die == 1) {
                    userTurnScore = 0;
                    setTextScore(text);
                    setRollOneText(text2, "You rolled a 1!");
                    handler.postDelayed(runnable, 1000);

                }
                else {
                    userTurnScore += die;
                    setTextScore(text);
                }


            }
        });

        // Reset button will set all fields (member variables) to zero.
        final Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userOverScore = 0;
                compOverScore = 0;
                userTurnScore = 0;
                compTurnScore = 0;

                setTextScore(text);
                setRollOneText(text2, "");
            }
        });

        // Hold button adds the turn score to the total score for the user and begins computer turn.
        final Button holdButton = (Button) findViewById(R.id.hold);
        holdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userOverScore += userTurnScore;
                userTurnScore = 0;

                setTextScore(text);
                setRollOneText(text2, "");

                // Runs computer turn function with 1000 ms time delay.
                handler.postDelayed(runnable, 1000);
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

    // Generates and returns a pseudo-random number between 1 and 6.
    public static int rollDie() {
        Random dieRoll = new Random();

        return dieRoll.nextInt(6) + 1;
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

    // Ends the computer turn and sets appropriate text.
    public void endComputerTurn(TextView msg) {
        compTurnScore = 0;

        String scoreText = "Your score: "+userOverScore+" Computer score: "+compOverScore+" " +
                "Computer turn score: "+compTurnScore;
        msg.setText(scoreText);
    }

    // Indicates to the user when a 1 was rolled.
    public void setRollOneText(TextView msg, String s) {

        msg.setText(s);
    }

  // Removes access to roll and hold buttons. Method to be used during computer turn.
    public void alterButtons(Boolean boo) {
        Button rButton = (Button) findViewById(R.id.roll);
        rButton.setEnabled(boo);

        Button hButton = (Button) findViewById(R.id.hold);
        hButton.setEnabled(boo);

    }

    // Sets up a turn where the computer rolls a maximum score. Computer generates die rolls until
    // their turn score is 20 or higher, or a 1 is rolled. Player buttons are disabled during the
    // computer turn with the alterButton method.
    public void computerTurn() {
        alterButtons(false);

        TextView text = (TextView) findViewById(R.id.textView);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        TextView text2 = (TextView) findViewById(R.id.textView2);

        int cDie = rollDie();

        changeDie(cDie, image);

        compTurnScore += cDie;

            if(cDie == 1) {
                compTurnCheck = true;
                endComputerTurn(text);
                setRollOneText(text2, "The computer rolled a 1!");

            }
            else if (compTurnScore >= 20) {
                compTurnCheck = true;
                compOverScore += compTurnScore;
                compTurnScore = 0;
                setRollOneText(text2, "The computer holds.");

            }

            setCompScore(text);

    }
}
