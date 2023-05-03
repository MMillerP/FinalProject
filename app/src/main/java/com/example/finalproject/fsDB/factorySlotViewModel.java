package com.example.finalproject.fsDB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class factorySlotViewModel extends AndroidViewModel {

    private LiveData<List<factorySlot>> factories;

    public factorySlotViewModel (Application application){
        super(application);
        factories = factoriesDatabase.getFactoriesDatabase(getApplication()).factorySlotDAO().getALL();
    }

    public LiveData<List<factorySlot>> getAllFactorySlots(){
        return factories;
    }
}
