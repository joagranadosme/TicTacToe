package co.edu.unal.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TicTacToe game;
    private Button mBoardButtons[];
    private TextView mInfoTextView, mHumanWins, mTies, mComputerWins;
    private int wins=0, losses=0, ties=0;
    private boolean turn = false;

    private char player1, player2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        player1 = intent.getCharExtra("Player1", ' ');
        player2 = intent.getCharExtra("Player2", ' ');

        mDatabase = FirebaseDatabase.getInstance().getReference("test");

        mBoardButtons = new Button[9];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        game = new TicTacToe();
        startNewGame();

        mDatabase.child(player1 + "").setValue(-1);
        mDatabase.child(player2 + "").setValue(-1);

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject json = new JSONObject(dataSnapshot.getValue().toString());
                    int position = json.getInt(player2 + "");
                    if(position != -1) {
                        unblock();
                        setMove(player2, position);
                        stateGame();
                    }
                }catch (Exception e){
                    Log.e("Json: ", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Main Activity", "Error reading");
            }
        });

    }

    private void startNewGame(){

        game.clearBoard();

        for(int i=0; i<mBoardButtons.length; i++){
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

    }

    private void setMove(char player, int location){

        game.make_move(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));

        if(player == 'O')
            mBoardButtons[location].setTextColor(Color.GREEN);
        else
            mBoardButtons[location].setTextColor(Color.RED);

    }

    private void block(){
        for(int i=0; i<9; i++)
            mBoardButtons[i].setEnabled(false);
    }

    private void unblock(){
        for(int i=0; i<9; i++)
            if(game.free(i))
                mBoardButtons[i].setEnabled(true);
    }

    public void sleep(int m){
        try{
            Thread.sleep(m);
        }catch (Exception e){
            Log.e("Sleep: ", e.toString());
        }
    }

    public void stateGame(){
        int state = game.state(player1, player2);
        switch (state){
            case 1:
                Toast.makeText(MainActivity.this, "¡Ganaste!", Toast.LENGTH_SHORT).show();
                block();
                break;
            case -1:
                Toast.makeText(MainActivity.this, "¡Perdiste!", Toast.LENGTH_SHORT).show();
                block();
                break;
            default:
                break;
        }
    }

    private class ButtonClickListener implements View.OnClickListener{

        int location;

        public ButtonClickListener(int location){
            this.location = location;
        }

        @Override
        public void onClick(View v) {

            if(mBoardButtons[location].isEnabled()){
                block();
                setMove(player1, location);
                stateGame();
                mDatabase.child(player1 + "").setValue(location);
                mDatabase.child(player2 + "").setValue(-1);

            }

        }

    }
}
