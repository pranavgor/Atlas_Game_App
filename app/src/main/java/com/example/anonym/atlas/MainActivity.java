package com.example.anonym.atlas;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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


public class MainActivity extends AppCompatActivity{

    private String IncomingPlace,placeEntered,final_place_to_be_passed;
    Random random=new Random();
    ArrayList<String> keys = new ArrayList<String>();
    LinkedHashMap<String,String> all_place = new LinkedHashMap<>(46354);
    LinkedHashMap<String,String> all_copy = new LinkedHashMap<>(46354);
    TextView comp_place,turn_indicator;
    TextView user_place;
    EditText enterplace;
    int firstuse=0;
    Character x = '#';
    private boolean userTurn = false;
    InputStream inputStream;
    BufferedReader in;

    public void redirectToMaps(View view) {
        if (user_place.getText().toString() == "" && comp_place.getText().toString() == "") {

            Toast.makeText(this, "Please enter the place name first, in order to look up on the map", Toast.LENGTH_LONG).show();

        } else {
            Intent intent1 = new Intent(MainActivity.this, MapsActivity.class);
            intent1.putExtra("trial", final_place_to_be_passed);
            startActivity(intent1);
        }
    }

    public void submitplace(View view) {

        if (TextUtils.isEmpty(enterplace.getText().toString())) {
            Log.i("EnterPlace =",enterplace.getText().toString());
            Toast.makeText(this, "Please enter a name of the place first.", Toast.LENGTH_LONG).show();
        }
        else{
            placeEntered = enterplace.getText().toString().toLowerCase();
            Character temp = placeEntered.charAt(0);
            Log.d("user-place index 0 : ", temp.toString());
            Log.d("comp-place index end : ", x.toString());
            Log.d("firstuse : ", String.valueOf(firstuse));

            if ((all_copy.containsKey(placeEntered) && firstuse == 1) || (all_copy.containsKey(placeEntered) && temp == x)){
                String place = all_copy.get(placeEntered);
                user_place.setText(place);
                final_place_to_be_passed = placeEntered;
                all_copy.remove(placeEntered);
                keys.remove(placeEntered);
                firstuse=0;
                computerturn();
            } else if (!all_copy.containsKey(placeEntered) || temp != x) {
                Toast.makeText(this, "Computer challenged, place invalid or repeated, Hence Computer Wins!", Toast.LENGTH_LONG).show();
                onStart(null);
            }
        }
    }

    public void computerturn(){
        int flag=0;
        Character initial=null;
        String input;
        Log.d("input of user : ",user_place.getText().toString());
        if (firstuse==1)
        {
            int no = random.nextInt(keys.size())-1;
            String temp = all_copy.get(keys.get(no));
            final_place_to_be_passed = keys.get(no);
            comp_place.setText(temp);
            Log.d("Output of computer : ",temp);
            enterplace.setText("");
            x = keys.get(no).charAt(temp.length()-1);
            enterplace.setHint("Enter place from "+x.toString()+"... ");
            firstuse=0;
            all_copy.remove(keys.get(no));
            keys.remove(no);
        }
        else
        {
            turn_indicator.setText("Computer Played!");
            input = user_place.getText().toString().toLowerCase();
            initial = input.charAt(input.length()-1);
            String result;
            int low=0;
            int high = keys.size()-1;

            while(low<=high)
            {
                int mid=(low+high)/2;
                String getword = keys.get(mid);
                Log.d("Getword = ",String.valueOf(getword));
                if(mid==keys.size())
                    break;
                Character test = keys.get(mid).toLowerCase().charAt(0);
                Log.d("Info:Initial of place",test.toString());
                if(test==initial)
                {
                    result = all_copy.get(keys.get(mid));
                    final_place_to_be_passed = keys.get(mid);
                    comp_place.setText(result);
                    Log.d("Output : ",result);
                    enterplace.setText("");
                    x = keys.get(mid).charAt(result.length()-1);
                    enterplace.setHint("Enter a place from "+x.toString());
                    flag=1;
                    all_copy.remove(keys.get(mid));
                    keys.remove(mid);
                    break;
                }
                else if(test < initial) {
                    low = mid + 1;
                    Log.d("Info: low val ", String.valueOf(low));
                }
                else {
                    high = mid - 1;
                    Log.d("Info: high val ", String.valueOf(high));
                }
            }
            if(flag==0){
                String print = String.valueOf(initial);
                Toast.makeText(this,"Computer couldn't find any place from"+print.toUpperCase()+"You win!!",Toast.LENGTH_LONG).show();
                onStart(null);
            }
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                turn_indicator.setText("Your Turn !");
            }
        }, 1000);
        Log.d("All_Place size : ",String.valueOf(all_place.size()));
        Log.d("All_Copy size : ",String.valueOf(all_copy.size()));
    }

    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        user_place.setText("");
        comp_place.setText("");
        firstuse=1;
        all_copy.putAll(all_place);
        for(Map.Entry<String, String> t : all_place.entrySet()) {
            keys.add(t.getKey());
        }
        enterplace.setText("");
        enterplace.setHint("Enter a place here");
        if (userTurn) {
            turn_indicator.setText("Your turn!");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Your Turn",Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        } else {
            turn_indicator.setText("Computer played first!");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Computer's Turn",Toast.LENGTH_SHORT).show();
                }
            }, 2000);

            computerturn();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode",0);
        comp_place = (TextView) findViewById(R.id.comp_place);
        user_place = (TextView) findViewById(R.id.player2_place);
        turn_indicator = (TextView) findViewById(R.id.turn_indicator);
        enterplace = (EditText)findViewById(R.id.enterplace);
        Log.i("Mode : ",String.valueOf(mode));
        AssetManager assetManager = getAssets();
        try {
            if (mode == 1)
                inputStream = assetManager.open("atlas.txt");
            else
                inputStream = assetManager.open("just_countries.txt");
            in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = in.readLine()) != null) {
                IncomingPlace = line.trim();
                all_place.put(IncomingPlace.toLowerCase(), IncomingPlace);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Could not load the 'Places' Directory.", Toast.LENGTH_LONG).show();
        }
        onStart(null);
    }
}


