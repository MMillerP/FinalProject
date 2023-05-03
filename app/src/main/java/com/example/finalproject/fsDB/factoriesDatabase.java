package com.example.finalproject.fsDB;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {factorySlot.class},version = 6,exportSchema = false)
public abstract class factoriesDatabase extends RoomDatabase{
    public interface factoriesListener{
        void onFactoriesReturned(factorySlot factorySlot);
    }

    public abstract factorySlotDAO factorySlotDAO();

    private static factoriesDatabase INSTANCE;

    public static factoriesDatabase getFactoriesDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (factoriesDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            factoriesDatabase.class,"factories_database")
                            .addCallback(createFactoriesDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                    create();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createFactoriesDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase fsDB){
            super.onCreate(fsDB);
            createFactorySlotTable();
        }
    };



    private static void createFactorySlotTable(){
        for(int i = 0; i < 12; i++){
            insert(new factorySlot(i, "NON",0,"NON", "NON",0,0,"NON",0));
        }
    }

    public static void getFactorySlot(int id, factoriesListener listener){
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                listener.onFactoriesReturned((factorySlot) msg.obj);
            }
        };

            (new Thread(() -> {
                Message msg = handler.obtainMessage();
                msg.obj = INSTANCE.factorySlotDAO().getById(id);
                handler.sendMessage(msg);
            })).start();

    }
    public static void insert(factorySlot factorySlot){
        (new Thread(()->INSTANCE.factorySlotDAO().insert(factorySlot))).start();
    }
    public static void delete(int id){
        (new Thread(()-> INSTANCE.factorySlotDAO().delete(id))).start();
    }
    public static void update(factorySlot factorySlot){
        (new Thread(()-> INSTANCE.factorySlotDAO().update(factorySlot))).start();
    }

    public static void destroy(){
        (new Thread(()->INSTANCE.factorySlotDAO().nukeTable())).start();
    }

    public static void create(){
        (new Thread(()-> createFactorySlotTable())).start();
    }

}
