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
                String popString = "Turn " + Integer.toString(currentTurn);
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
            }
        });

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
