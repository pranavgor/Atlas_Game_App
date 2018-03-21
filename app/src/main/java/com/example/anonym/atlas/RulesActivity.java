package com.example.anonym.atlas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RulesActivity extends AppCompatActivity {
    ImageView hardimage,easyimage,multiplayerimage;
    TextView rules_list;

    public void response_easy(View view){
        Toast.makeText(this, "easy mode selected", Toast.LENGTH_SHORT).show();
        Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        easyimage.startAnimation(animFadein);
        Intent intent=new Intent(RulesActivity.this,MainActivity.class);
        intent.putExtra("mode",0);
        startActivity(intent);
    }
    public void response_hard(View view){
        Toast.makeText(this, "hard mode selected", Toast.LENGTH_SHORT).show();
        Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        hardimage.startAnimation(animFadein);
        Intent intent=new Intent(RulesActivity.this,MainActivity.class);
        intent.putExtra("mode",1);
        startActivity(intent);
    }
    public void response_multiplayer(View view){
        Toast.makeText(this, "Multiplayer mode selected", Toast.LENGTH_SHORT).show();
        Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        multiplayerimage.startAnimation(animFadein);
        Intent intent=new Intent(RulesActivity.this,MultiplayerActivity.class);
        intent.putExtra("mode",2);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Intent intent=getIntent();
        hardimage=(ImageView)findViewById(R.id.hard_image);
        easyimage=(ImageView)findViewById(R.id.easy_image);
        multiplayerimage=(ImageView)findViewById(R.id.multiplayer_image);
        rules_list=(TextView)findViewById(R.id.rules_list);
        rules_list.setText(Html.fromHtml(getString(R.string.names)));

    }
}
