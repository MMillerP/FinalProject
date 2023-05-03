package com.example.finalproject;

// this activity manages settings

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.fsDB.factoriesDatabase;
import com.example.finalproject.rDB.resourcesDatabase;

public class FourthActivity extends AppCompatActivity {

    int currentMoney = 1;
    int currentTurn = 1;
    int turnMax = 1;
    int currentPop = 1;
    Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentMoney = extras.getInt("money");
            currentTurn = extras.getInt("turn");
            turnMax = extras.getInt("max");
            currentPop = extras.getInt("pop");
        }

        Toolbar myToolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(myToolbar);

        quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronized (ThirdActivity.class){
                    factoriesDatabase.destroy();
                    resourcesDatabase.destroy();
                    System.exit(0);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("money",currentMoney);
        outState.putInt("turn",currentTurn);
        outState.putInt("pop",currentPop);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        currentMoney = savedInstanceState.getInt("money");
        currentTurn = savedInstanceState.getInt("turn");
        currentPop = savedInstanceState.getInt("pop");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.settings_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_factory:
                Intent intent1 = new Intent(this,MainActivity.class);
                intent1.putExtra("money",currentMoney);
                intent1.putExtra("turn",currentTurn);
                intent1.putExtra("max",turnMax);
                intent1.putExtra("pop",currentPop);
                startActivity(intent1);
                return true;

            case R.id.action_resources:
                Intent intent2 = new Intent(this,SecondActivity.class);
                intent2.putExtra("money",currentMoney);
                intent2.putExtra("turn",currentTurn);
                intent2.putExtra("max",turnMax);
                intent2.putExtra("pop",currentPop);
                startActivity(intent2);
                return true;
            case R.id.action_statistics:
                Intent intent3 = new Intent(this,ThirdActivity.class);
                intent3.putExtra("money",currentMoney);
                intent3.putExtra("turn",currentTurn);
                intent3.putExtra("max",turnMax);
                intent3.putExtra("pop",currentPop);
                startActivity(intent3);
                return true;
            case R.id.action_settings:
                Intent intent4 = new Intent(this, FourthActivity.class);
                intent4.putExtra("money",currentMoney);
                intent4.putExtra("turn",currentTurn);
                intent4.putExtra("max",turnMax);
                intent4.putExtra("pop",currentPop);
                startActivity(intent4);
            default:
                return true;
        }
    }
}
