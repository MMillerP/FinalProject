package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.fsDB.factoriesDatabase;
import com.example.finalproject.fsDB.factorySlot;
import com.example.finalproject.fsDB.factorySlotViewModel;
import com.example.finalproject.rDB.resource;
import com.example.finalproject.rDB.resourcesDatabase;
import com.example.finalproject.rDB.resourcesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

// This activity manages factory_management.xml

public class MainActivity extends AppCompatActivity {
    Spinner factoriesSpinner;
    int currentMoney = 100000;
    static int factoryCost = 1000;
    int currentTurn = 1;
    int turnMax = 120;
    int currentSpinner = -1;
    int currentID = -1;

    int fsID = 0;
    String fsFactory = "N";
    int fsLevel = 0;
    String fsInputTypeOne = "N";
    String fsInputTypeTwo= "N";
    int fsInputOne = 0;
    int fsInputTwo = 0;
    String fsOutputType ="N";
    int fsOutput = 0;
    int currentPop = 100000;

    Button BEButton;
    Button DButton;
    TextView factoryInputTV;
    TextView factoryOutputTV;
    TextView factoryMoneyTV;
    TextView factoryLevelTV;
    TextView moneyTV;
    int exist = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_management);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentMoney = extras.getInt("money");
            currentTurn = extras.getInt("turn");
            turnMax = extras.getInt("max");
            currentPop = extras.getInt("pop");
        }

        Toolbar myToolbar = findViewById(R.id.factory_toolbar);
        setSupportActionBar(myToolbar);

        factorySlotAdapter FSAdapter = new factorySlotAdapter(this);
        factorySlotViewModel factorySlotViewModel = new ViewModelProvider(this).get(com.example.finalproject.fsDB.factorySlotViewModel.class);
        factorySlotViewModel.getAllFactorySlots().observe(this, FSAdapter::setFactories);

        resourceAdapter RAdapter = new resourceAdapter(this);
        resourcesViewModel resourcesViewModel = new ViewModelProvider(this).get(com.example.finalproject.rDB.resourcesViewModel.class);
        resourcesViewModel.getAllResources().observe(this,RAdapter::setResources);

        BEButton = findViewById(R.id.factoryBuildExpandButton);
        DButton = findViewById(R.id.factoryDestroyButton);

        //grabbing text boxes in info screen on factory management page
        factoryInputTV = findViewById(R.id.factoryInputText);
        factoryOutputTV = findViewById(R.id.factoryOutputText);
        factoryMoneyTV = findViewById(R.id.factoryMoneyText);
        factoryLevelTV = findViewById(R.id.factoryLevelText);
        moneyTV = findViewById(R.id.moneyView);
        moneyTV.setText("$"+Integer.toString(currentMoney));

        //grabbing factory buttons on factory management page

        ImageButton[] factories = new ImageButton[12];
        factories[0] = findViewById(R.id.factoryOneButton);
        factories[1] = findViewById(R.id.factoryTwoButton);
        factories[2] = findViewById(R.id.factoryThreeButton);
        factories[3] = findViewById(R.id.factoryFourButton);
        factories[4] = findViewById(R.id.factoryFiveButton);
        factories[5] = findViewById(R.id.factorySixButton);
        factories[6] = findViewById(R.id.factorySevenButton);
        factories[7] = findViewById(R.id.factoryEightButton);
        factories[8] = findViewById(R.id.factoryNineButton);
        factories[9] = findViewById(R.id.factoryTenButton);
        factories[10] = findViewById(R.id.factoryElevenButton);
        factories[11] = findViewById(R.id.factoryTwelveButton);

        // creates button functionality for factory slots
        factories[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 0;
                getFSValues();

            }
        });
        factories[0].performClick();
        factories[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 1;
                getFSValues();
            }
        });
        factories[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 2;
                getFSValues();
            }
        });
        factories[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 3;
                getFSValues();
            }
        });
        factories[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 4;
                getFSValues();
            }
        });
        factories[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 5;
                getFSValues();
            }
        });
        factories[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 6;
                getFSValues();
            }
        });
        factories[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 7;
                getFSValues();
            }
        });
        factories[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 8;
                getFSValues();
            }
        });
        factories[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 9;
                getFSValues();
            }
        });
        factories[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 10;
                getFSValues();
            }
        });
        factories[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentID = 11;
                getFSValues();
            }
        });

        // functionality for bulid/expand button
        BEButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(getFactoryID(fsFactory)==0){ // no factory
                    if(currentSpinner==0){ // spinner is set to No Factory
                        Toast toast = Toast.makeText(getApplicationContext(), "Select a factory to Build.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{ // spinner is set to valid factory
                        if(currentMoney>=factoryCost){
                            BuildConfirmationDialog popup = new BuildConfirmationDialog();
                            popup.show(getSupportFragmentManager(),"Build Confirmation");
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(), "You do not have enough money to Build this factory.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                }else{ // factory built
                    if(currentMoney >= factoryCost){

                        ExpandConfirmationDialog popup = new ExpandConfirmationDialog();
                        popup.show(getSupportFragmentManager(),"Expand Confirmation");
                    } else{
                        Toast toast = Toast.makeText(getApplicationContext(), "You do not have enough money to Expand this factory.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }

            }
        });

        DButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(getFactoryID(fsFactory)==0){ // no factory
                    Toast toast = Toast.makeText(getApplicationContext(), "There is no factory to Destroy here.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else{
                    DestroyConfirmationDialog popup = new DestroyConfirmationDialog();
                    popup.show(getSupportFragmentManager(),"Destroy Confirmation");
                }
            }
        });

        // setting up the spinner on the factory management page.
        factoriesSpinner = findViewById(R.id.factoryBuildSelectSpinner);

        ArrayAdapter<CharSequence> factoriesAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.buildableFactories,
                        android.R.layout.simple_spinner_dropdown_item);
        factoriesSpinner.setAdapter(factoriesAdapter);
        factoriesSpinner.setSelection(0);

        // Changes text on factory management screen based on spinner item selected

        factoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSpinner = i;
                  if(factoriesSpinner.isEnabled()){
                      String moneyText = "REQUIRED FUNDS\n"+Integer.toString(factoryCost)+" Dollars";
                    switch (i) {
                        case 1: //LumberYard
                            factoryInputTV.setText("INPUT\nNo Resources");
                            factoryOutputTV.setText("OUTPUT\n2 Lumber");
                            factoryMoneyTV.setText(moneyText);
                            break;
                        case 2: //Iron Mine
                            factoryInputTV.setText("INPUT\nNo Resources");
                            factoryOutputTV.setText("OUTPUT\n2 Iron");
                            factoryMoneyTV.setText(moneyText);
                            break;
                        case 3: // Coal Mine
                            factoryInputTV.setText("INPUT\nNo Resources");
                            factoryOutputTV.setText("OUTPUT\n2 coal");
                            factoryMoneyTV.setText(moneyText);
                            break;
                        case 4: // String Maker
                            factoryInputTV.setText("INPUT\nNo Resources");
                            factoryOutputTV.setText("OUTPUT\n2 String");
                            factoryMoneyTV.setText(moneyText);
                            break;
                        case 5: // Steel Plant
                        factoryInputTV.setText("INPUT\n1 Iron\n1 Coal");
                        factoryOutputTV.setText("OUTPUT\n4 Steel");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 6: // Wood Plant
                        factoryInputTV.setText("INPUT\n1 Lumber");
                        factoryOutputTV.setText("OUTPUT\n2 Plank");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 7: // Cement Plant
                        factoryInputTV.setText("INPUT\n1 Coal");
                        factoryOutputTV.setText("OUTPUT\n2 Cement");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 8: // Cloth Plant
                        factoryInputTV.setText("INPUT\n1 String");
                        factoryOutputTV.setText("OUTPUT\n2 Cloth");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 9: // Clothes
                        factoryInputTV.setText("INPUT\n1 Cloth");
                        factoryOutputTV.setText("OUTPUT\n2 Clothes");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 10: // Carpenter
                        factoryInputTV.setText("INPUT\n1 Wood\n1 Iron");
                        factoryOutputTV.setText("OUTPUT\n2 Furniture");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 11: // Food Plant
                        factoryInputTV.setText("INPUT\nNo Resources");
                        factoryOutputTV.setText("OUTPUT\n20 Food");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    case 12: // Paper Mill
                        factoryInputTV.setText("INPUT\n1 Lumber");
                        factoryOutputTV.setText("OUTPUT\n2 Paper");
                        factoryMoneyTV.setText(moneyText);
                        break;
                    default: // No Factory
                        factoryInputTV.setText("INPUT\nNo Resources");
                        factoryOutputTV.setText("OUTPUT\nNo Resources");
                        factoryMoneyTV.setText(moneyText);
                        break;
                }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }

    public void updateResourceValues(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.factory_toolbar, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    // updates all ui elements for factory management page
    // exclusively triggers on build, expand, and destroy buttons since the database update takes a moment
    public void refreshUIPass(factorySlot current){
        if(getFactoryID(current.factory)==0){ // No Factory - build
            BEButton.setText("Build");
        }
        else{ // Has a factory - expand
            BEButton.setText("Expand");
        }

        int factoryID = getFactoryID(current.factory); // sets spinner to correct factory
        if (factoryID==0){ // no factory in slot
            Log.d("RUIP","NoFac");
            factoriesSpinner.setEnabled(true);
            factoriesSpinner.setSelection(0);
            factoryLevelTV.setText(getLevelText(current.level));
        }
        else{ // preexisting factory in slot
            Log.d("RUIP","Fac");
            String moneyText = "REQUIRED FUNDS TO EXPAND\n" + Integer.toString(factoryCost) + " Dollars";
            factoriesSpinner.setEnabled(false);
            factoriesSpinner.setSelection(factoryID);
            factoryMoneyTV.setText(moneyText);
            factoryLevelTV.setText(getLevelText(current.level));
            String inputText = getInputText(current.inputTypeOne,current.inputTypeTwo,current.inputOne,current.inputTwo);
            factoryInputTV.setText(inputText);
            String outputText = getOutputText(current.outputType,current.output);
            factoryOutputTV.setText(outputText);
        }
        Log.d("RUIP","done");
    }

    public void getFSValues(){
        factoriesDatabase.getFactorySlot(currentID, factorySlot -> {
            Bundle args = new Bundle();
            args.putInt("id", factorySlot.id);
            args.putString("factory", factorySlot.factory);
            args.putInt("level", factorySlot.level);
            args.putString("inputTypeOne", factorySlot.inputTypeOne);
            args.putString("inputTypeTwo", factorySlot.inputTypeTwo);
            args.putInt("inputOne", factorySlot.inputOne);
            args.putInt("inputTwo", factorySlot.inputTwo);
            args.putString("outputType", factorySlot.outputType);
            args.putInt("output", factorySlot.output);
            buildFactoryCur(args);
        });
    }

    public void buildFactoryCur (Bundle args){
        // updates fs values
        fsID = args.getInt("id");
        fsFactory = args.getString("factory");
        fsLevel = args.getInt("level");
        fsInputTypeOne = args.getString("inputTypeOne");
        fsInputTypeTwo = args.getString("inputTypeTwo");
        fsInputOne = args.getInt("inputOne");
        fsInputTwo = args.getInt("inputTwo");
        fsOutputType = args.getString("outputType");
        fsOutput = args.getInt("output");
        refreshUI();
    }

    public void refreshUI(){
        // updates all ui elements for factory management page
        if(getFactoryID(fsFactory)==0){ // No Factory - build
            BEButton.setText("Build");
        }
        else{ // Has a factory - expand
            BEButton.setText("Expand");
        }

        int factoryID = getFactoryID(fsFactory); // sets spinner to correct factory
        if (factoryID==0){ // no factory in slot
            Log.d("RUI","NoFac");
            factoriesSpinner.setEnabled(true);
            factoriesSpinner.setSelection(0);
            factoryLevelTV.setText(getLevelText(fsLevel));
        }
        else{ // preexisting factory in slot
            Log.d("RUI","Fac");
            String moneyText = "REQUIRED FUNDS TO EXPAND\n" + Integer.toString(factoryCost) + " Dollars";
            factoriesSpinner.setEnabled(false);
            factoriesSpinner.setSelection(factoryID);
            factoryMoneyTV.setText(moneyText);
            factoryLevelTV.setText(getLevelText(fsLevel));
            String inputText = getInputText(fsInputTypeOne,fsInputTypeTwo,fsInputOne,fsInputTwo);
            factoryInputTV.setText(inputText);
            String outputText = getOutputText(fsOutputType,fsOutput);
            factoryOutputTV.setText(outputText);
        }
        Log.d("RUI",Integer.toString(fsLevel));

    }

    public void getFSValuesPass(){
        factoriesDatabase.getFactorySlot(currentID, factorySlot -> {
            Bundle args = new Bundle();
            args.putInt("id", factorySlot.id);
            args.putString("factory", factorySlot.factory);
            args.putInt("level", factorySlot.level);
            args.putString("inputTypeOne", factorySlot.inputTypeOne);
            args.putString("inputTypeTwo", factorySlot.inputTypeTwo);
            args.putInt("inputOne", factorySlot.inputOne);
            args.putInt("inputTwo", factorySlot.inputTwo);
            args.putString("outputType", factorySlot.outputType);
            args.putInt("output", factorySlot.output);
            buildFactoryCurPass(args);
        });
    }

    public void buildFactoryCurPass (Bundle args){
        // updates fs values
        fsID = args.getInt("id");
        fsFactory = args.getString("factory");
        fsLevel = args.getInt("level");
        fsInputTypeOne = args.getString("inputTypeOne");
        fsInputTypeTwo = args.getString("inputTypeTwo");
        fsInputOne = args.getInt("inputOne");
        fsInputTwo = args.getInt("inputTwo");
        fsOutputType = args.getString("outputType");
        fsOutput = args.getInt("output");
    }

    public factorySlot generateFactorySlot(boolean destroy){
        // generates new factory slot entity, used to update when upgrading or destroying
        if (destroy){
            return new factorySlot(fsID, "NON", 0, "NON","NON",0,0,"NON",0);
        } else {
            String tag = getFactoryTag(currentSpinner);
            String[] typeInfo = getFactoryStringInfo(tag);
            int[] amt = getFactoryIntInfo(tag, fsLevel + 1);
            return new factorySlot(fsID, tag, fsLevel + 1, typeInfo[0], typeInfo[1], amt[0], amt[1], typeInfo[2], amt[2]);
        }
    }

    public void updateResourceValues(factorySlot newFactory, factorySlot oldFactory, boolean destroy){ // updates resource database after changes to factory
        int NewInputOne = newFactory.inputOne;
        int NewInputTwo = newFactory.inputTwo;
        int NewOutput = newFactory.output;
        String NewInputTOne = newFactory.inputTypeOne;
        String NewInputTTwo = newFactory.inputTypeTwo;
        String NewOutputT = newFactory.outputType;

        if(destroy){ // factory is being destroyed
            int inputTypeOneID = getFactoryID(oldFactory.inputTypeOne);
            int inputTypeTwoID = getFactoryID(oldFactory.inputTypeTwo);
            int outputTypeID = getFactoryID(oldFactory.outputType);

            if(inputTypeOneID!=0) {
                resourcesDatabase.getResource(inputTypeOneID-1, resource -> {
                    Bundle args = new Bundle();
                    args.putInt("id", resource.id);
                    args.putString("tag", resource.tag);
                    args.putInt("pp", resource.produceOne);
                    args.putInt("pc", resource.consumeOne);
                    args.putInt("a1p", resource.produceTwo);
                    args.putInt("a1c", resource.consumeTwo);
                    args.putInt("a2p", resource.produceThree);
                    args.putInt("a2c", resource.consumeThree);
                    args.putInt("a3p", resource.consumeFour);
                    args.putInt("a3c", resource.consumeFour);
                    updateResourceValuesHelperDestroyConsume(args, oldFactory.factory, oldFactory.level);
                });
            }
            if(inputTypeTwoID!=0){
                resourcesDatabase.getResource(inputTypeTwoID-1, resource -> {
                    Bundle args = new Bundle();
                    args.putInt("id", resource.id);
                    args.putString("tag", resource.tag);
                    args.putInt("pp", resource.produceOne);
                    args.putInt("pc", resource.consumeOne);
                    args.putInt("a1p", resource.produceTwo);
                    args.putInt("a1c", resource.consumeTwo);
                    args.putInt("a2p", resource.produceThree);
                    args.putInt("a2c", resource.consumeThree);
                    args.putInt("a3p", resource.consumeFour);
                    args.putInt("a3c", resource.consumeFour);
                    updateResourceValuesHelperDestroyConsume(args, oldFactory.factory, oldFactory.level);
                });
            }
            if(outputTypeID!=0){
                resourcesDatabase.getResource(outputTypeID-1, resource -> {
                    Bundle args = new Bundle();
                    args.putInt("id", resource.id);
                    args.putString("tag", resource.tag);
                    args.putInt("pp", resource.produceOne);
                    args.putInt("pc", resource.consumeOne);
                    args.putInt("a1p", resource.produceTwo);
                    args.putInt("a1c", resource.consumeTwo);
                    args.putInt("a2p", resource.produceThree);
                    args.putInt("a2c", resource.consumeThree);
                    args.putInt("a3p", resource.consumeFour);
                    args.putInt("a3c", resource.consumeFour);
                    updateResourceValuesHelperDestroyProduce(args, oldFactory.factory, oldFactory.level);
                });
            }

        } else{// factory is being built/expanded
            int inputTypeOneID = getFactoryID(newFactory.inputTypeOne);
            int inputTypeTwoID = getFactoryID(newFactory.inputTypeTwo);
            int outputTypeID = getFactoryID(newFactory.outputType);

            if(inputTypeOneID!=0) { //input one
                resourcesDatabase.getResource(inputTypeOneID-1, resource -> {
                    Bundle args = new Bundle();
                    args.putInt("id", resource.id);
                    args.putString("tag", resource.tag);
                    args.putInt("pp", resource.produceOne);
                    args.putInt("pc", resource.consumeOne);
                    args.putInt("a1p", resource.produceTwo);
                    args.putInt("a1c", resource.consumeTwo);
                    args.putInt("a2p", resource.produceThree);
                    args.putInt("a2c", resource.consumeThree);
                    args.putInt("a3p", resource.consumeFour);
                    args.putInt("a3c", resource.consumeFour);
                    updateResourceValuesHelperBuildConsume(args, newFactory.factory, newFactory.level);
                });
            }

            if(inputTypeTwoID!=0) { // input two
                resourcesDatabase.getResource(inputTypeTwoID-1, resource -> {
                    Bundle args = new Bundle();
                    args.putInt("id", resource.id);
                    args.putString("tag", resource.tag);
                    args.putInt("pp", resource.produceOne);
                    args.putInt("pc", resource.consumeOne);
                    args.putInt("a1p", resource.produceTwo);
                    args.putInt("a1c", resource.consumeTwo);
                    args.putInt("a2p", resource.produceThree);
                    args.putInt("a2c", resource.consumeThree);
                    args.putInt("a3p", resource.consumeFour);
                    args.putInt("a3c", resource.consumeFour);
                    updateResourceValuesHelperBuildConsume(args, newFactory.factory, newFactory.level);
                });
            }

            if(outputTypeID!=0){//output
                resourcesDatabase.getResource(outputTypeID-1, resource -> {
                    Bundle args = new Bundle();
                    args.putInt("id", resource.id);
                    args.putString("tag", resource.tag);
                    args.putInt("pp", resource.produceOne);
                    args.putInt("pc", resource.consumeOne);
                    args.putInt("a1p", resource.produceTwo);
                    args.putInt("a1c", resource.consumeTwo);
                    args.putInt("a2p", resource.produceThree);
                    args.putInt("a2c", resource.consumeThree);
                    args.putInt("a3p", resource.consumeFour);
                    args.putInt("a3c", resource.consumeFour);
                    updateResourceValuesHelperBuildProduce(args, newFactory.factory, newFactory.level);
                });
            }
        }
    }

    public void updateResourceValuesHelperDestroyConsume(Bundle args, String tag, int level){
        resourcesDatabase.update(new resource(
                args.getInt("id"),
                args.getString("tag"),
                args.getInt("pp"),
                (args.getInt("pc") - (getFactoryIntInfo(tag,level)[0]) + (getFactoryIntInfo(tag,level-1)[0])),// new consumption amount for player
                args.getInt("a1p"),
                args.getInt("a1c"),
                args.getInt("a2p"),
                args.getInt("a2c"),
                args.getInt("a3p"),
                args.getInt("a3c")));
    }

    public void updateResourceValuesHelperDestroyProduce(Bundle args, String tag, int level){
        resourcesDatabase.update(new resource(
                args.getInt("id"),
                args.getString("tag"),
                (args.getInt("pp") - (getFactoryIntInfo(tag,level)[2]) + (getFactoryIntInfo(tag,level-1)[2])), // new production amount for player
                args.getInt("pc"),// new consumption amount for player
                args.getInt("a1p"),
                args.getInt("a1c"),
                args.getInt("a2p"),
                args.getInt("a2c"),
                args.getInt("a3p"),
                args.getInt("a3c")));
    }

    public void updateResourceValuesHelperBuildConsume(Bundle args,String tag,int level){
        resourcesDatabase.update(new resource(
                args.getInt("id"),
                args.getString("tag"),
                args.getInt("pp"),
                (args.getInt("pc") + (getFactoryIntInfo(tag,level)[0]) - (getFactoryIntInfo(tag,level-1)[0])),// new consumption amount for player
                args.getInt("a1p"),
                args.getInt("a1c"),
                args.getInt("a2p"),
                args.getInt("a2c"),
                args.getInt("a3p"),
                args.getInt("a3c")));
    }

    public void updateResourceValuesHelperBuildProduce(Bundle args, String tag, int level){
            resourcesDatabase.update(new resource(
                    args.getInt("id"),
                    args.getString("tag"),
                    (args.getInt("pp") + (getFactoryIntInfo(tag,level)[2]) - (getFactoryIntInfo(tag,level-1)[2])), // new production amount for player
                    args.getInt("pc"),// new consumption amount for player
                    args.getInt("a1p"),
                    args.getInt("a1c"),
                    args.getInt("a2p"),
                    args.getInt("a2c"),
                    args.getInt("a3p"),
                    args.getInt("a3c")));
        }


    public int[] getFactoryIntInfo(String tag, int level){

        int[] amt = {0,0,0}; //input one, input two, output
        switch(tag){
            case "LMB": //Lumber Yard
                amt[0] = 0*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "IRN": // Iron Mine
                amt[0] = 0*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "COA": // Coal Mine
                amt[0] = 0*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "STR": // String Maker
                amt[0] = 0*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "STE": // Steel Plant
                amt[0] = 1*level;
                amt[1] = 1*level;
                amt[2] = 4*level;
                return amt;
            case "WOO": // Wood Mill
                amt[0] = 1*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "CEM": // Cement Mix
                amt[0] = 1*level;
                amt[1] = 1*level;
                amt[2] = 4*level;
                return amt;
            case "CLO": // Cloth Mill
                amt[0] = 1*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "CLT": // Clothes
                amt[0] = 1*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            case "CAR": // Carpenter
                amt[0] = 1*level;
                amt[1] = 1*level;
                amt[2] = 4*level;
                return amt;
            case "FOO": // Food Plant
                amt[0] = 0*level;
                amt[1] = 0*level;
                amt[2] = 20*level;
                return amt;
            case "PAP": // Paper Mill
                amt[0] = 1*level;
                amt[1] = 0*level;
                amt[2] = 2*level;
                return amt;
            default: // No Factory
                amt[0] = 0*level;
                amt[1] = 0*level;
                amt[2] = 0*level;
                return amt;
        }
    }

    public String[] getFactoryStringInfo(String tag){
        String[] typeInfo = new String[3];
        switch(tag){
                case "LMB": //Lumber Yard
                    typeInfo[0]="NON";
                    typeInfo[1]="NON";
                    typeInfo[2]="LMB";
                    return typeInfo;
                case "IRN": // Iron Mine
                    typeInfo[0]="NON";
                    typeInfo[1]="NON";
                    typeInfo[2]="IRN";
                    return typeInfo;
                case "COA": // Coal Mine
                    typeInfo[0]="NON";
                    typeInfo[1]="NON";
                    typeInfo[2]="COA";
                    return typeInfo;
                case "STR": // String Maker
                    typeInfo[0]="NON";
                    typeInfo[1]="NON";
                    typeInfo[2]="STR";
                    return typeInfo;
                case "STE": // Steel Plant
                    typeInfo[0]="IRN";
                    typeInfo[1]="COA";
                    typeInfo[2]="STE";
                    return typeInfo;
                case "WOO": // Wood Mill
                    typeInfo[0]="LMB";
                    typeInfo[1]="NON";
                    typeInfo[2]="WOO";
                    return typeInfo;
                case "CEM": // Cement Mix
                    typeInfo[0]="IRN";
                    typeInfo[1]="COA";
                    typeInfo[2]="CEM";
                    return typeInfo;
                case "CLO": // Cloth Mill
                    typeInfo[0]="STR";
                    typeInfo[1]="NON";
                    typeInfo[2]="CLO";
                    return typeInfo;
                case "CLT": // Clothes
                    typeInfo[0]="CLO";
                    typeInfo[1]="NON";
                    typeInfo[2]="CLT";
                    return typeInfo;
                case "CAR": // Carpenter
                    typeInfo[0]="STE";
                    typeInfo[1]="WOO";
                    typeInfo[2]="CAR";
                    return typeInfo;
                case "FOO": // Food Plant
                    typeInfo[0]="NON";
                    typeInfo[1]="NON";
                    typeInfo[2]="FOO";
                    return typeInfo;
                case "PAP": // Paper Mill
                    typeInfo[0]="LMB";
                    typeInfo[1]="NON";
                    typeInfo[2]="PAP";
                    return typeInfo;
                default: // No Factory
                    typeInfo[0]="NON";
                    typeInfo[1]="NON";
                    typeInfo[2]="NON";
                    return typeInfo;
            }
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
            default: // No Factory
                return "NON";
        }
    }

    public int getFactoryID (String factoryTag){
            //converts DB String tag to Spinner int ID
            switch (factoryTag) {
                case "LMB": //Lumber Yard
                    return 1;
                case "IRN": // Iron Mine
                    return 2;
                case "COA": // Coal Mine
                    return 3;
                case "STR": // String Maker
                    return 4;
                case "STE": // Steel Plant
                    return 5;
                case "WOO": // Wood Mill
                    return 6;
                case "CEM": // Cement Mix
                    return 7;
                case "CLO": // Cloth Mill
                    return 8;
                case "CLT": // Clothes
                    return 9;
                case "CAR": // Carpenter
                    return 10;
                case "FOO": // Food Plant
                    return 11;
                case "PAP": // Paper Mill
                    return 12;
                default: // No Factory
                    return 0;

            }
        }
    public String getStringFromPutType (String input){
            switch (input) {
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
                    return "CAR\n";
                case "FOO": // Food Plant
                    return "Food\n";
                case "PAP": // Paper Mill
                    return "Paper\n";
                default:
                    return "Nothing";
            }
        }
    public String getLevelText ( int level){
        if (level>0) {
            return "LEVEL " + Integer.toString(level);
        }
        else{
            return "No Factory Built";
        }
    }

    public String getInputText (String inputTypeOne, String inputTypeTwo,int inputOne, int inputTwo){
        String returnValue = "";

            if (inputOne > 0) {
                returnValue = "INPUT\n"
                        + Integer.toString(inputOne)
                        + " "
                        + getStringFromPutType(inputTypeOne);
                        if(inputTwo > 0) {
                            returnValue = returnValue
                                    + Integer.toString(inputTwo)
                                    + " "
                                    + getStringFromPutType(inputTypeTwo);
                        }
                return returnValue;
            } else {
                return "No Input";
            }
    }

    public String getOutputText (String outputType,int output){
        String returnValue = "";
            if (output > 0) {
                returnValue = "OUTPUT\n"
                        + Integer.toString(output)
                        + " "
                        + getStringFromPutType(outputType);
                return returnValue;
            } else {
                return "No Output";
            }
        }

    public void doBuildLoad(){
        factorySlot tempFac = generateFactorySlot(false);
        factorySlot oldFac = new factorySlot(fsID,fsFactory,fsLevel,fsInputTypeOne,fsInputTypeTwo,fsInputOne,fsInputTwo,fsOutputType,fsOutput);
        updateResourceValues(tempFac,oldFac,false);
        factoriesDatabase.update(tempFac);
        currentMoney-= factoryCost;
        refreshUIPass(tempFac);
        getFSValuesPass();
        moneyTV.setText("$"+Integer.toString(currentMoney));
    }

    public static class BuildConfirmationDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you would like to Build this building?")
                    .setPositiveButton("Build", (dialog,id)-> ((MainActivity) getActivity()).doBuildLoad())
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            return builder.create();
        }
    }

    public void doExpandLoad(){
        factorySlot tempFac = generateFactorySlot(false);
        factorySlot oldFac = new factorySlot(fsID,fsFactory,fsLevel,fsInputTypeOne,fsInputTypeTwo,fsInputOne,fsInputTwo,fsOutputType,fsOutput);
        updateResourceValues(tempFac,oldFac,false);
        factoriesDatabase.update(tempFac);
        currentMoney-=factoryCost;
        Log.d("FB",tempFac.factory);
        Log.d("FB",Integer.toString(tempFac.output));
        refreshUIPass(tempFac);
        getFSValuesPass();

        moneyTV.setText("$"+Integer.toString(currentMoney));
    }

    public static class ExpandConfirmationDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you would like to Expand this building?")
                    .setPositiveButton("Expand", (dialog,id)-> ((MainActivity) getActivity()).doExpandLoad())
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            return builder.create();
        }
    }

    public void doDestroyLoad(){
        factorySlot tempFac = generateFactorySlot(true);
        factorySlot oldFac = new factorySlot(fsID,fsFactory,fsLevel,fsInputTypeOne,fsInputTypeTwo,fsInputOne,fsInputTwo,fsOutputType,fsOutput);
        updateResourceValues(tempFac,oldFac,true);
        factoriesDatabase.update(tempFac);
        currentMoney+=(factoryCost*fsLevel)/2;
        refreshUIPass(tempFac);
        getFSValuesPass();

        moneyTV.setText("$"+Integer.toString(currentMoney));
    }

    public static class DestroyConfirmationDialog extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you would like to Destroy this building?\nYou will get back half of the money spent in its construction.")
                    .setPositiveButton("Destroy",(dialog,id)->((MainActivity) getActivity()).doDestroyLoad())
                    .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){

                        }
                    });
            return builder.create();
        }
    }




        public class factorySlotAdapter extends RecyclerView.Adapter<factorySlotAdapter.factorySlotViewHolder> {
            class factorySlotViewHolder extends RecyclerView.ViewHolder {
                private factorySlot factorySlot;

                private factorySlotViewHolder(View itemView) {
                    super(itemView);
                }
            }

            private final LayoutInflater layoutInflater;
            private List<factorySlot> factories;

            factorySlotAdapter(Context context) {
                layoutInflater = LayoutInflater.from(context);
            }

            @Override
            public int getItemCount() {
                if (factories != null)
                    return factories.size();
                else return 0;
            }

            @Override
            public factorySlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = layoutInflater.inflate(R.layout.factory_management, parent, false);
                return new factorySlotViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(factorySlotViewHolder holder, int position) {
                if (factories != null) {
                    factorySlot current = factories.get(position);
                    holder.factorySlot = current;
                }
            }

            void setFactories(List<factorySlot> factories) {
                this.factories = factories;
                notifyDataSetChanged();
            }


        }

    public class resourceAdapter extends RecyclerView.Adapter<resourceAdapter.resourceViewHolder>{
        class resourceViewHolder extends RecyclerView.ViewHolder{
            private resource resource;

            private resourceViewHolder(View itemView){
                super(itemView);
            }
        }
        private final LayoutInflater layoutInflater;

        private List<resource> resources;

        resourceAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getItemCount(){
            if(resources!=null)
                return resources.size();
            else return 0;
        }
        @Override
        public resourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View itemView = layoutInflater.inflate(R.layout.resource_view,parent,false);
            return new resourceViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(resourceViewHolder holder, int position){
            if(resources != null){
                resource current = resources.get(position);
                holder.resource = current;
            }
        }

        void setResources(List<resource> resources){
            this.resources = resources;
            notifyDataSetChanged();
        }
    }


}