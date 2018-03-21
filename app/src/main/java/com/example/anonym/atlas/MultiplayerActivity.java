package com.example.anonym.atlas;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class MultiplayerActivity extends AppCompatActivity {

    InputStream inputStream;
    BufferedReader in;
    private String IncomingPlace,placeEntered,place_to_be_passed1,place_to_be_passed2;
    Random random=new Random();
    ArrayList<String> keys = new ArrayList<String>();
    LinkedHashMap<String,String> all_place = new LinkedHashMap<>(46354);
    LinkedHashMap<String,String> all_copy = new LinkedHashMap<>(46354);
    Button player1_submit,player2_submit;
    TextView player1_place,player2_place,turnindicator;
    EditText player1_edit,player2_edit;
    boolean player1turn=false;
    int firstuse=0;
    Character x = '#';


    //button methods
    public void submit1(View view) {
        player1_edit.setHint("");
        if (TextUtils.isEmpty(player1_edit.getText().toString())) {
            Toast.makeText(this, "Please enter a name of the place first.", Toast.LENGTH_LONG).show();
        }
        else {
            placeEntered = player1_edit.getText().toString().toLowerCase();
            Character temp = placeEntered.charAt(0);
            Log.d("user-place index 0 : ", temp.toString());
            Log.d("comp-place index end : ", x.toString());
            Log.d("firstuse : ", String.valueOf(firstuse));
            if ((all_copy.containsKey(placeEntered) && firstuse == 1) || (all_copy.containsKey(placeEntered) && temp == x)) {
                String place = all_copy.get(placeEntered);
                player1_place.setText(place);
                place_to_be_passed1 = placeEntered;
                all_copy.remove(placeEntered);
                keys.remove(placeEntered);
                x = placeEntered.charAt(placeEntered.length() - 1);
                firstuse = 0;
                player1_edit.setText("");
                player2_edit.setHint("Enter place from '" + x.toString() + "'");
                turnindicator.setText("Player 2's turn");
                player1_submit.setEnabled(false);
                player1_edit.setEnabled(false);
                player2_submit.setEnabled(true);
                player2_edit.setEnabled(true);
            }
            else if (!all_copy.containsKey(placeEntered) || temp != x) {
                Toast.makeText(this, "Place invalid or repeated, Player 2 Wins!", Toast.LENGTH_LONG).show();
                onStart(null);
            }
        }
    }

    public void submit2(View view){
        player2_edit.setHint("\t");
        if (TextUtils.isEmpty(player2_edit.getText().toString())) {
            Toast.makeText(this, "Please enter a name of the place first.", Toast.LENGTH_LONG).show();
        }
        else {
            placeEntered = player2_edit.getText().toString().toLowerCase();
            Character temp = placeEntered.charAt(0);
            if ((all_copy.containsKey(placeEntered) && firstuse == 1) || (all_copy.containsKey(placeEntered) && temp == x)) {
                String place = all_copy.get(placeEntered);
                player2_place.setText(place);
                place_to_be_passed2 = placeEntered;
                all_copy.remove(placeEntered);
                keys.remove(placeEntered);
                x = placeEntered.charAt(placeEntered.length() - 1);
                firstuse = 0;
                player1_edit.setHint("Enter place from '" + x.toString()+"'");
                player2_edit.setText("");
                turnindicator.setText("Player 1's turn");
                player1_submit.setEnabled(true);
                player1_edit.setEnabled(true);
                player2_submit.setEnabled(false);
                player2_edit.setEnabled(false);
            }
            else if (!all_copy.containsKey(placeEntered) || temp != x) {
                Toast.makeText(this, "Place invalid or repeated, Hence Player 1 Wins!", Toast.LENGTH_LONG).show();
                onStart(null);
            }
        }
    }


    public void player1map(View view){
        Toast.makeText(this, "player1 clicked", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MultiplayerActivity.this,MapsActivity.class);
        intent.putExtra("trial",place_to_be_passed1);
        startActivity(intent);

    }
    public void player2map(View view){
        Toast.makeText(this, "player2 clicked", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MultiplayerActivity.this,MapsActivity.class);
        intent.putExtra("trial",place_to_be_passed2);
        startActivity(intent);
    }


    //core methods

    public boolean onStart(View view) {
        player1turn = random.nextBoolean();
        player1_place.setText("");
        player2_place.setText("");
        player1_edit.setHint("");
        player2_edit.setHint("");
        player1_edit.setText("");
        player2_edit.setText("");
        firstuse=1;
        all_copy.putAll(all_place);
        for(Map.Entry<String, String> t : all_place.entrySet()) {
            keys.add(t.getKey());
        }
        if (player1turn) {
            turnindicator.setText("Player 1's turn!");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MultiplayerActivity.this, "Player 1's turn",Toast.LENGTH_SHORT).show();
                }
            }, 2000);
            player1_edit.setHint("Enter a random place");
            player2_submit.setEnabled(false);
            player2_edit.setEnabled(false);
        } else {
            turnindicator.setText("Player 2's turn");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MultiplayerActivity.this, "Player 2's Turn",Toast.LENGTH_SHORT).show();
                }
            }, 2000);
            player2_edit.setHint("Enter a random place");
            player1_submit.setEnabled(false);
            player1_edit.setEnabled(false);
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        Intent intent = getIntent();
        intent.getIntExtra("mode",2);
        player1_place=(TextView)findViewById(R.id.player1_place);
        player2_place=(TextView)findViewById(R.id.player2_place);
        turnindicator=(TextView)findViewById(R.id.turnindicator);
        player1_edit=(EditText)findViewById(R.id.player1_edittext);
        player2_edit=(EditText)findViewById(R.id.player2_edittext);
        player1_submit=(Button)findViewById(R.id.player1_submit);
        player2_submit=(Button)findViewById(R.id.player2_submit);
        Log.i("Edit text : ",player2_edit.getHint().toString());
        AssetManager assetManager = getAssets();
        try{
            inputStream = assetManager.open("atlas.txt");
            in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = in.readLine()) != null) {
                IncomingPlace = line.trim();
                all_place.put(IncomingPlace.toLowerCase(), IncomingPlace);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        onStart(null);
    }
}
