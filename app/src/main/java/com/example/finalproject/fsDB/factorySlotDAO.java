package com.example.finalproject.fsDB;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface factorySlotDAO {
    @Query("SELECT * FROM factories ORDER BY rowid")
    LiveData<List<factorySlot>> getALL();

    @Query("SELECT * FROM factories WHERE rowid = :id")
    factorySlot getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(factorySlot... factories);

    @Update
    void update(factorySlot... factorySlot);

    @Delete
    void delete(factorySlot... user);

    @Query("DELETE FROM factories WHERE rowid= :id")
    void delete(int id);

    @Query("DELETE FROM factories")
    void nukeTable();

}
