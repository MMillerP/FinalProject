package com.example.finalproject.rDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="resources")
public class resource {
    public resource(int id, String tag, int produceOne, int consumeOne, int produceTwo, int consumeTwo, int produceThree, int consumeThree, int produceFour, int consumeFour){
        this.id = id;
        this.tag = tag;
        this.produceOne = produceOne;
        this.consumeOne = consumeOne;
        this.produceTwo = produceTwo;
        this.consumeTwo = consumeTwo;
        this.produceThree = produceThree;
        this.consumeThree= consumeThree;
        this.produceFour = produceFour;
        this.consumeFour = consumeFour;
    }
    @PrimaryKey(autoGenerate=false)
    @ColumnInfo(name = "rowid")
    public int id;

    @ColumnInfo(name = "resource tag")
    public String tag;

    @ColumnInfo(name = "total produced for player")
    public int produceOne;

    @ColumnInfo(name = "total consumed by player")
    public int consumeOne;

    @ColumnInfo(name = "total produced by ai 1")
    public int produceTwo;

    @ColumnInfo(name = "total consumed by ai 1")
    public int consumeTwo;

    @ColumnInfo(name = "total produced by ai 2")
    public int produceThree;

    @ColumnInfo(name = "total consumed by ai 2")
    public int consumeThree;

    @ColumnInfo(name = "total produced by ai 3")
    public int produceFour;

    @ColumnInfo(name = "total consumed by ai 3")
    public int consumeFour;
}
