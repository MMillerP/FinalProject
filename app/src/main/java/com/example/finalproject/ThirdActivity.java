package com.example.finalproject;

// this activity manages statistics_view

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.finalproject.fsDB.factoriesDatabase;
import com.example.finalproject.rDB.DefaultContentResource;
import com.example.finalproject.rDB.resourcesDatabase;
import com.example.finalproject.rDB.resource;
import com.example.finalproject.rDB.resourcesViewModel;

import java.util.Random;


public class ThirdActivity extends AppCompatActivity {

    int currentMoney = 10000;
    int currentTurn = 1;
    int turnMax = 120;
    int currentPop = 100000;

    int currentIncome = -1;
    int currentExpenses = -1;
    int currentProfit = -1;

    TextView moneyTV;
    TextView popTV;
    TextView expensesTV;
    TextView incomeTV;
    TextView turnTV;
    TextView profitTV;

    Button turnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentMoney = extras.getInt("money");
            currentTurn = extras.getInt("turn");
            turnMax = extras.getInt("max");
            currentPop = extras.getInt("pop");
        }

        Toolbar myToolbar = findViewById(R.id.statisticsToolbar);
        setSupportActionBar(myToolbar);

        moneyTV = findViewById(R.id.moneyView);
        String moneyString = "$" + Integer.toString(currentMoney);
        moneyTV.setText(moneyString);
        popTV = findViewById(R.id.statisticsPopView);
        String popString = Integer.toString(currentPop) + " people";
        popTV.setText(popString);
        expensesTV = findViewById(R.id.statisticsExpensesView);
        incomeTV = findViewById(R.id.statisticsIncomeView);
        turnTV = findViewById(R.id.statisticsTurnView);
        String turnString = "Turn " + Integer.toString(currentTurn);
        turnTV.setText(turnString);
        profitTV = findViewById(R.id.statisticsProfitView);
        getIncome();
        getExpenses();


        turnButton = findViewById(R.id.statisticsTurnButton);
        turnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPop = currentPop + 10000;
                String popString = Integer.toString(currentPop) + " people";
                popTV.setText(popString);
                for (int i = 0; i < 12; i++){
                    resourcesDatabase.getResource(i,resource-> { // updates pop consumptions
                        resourcesDatabase.update(new resource(
                                resource.id,
                                resource.tag,
                                resource.produceOne,
                                resource.consumeOne+(10*DefaultContentResource.popConsume[resource.id]),
                                resource.produceTwo,
                                resource.consumeTwo,
                                resource.produceThree,
                                resource.consumeThree,
                                resource.produceFour,
                                resource.consumeFour
                        ));
                    });
                }
                currentTurn = currentTurn + 1;
                currentMoney = currentMoney + currentProfit;
                String turnString = "Turn: " + Integer.toString(currentTurn);
                turnTV.setText(turnString);
                String moneyString = "$" + Integer.toString(currentMoney);
                moneyTV.setText(moneyString);
                if (currentMoney <= 0){
                    endGameConfirmationDialog popup = new endGameConfirmationDialog();
                    popup.show(getSupportFragmentManager(),"end Game notification");
                }
                randomEventConfirmationDialog popup = new randomEventConfirmationDialog();
                popup.show(getSupportFragmentManager(),"random event popup");

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

    public void getIncome(){// gets correct income value - used for UI refreshment
        double[] incomes = new double[12];
        for (int i = 0; i < 12; i++){
            resourcesDatabase.getResource(i,resource-> {
                if((resource.produceOne + resource.produceTwo + resource.produceThree+resource.produceFour)==0){
                    incomes[resource.id]=0;
                }else{
                    double intermediate1 =(resource.consumeOne + resource.consumeTwo + resource.consumeThree + resource.consumeFour);
                    double intermediate2 =(resource.produceOne + resource.produceTwo + resource.produceThree+resource.produceFour);
                incomes[resource.id] += resource.produceOne * (DefaultContentResource.startPrices[resource.id] *
                        (intermediate1/intermediate2));
                }
            });
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int totalIncome = 0;
                Bundle args = new Bundle();
                for (int i = 0;i < 12; i++){
                    totalIncome += incomes[i];
                }
                args.putInt("income",totalIncome);
                getIncomeHelper(args);
            }
        }, 100);

    }

    public void getIncomeHelper(Bundle args){
        String incomeString = "Income: $" + Integer.toString(args.getInt("income"));
        incomeTV.setText(incomeString);
        currentIncome = args.getInt("income");
    }

    public void getExpenses(){//gets correct expenses value - used for UI refreshment
        double[] expenses = new double[12];
        for (int i = 0; i < 12; i++){
            resourcesDatabase.getResource(i,resource-> { // updates new pop consumption values
                if((resource.produceOne + resource.produceTwo + resource.produceThree+resource.produceFour)==0){
                    expenses[resource.id]=DefaultContentResource.startPrices[resource.id]*(resource.consumeOne+resource.consumeTwo+resource.consumeThree+resource.consumeFour);
                }else{
                    double intermediate1 =(resource.consumeOne + resource.consumeTwo + resource.consumeThree + resource.consumeFour);
                    double intermediate2 =(resource.produceOne + resource.produceTwo + resource.produceThree+resource.produceFour);
                    expenses[resource.id] += resource.consumeOne * (DefaultContentResource.startPrices[resource.id] *
                            (intermediate1/intermediate2));
                }
            });
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int totalExpense= 0;
                Bundle args = new Bundle();
                for (int i = 0;i < 12; i++){
                    totalExpense += expenses[i];
                }
                args.putInt("expense",totalExpense);
                getExpensesHelper(args);
            }
        }, 100);


    }

    public void getExpensesHelper(Bundle args){
        String expenseString = "Expenses: $" + Integer.toString(args.getInt("expense"));
        expensesTV.setText(expenseString);
        currentExpenses = args.getInt("expense");
        getProfit();
    }

    public void getProfit(){ // gets profit value from income and expenses
        currentProfit = currentIncome - currentExpenses;
        String profitString = "Profit: $" + Integer.toString(currentProfit);
        profitTV.setText(profitString);
    }

    public void doEndGameLoad(){
        synchronized (ThirdActivity.class){
            factoriesDatabase.destroy();
            resourcesDatabase.destroy();
            System.exit(0);
        }

    }

    public void doRandomEventLoad(final int player, final int type, final int pc, final int amt){
        // player two
        if(player == 0){// pc == 0 is produce,
            if(pc==0){
                resourcesDatabase.getResource(type,resource -> {
                    resourcesDatabase.update(new resource(
                            type,
                            resource.tag,
                            resource.produceOne,
                            resource.consumeOne,
                            resource.produceTwo+amt,
                            resource.consumeTwo,
                            resource.produceThree,
                            resource.consumeThree,
                            resource.produceFour,
                            resource.consumeFour
                    ));
                });
            }else{
                resourcesDatabase.getResource(type,resource -> {
                    resourcesDatabase.update(new resource(
                            type,
                            resource.tag,
                            resource.produceOne,
                            resource.consumeOne,
                            resource.produceTwo,
                            resource.consumeTwo+amt,
                            resource.produceThree,
                            resource.consumeThree,
                            resource.produceFour,
                            resource.consumeFour
                    ));
                });
            }
        }
        // player three
        else if(player ==1){
            if(pc == 0){
                resourcesDatabase.getResource(type,resource -> {
                    resourcesDatabase.update(new resource(
                            type,
                            resource.tag,
                            resource.produceOne,
                            resource.consumeOne,
                            resource.produceTwo,
                            resource.consumeTwo,
                            resource.produceThree+amt,
                            resource.consumeThree,
                            resource.produceFour,
                            resource.consumeFour
                    ));
                });
            }else{
                resourcesDatabase.getResource(type,resource -> {
                    resourcesDatabase.update(new resource(
                            type,
                            resource.tag,
                            resource.produceOne,
                            resource.consumeOne,
                            resource.produceTwo,
                            resource.consumeTwo,
                            resource.produceThree,
                            resource.consumeThree+amt,
                            resource.produceFour,
                            resource.consumeFour
                    ));
                });
            }
        }
        // player four
        else{
            if(pc == 0){
                resourcesDatabase.getResource(type,resource -> {
                    resourcesDatabase.update(new resource(
                            type,
                            resource.tag,
                            resource.produceOne,
                            resource.consumeOne,
                            resource.produceTwo,
                            resource.consumeTwo,
                            resource.produceThree,
                            resource.consumeThree,
                            resource.produceFour+amt,
                            resource.consumeFour
                    ));
                });
            }else{
                resourcesDatabase.getResource(type,resource -> {
                    resourcesDatabase.update(new resource(
                            type,
                            resource.tag,
                            resource.produceOne,
                            resource.consumeOne,
                            resource.produceTwo,
                            resource.consumeTwo,
                            resource.produceThree,
                            resource.consumeThree,
                            resource.produceFour,
                            resource.consumeFour+amt
                    ));
                });
            }
        }
        getIncome();
        getExpenses();
        getProfit();
    }

    public String getFactoryTag(int factoryID) {
        //convert ID to tag
        switch (factoryID) {
            case 1: //Lumber Yard
                return "LMB";
            case 2: // Iron Mine
                return "IRN";
            case 3: // Coal Mine
                return "COA";
            case 4: // String Maker
                return "STR";
            case 5: // Steel Plant
                return "STE";
            case 6: // Wood Mill
                return "WOO";
            case 7: // Cement Mix
                return "CEM";
            case 8: // Cloth Mill
                return "CLO";
            case 9: // Clothes
                return "CLT";
            case 10: // Carpenter
                return "CAR";
            case 11: // Food Plant
                return "FOO";
            case 12: // Paper Mill
                return "PAP";
        }
        return "";
    }

    public static class randomEventConfirmationDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            Random rand = new Random();
            final int player = rand.nextInt(3); // 0 -> 2 ai player
            final int type = rand.nextInt(12); // 0 -> 11 resource type
            final int pc = rand.nextInt(2); // 0 -> 1 produce or consume
            final int amt = rand.nextInt(50) + 1; // 1 -> 50 amount

            String message = "";
            if(player == 0){
                message = message + "Rivertown ";
            }
            else if (player == 1){
                message = message + "New Carlisle ";
            }
            else{
                message = message + "Cliffton ";
            }
            if(pc == 0){
                message = message + "has started to produce ";
            }
            else{
                message = message + "has started to consume ";
            }
            message = message + Integer.toString(amt) + " more ";
            if(type == 0){
                    message = message + "units of lumber.";}
            else if (type == 1){
                    message = message + "units of iron.";}
            else if(type == 2){
                    message = message + "units of coal.";}
            else if(type == 3){
                    message = message + "units of string.";}
            else if(type == 4){
                    message = message + "units of steel.";}
            else if(type == 5){
                    message = message + "units of wood.";}
            else if(type == 6){
                    message = message + "units of cement.";}
            else if(type == 7){
                    message = message + "units of cloth.";}
            else if(type == 8){
                    message = message + "units of clothes.";}
            else if(type == 9){
                    message = message + "units of furniture.";}
            else if(type == 10){
                    message = message + "units of food.";}
            else if(type == 11){
                    message = message + "units of paper.";
            }

            builder.setMessage(message).setPositiveButton("Interesting", (dialog,id)-> ((ThirdActivity) getActivity()).doRandomEventLoad(player,type,pc,amt));
            return builder.create();
        }
    }



    public static class endGameConfirmationDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Your money has reached 0, and you have lost the game.")
                    .setPositiveButton("Quit", (dialog,id)-> ((ThirdActivity) getActivity()).doEndGameLoad());
            return builder.create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.statistics_toolbar, menu);
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
