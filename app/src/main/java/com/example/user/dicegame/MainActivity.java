package com.example.user.dicegame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Random rn;
    Button Roll;
    Button Hold;
    Button reset;
    ImageView image;
    ImageView image2;
    TextView score;
    int playerSocre=0;
    int player;
    int computerScore=0;
    int computer;
    int dice=-1;
    int seconddice=-1;
    boolean keep = true;
    Drawable dice1;
    Drawable dice2;
    Drawable dice3;
    Drawable dice4;
    Drawable dice5;
    Drawable dice6;

    Handler myhandler = new Handler() {};
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            if(!keep)
                return;
            Roll.setEnabled(false);
            Hold.setEnabled(false);
            rn = new Random();
            dice = rn.nextInt(6) + 1;
            seconddice = rn.nextInt(6) + 1;
                if(computerScore>=20)
                {
                    computer = computer+computerScore;
                    printScore(player,computer);
                    if(computer>=100)
                    {
                        playerSocre = 0;
                        computerScore=0;
                        player=0;
                        computer=0;
                        printScore(player,computer);
                        dice=-1;
                        message("computer", "player");
                        Roll.setEnabled(true);
                        Hold.setEnabled(true);
                        keep =false;
                    }
                    computerScore =0;
                    dice=-1;
                    Roll.setEnabled(true);
                    Hold.setEnabled(true);
                    keep = false;
                }


            if(dice==1&&seconddice==1)
            {
                setImage(dice1,dice1);

                computer = 0;
                computerScore =0;
                printScore(player,computer);
                dice=-1;
                Roll.setEnabled(true);
                Hold.setEnabled(true);
                keep =false;
            }
            else if(dice==1||seconddice==1)
            {
                if(dice==1) {
                    switch (seconddice) {
                        case 2:
                            setImage(dice1, dice2);
                            break;
                        case 3:
                            setImage(dice1, dice3);
                            break;
                        case 4:
                            setImage(dice1, dice4);
                            break;
                        case 5:
                            setImage(dice1, dice5);
                            break;
                        case 6:
                            setImage(dice1, dice6);
                            break;
                    }
                }
                else
                {
                    switch (dice) {
                        case 2:
                            setImage(dice2, dice1);
                            break;
                        case 3:
                            setImage(dice3, dice1);
                            break;
                        case 4:
                            setImage(dice4, dice1);
                            break;
                        case 5:
                            setImage(dice5, dice1);
                            break;
                        case 6:
                            setImage(dice6, dice1);
                            break;
                    }
                }

                dice =-1;
                printScore(player,computer);
                Roll.setEnabled(true);
                Hold.setEnabled(true);
                keep = false;
            }
            else if(dice==seconddice)
            {
                switch(dice)
                {
                    case 2:
                        setImage(dice2,dice2);
                        break;
                    case 3:
                        setImage(dice3,dice3);
                        break;
                    case 4:
                        setImage(dice4,dice4);
                        break;
                    case 5:
                        setImage(dice5,dice5);
                        break;
                    case 6:
                        setImage(dice6,dice6);
                        break;

                }

                computerScore+= dice+seconddice;
                computer += computerScore;
                computerScore=0;
                printSocoreWithCS(player,computer,computerScore);
            }
            else
            {
                rollImage();
                computerScore += dice+seconddice;
                printSocoreWithCS(player,computer,computerScore);
            }


            myhandler.postDelayed(this, 1000);
        }
    };
    public void stop()
    {
        myhandler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Roll = (Button)findViewById(R.id.roll);
        Roll.setOnClickListener(this);
        Hold = (Button)findViewById(R.id.hold);
        Hold.setOnClickListener(this);
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);

        image = (ImageView)findViewById(R.id.imageView);
        image2 = (ImageView)findViewById(R.id.imageView2);

        score = (TextView)findViewById(R.id.textView);


        dice1 = getResources().getDrawable(R.drawable.dice1);
        dice2 = getResources().getDrawable(R.drawable.dice2);
        dice3 = getResources().getDrawable(R.drawable.dice3);
        dice4 = getResources().getDrawable(R.drawable.dice4);
        dice5 = getResources().getDrawable(R.drawable.dice5);
        dice6 = getResources().getDrawable(R.drawable.dice6);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.roll:
                roll();
                break;
            case R.id.hold:
                if(dice==-1)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Please Roll the Dice First!!!")
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    alertDialog.show();
                    break;
                }
                player += playerSocre;
                printScore(player,computer);
                if(player>=100)
                {
                    message("player","computer");
                    playerSocre=0;
                    computerScore=0;
                    player=0;
                    computer=0;
                    printScore(player,computer);
                    break;
                }
                playerSocre=0;
                keep = true;
                computerTurn();

                break;
            case R.id.reset:
                playerSocre=0;
                computerScore=0;
                player=0;
                computer=0;
                printScore(player,computer);
                Hold.setEnabled(true);
                Roll.setEnabled(true);
                keep = false;
                break;

        }
    }
    public void message(String winner, String loser)
    {
        String w = winner;
        String l = loser;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if(winner=="player")
        alertDialog.setTitle("WIN");
        else
        alertDialog.setTitle("LOSE");
        alertDialog.setMessage(w+" Beated"+l+"!!!")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }
    public void printScoreWithTurn(int pSocre,int cScore,int turnScore)
    {
        score.setText("PlayerScore: " + pSocre + "\nComputerScore: " + cScore + "\nYour Turn Score: " + turnScore);
    }
    public void printScore(int pSocre,int cScore)
    {
        score.setText("PlayerScore: "+pSocre+"\nComputerScore: "+cScore);
    }
    public void printSocoreWithCS(int pSocre,int cScore,int turnScore)
    {
        score.setText("PlayerScore: "+pSocre+"\nComputerScore: "+cScore+ "\nComputer Round Score: "+turnScore);

    }
    public void roll(){
        rn = new Random();
        dice = rn.nextInt(6) + 1;
        seconddice = rn.nextInt(6)+1;

        if(dice==1&&seconddice==1)
        {
            setImage(dice1,dice1);

            player = 0;
            playerSocre =0;
            printScore(player,computer);
            dice=-1;
            keep =true;
            computerTurn();
        }
        else if(dice==1||seconddice==1)
        {
            if(dice==1) {
                switch (seconddice) {
                    case 2:
                        setImage(dice1, dice2);
                        break;
                    case 3:
                        setImage(dice1, dice3);
                        break;
                    case 4:
                        setImage(dice1, dice4);
                        break;
                    case 5:
                        setImage(dice1, dice5);
                        break;
                    case 6:
                        setImage(dice1, dice6);
                        break;
                }
            }
            else
            {
                switch (dice) {
                    case 2:
                        setImage(dice2, dice1);
                        break;
                    case 3:
                        setImage(dice3, dice1);
                        break;
                    case 4:
                        setImage(dice4, dice1);
                        break;
                    case 5:
                        setImage(dice5, dice1);
                        break;
                    case 6:
                        setImage(dice6, dice1);
                        break;
                }
            }

            dice =-1;
            printScore(player,computer);
            computerTurn();
        }
        else if(dice==seconddice)
        {
            switch(dice)
            {
                case 2:
                    setImage(dice2,dice2);
                    break;
                case 3:
                    setImage(dice3,dice3);
                    break;
                case 4:
                    setImage(dice4,dice4);
                    break;
                case 5:
                    setImage(dice5,dice5);
                    break;
                case 6:
                    setImage(dice6,dice6);
                    break;

            }

            playerSocre+= dice+seconddice;
            player+= playerSocre;
            playerSocre=0;
            printScoreWithTurn(player,computer,playerSocre);
        }
        else
        {
            rollImage();
            playerSocre += dice+seconddice;
            printScoreWithTurn(player,computer,playerSocre);
        }
    }
    public void setImage(Drawable dice1,Drawable dice2)
    {
        image.setImageDrawable(dice1);
        image2.setImageDrawable(dice2);
    }
    public void computerTurn()
    {
        myhandler.postDelayed(myRunnable, 0);
    }

    public void rollImage()
    {
        switch (dice)
        {
            case 2:
                switch(seconddice)
                {
                    case 3:
                        setImage(dice2,dice3);
                        break;
                    case 4:
                        setImage(dice2,dice4);
                        break;
                    case 5:
                        setImage(dice2,dice5);
                        break;
                    case 6:
                        setImage(dice2,dice6);
                        break;
                }
                break;
            case 3:
                switch(seconddice)
                {
                    case 2:
                        setImage(dice3,dice2);
                        break;
                    case 4:
                        setImage(dice3,dice4);
                        break;
                    case 5:
                        setImage(dice3,dice5);
                        break;
                    case 6:
                        setImage(dice3,dice6);
                        break;
                }
                break;
            case 4:
                switch(seconddice)
                {
                    case 3:
                        setImage(dice4,dice3);
                        break;
                    case 2:
                        setImage(dice4,dice2);
                        break;
                    case 5:
                        setImage(dice4,dice5);
                        break;
                    case 6:
                        setImage(dice4,dice6);
                        break;
                }
                break;
            case 5:
                switch(seconddice)
                {
                    case 3:
                        setImage(dice5,dice3);
                        break;
                    case 4:
                        setImage(dice5,dice4);
                        break;
                    case 2:
                        setImage(dice5,dice2);
                        break;
                    case 6:
                        setImage(dice5,dice6);
                        break;
                }
                break;
            case 6:
                switch(seconddice)
                {
                    case 3:
                        setImage(dice6,dice3);
                        break;
                    case 4:
                        setImage(dice6,dice4);
                        break;
                    case 5:
                        setImage(dice6,dice5);
                        break;
                    case 2:
                        setImage(dice6,dice2);
                        break;
                }
                break;
        }
    }
}


