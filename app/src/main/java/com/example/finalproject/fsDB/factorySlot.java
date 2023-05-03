package com.example.finalproject.fsDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="factories")
public class factorySlot {
    public factorySlot(int id, String factory, int level, String inputTypeOne,String inputTypeTwo, int inputOne, int inputTwo, String outputType, int output){
        this.id = id;
        this.factory = factory;
        this.level = level;
        this.inputTypeOne = inputTypeOne;
        this.inputTypeTwo = inputTypeTwo;
        this.inputOne = inputOne;
        this.inputTwo = inputTwo;
        this.outputType = outputType;
        this.output = output;
    }

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "rowid")
    public int id;

    @ColumnInfo(name = "factory type")
    public String factory;

    @ColumnInfo(name = "factory level")
    public int level;

    @ColumnInfo(name = "input one type")
    public String inputTypeOne;

    @ColumnInfo(name = "input two type")
    public String inputTypeTwo;

    @ColumnInfo(name = "input one amount")
    public int inputOne;

    @ColumnInfo(name = "input two amount")
    public int inputTwo;

    @ColumnInfo(name = "output type")
    public String outputType;

    @ColumnInfo(name = "output amount")
    public int output;
}
