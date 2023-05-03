package com.example.finalproject.rDB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalproject.fsDB.factoriesDatabase;
import com.example.finalproject.fsDB.factorySlot;

import java.util.List;

public class resourcesViewModel extends AndroidViewModel {

    private LiveData<List<resource>> resources;

    public resourcesViewModel (Application application){
        super(application);
        resources = resourcesDatabase.getResourcesDatabase(getApplication()).resourcesDAO().getALL();
    }

    public LiveData<List<resource>> getAllResources(){
        return resources;
    }
}