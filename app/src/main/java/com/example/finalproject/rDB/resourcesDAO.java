package com.example.finalproject.rDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface resourcesDAO {

    @Query("SELECT * FROM resources ORDER BY rowid")
    LiveData<List<resource>> getALL();

    @Query("SELECT * FROM resources WHERE rowid = :id")
    resource getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(resource... resources);

    @Update
    void update(resource... resource);

    @Delete
    void delete(resource... user);

    @Query("DELETE FROM resources WHERE rowid = :id")
    void delete(int id);

    @Query("DELETE FROM resources")
    void nukeTable();

}
