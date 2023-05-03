package com.example.finalproject;

// this activity manages resource_view

import android.content.Context;
import android.content.Intent;
import android.icu.number.Precision;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.rDB.DefaultContentResource;
import com.example.finalproject.rDB.resourcesDatabase;
import com.example.finalproject.rDB.resource;
import com.example.finalproject.rDB.resourcesViewModel;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    int rID = 0;
    String rTag = "NON";
    int rPP = 0;
    int rPC = 0;
    int rA1P = 0;
    int rA1C = 0;
    int rA2P = 0;
    int rA2C = 0;
    int rA3P = 0;
    int rA3C = 0;

    int currentTurn = 1;
    int turnMax = 120;

    int currentMoney = 10000;
    int currentID = -1;
    int currentPop = 100000;

    TextView resourceTV;
    TextView resourcePriceTV;
    TextView resourceProducerTV;
    TextView resourceDemandTV;
    TextView moneyTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_view);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentMoney = extras.getInt("money");
            currentTurn = extras.getInt("turn");
            turnMax = extras.getInt("max");
            currentPop = extras.getInt("pop");
        }

        Toolbar myToolbar = findViewById(R.id.resourceToolbar);
        setSupportActionBar(myToolbar);


        resourceTV = findViewById(R.id.resourceText);
        resourcePriceTV = findViewById(R.id.resourcePriceText);
        resourceProducerTV = findViewById(R.id.resourceProducerText);
        resourceDemandTV = findViewById(R.id.resourceDemandText);
        moneyTV = findViewById(R.id.resourceMoneyView);

        ImageButton[] resources = new ImageButton[12];

        resources[0] = findViewById(R.id.lumberButton);
        resources[1] = findViewById(R.id.ironButton);
        resources[2] = findViewById(R.id.coalButton);
        resources[3] = findViewById(R.id.stringButton);
        resources[4] = findViewById(R.id.steelButton);
        resources[5] = findViewById(R.id.woodButton);
        resources[6] = findViewById(R.id.cementButton);
        resources[7] = findViewById(R.id.clothButton);
        resources[8] = findViewById(R.id.clothesButton);
        resources[9] = findViewById(R.id.furnitureButton);
        resources[10] = findViewById(R.id.foodButton);
        resources[11] = findViewById(R.id.paperButton);

        resources[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 0;
                getRValues();
            }
        });
        resources[0].performClick();
        resources[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 1;
                getRValues();
            }
        });
        resources[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 2;
                getRValues();
            }
        });
        resources[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 3;
                getRValues();
            }
        });
        resources[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 4;
                getRValues();
            }
        });
        resources[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 5;
                getRValues();
            }
        });
        resources[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 6;
                getRValues();
            }
        });
        resources[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 7;
                getRValues();
            }
        });
        resources[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 8;
                getRValues();
            }
        });
        resources[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 9;
                getRValues();
            }
        });
        resources[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 10;
                getRValues();
            }
        });
        resources[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 11;
                getRValues();
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

        public void getRValues(){
            resourcesDatabase.getResource(currentID,resource->{
            Bundle args = new Bundle();
            args.putInt("id",resource.id);
            args.putString("tag",resource.tag);
            args.putInt("pp",resource.produceOne);
            args.putInt("pc",resource.consumeOne);
            args.putInt("a1p",resource.produceTwo);
            args.putInt("a1c",resource.consumeTwo);
            args.putInt("a2p",resource.produceThree);
            args.putInt("a2c",resource.consumeThree);
            args.putInt("a3p",resource.consumeFour);
            args.putInt("a3c",resource.consumeFour);
            buildResource(args);
        });
    }
    public void buildResource(Bundle args){
           rID = args.getInt("id");
           rTag = args.getString("tag");
           rPP = args.getInt("pp");
           rPC = args.getInt("pc");
           rA1P = args.getInt("a1p");
           rA1C = args.getInt("a1c");
           rA2P = args.getInt("a2p");
           rA2C = args.getInt("a2c");
           rA3P = args.getInt("a3p");
           rA3C = args.getInt("a3c");
           refreshUI();
        }
    public void refreshUI(){
        String resourceTag = convertTagToString(rTag);
        resourceTV.setText(resourceTag);
        String priceString = "$" + getMarketPrice();
        resourcePriceTV.setText(priceString);
        String produceString = getProduce();
        resourceProducerTV.setText(produceString);
        String demandString = getDemand();
        resourceDemandTV.setText(demandString);
        String moneyString = "$" + Integer.toString(currentMoney);
        moneyTV.setText(moneyString);
    }

    public String convertTagToString(String tag){
        switch (tag) {
            case "LMB": //Lumber Yard
                return "Lumber\n";
            case "IRN": // Iron Mine
                return "Iron\n";
            case "COA": // Coal Mine
                return "Coal\n";
            case "STR": // String Maker
                return "String\n";
            case "STE": // Steel Plant
                return "Steel\n";
            case "WOO": // Wood Mill
                return "Wool\n";
            case "CEM": // Cement Mix
                return "Cement\n";
            case "CLO": // Cloth Mill
                return "Cloth\n";
            case "CLT": // Clothes
                return "Clothes\n";
            case "CAR": // Carpenter
                return "Furniture\n";
            case "FOO": // Food Plant
                return "Food\n";
            case "PAP": // Paper Mill
                return "Paper\n";
            default:
                return "Nothing";
        }
    }

    public String getMarketPrice(){
        double totalDemand = rPC + rA1C + rA2C + rA3C;
        double totalProduce = rPP + rA1P + rA2P + rA3P;
        if(totalProduce ==0){
            String returnValue = String.format("%.1f",DefaultContentResource.startPrices[rID]* totalDemand,1);
            return returnValue;
        }else{
            String returnValue = String.format("%.1f",DefaultContentResource.startPrices[rID] * (totalDemand/totalProduce));
            return returnValue;
        }

    }

    public String getProduce(){
        String returnValue = "";
        if(rPP==0){
            returnValue = returnValue
                    + "You make none.\n";
        } else{
            returnValue = returnValue
                    + "You make " + Integer.toString(rPP) + "\n";
        }
        if(rA1P==0){
            returnValue = returnValue
                    + "Rivertown makes none.\n";
        } else{
            returnValue = returnValue
                    + "Rivertown makes " + Integer.toString(rA1P) + "\n";
        }
        if(rA2P==0){
            returnValue = returnValue
                    + "New Carlisle makes none.\n";
        } else{
            returnValue = returnValue
                    + "New Carlisle makes " + Integer.toString(rA2P) + "\n";
        }
        if(rA3P==0){
            returnValue = returnValue
                    + "Cliffton makes none.\n";
        } else{
            returnValue = returnValue
                    + "Cliffton makes " + Integer.toString(rA3P) + "\n";
        }
        return returnValue;
    }

    public String getDemand(){
        String returnValue = "";
        if(rPC==0){
            returnValue = returnValue
                    + "You consume none.\n";
        } else{
            returnValue = returnValue
                    + "You consume " + Integer.toString(rPC) + "\n";
        }
        if(rA1C==0){
            returnValue = returnValue
                    + "Rivertown consumes none.\n";
        } else{
            returnValue = returnValue
                    + "Rivertown consumes " + Integer.toString(rA1C) + "\n";
        }
        if(rA2C==0){
            returnValue = returnValue
                    + "New Carlisle consumes none.\n";
        } else{
            returnValue = returnValue
                    + "New Carlisle consumes " + Integer.toString(rA2C) + "\n";
        }
        if(rA3C==0){
            returnValue = returnValue
                    + "Cliffton consumes none.\n";
        } else{
            returnValue = returnValue
                    + "Cliffton consumes " + Integer.toString(rA3C) + "\n";
        }
        return returnValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.resource_toolbar, menu);
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
