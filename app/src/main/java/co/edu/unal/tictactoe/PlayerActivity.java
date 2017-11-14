package co.edu.unal.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }

    public void playerOneClick(View view){
        startActivity(new Intent(PlayerActivity.this, MainActivity.class).putExtra("Player1", 'O').putExtra("Player2", 'X'));
    }

    public void playerTwoClick(View view){
        startActivity(new Intent(PlayerActivity.this, MainActivity.class).putExtra("Player1", 'X').putExtra("Player2", 'O'));
    }
}
