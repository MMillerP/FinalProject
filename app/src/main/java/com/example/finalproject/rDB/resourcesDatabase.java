package com.example.finalproject.rDB;

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

import com.example.finalproject.MainActivity;

@Database(entities={resource.class},version = 3, exportSchema = false)
public abstract class resourcesDatabase extends RoomDatabase {
    public interface resourcesListener{
        void onResourcesReturned(resource resource);
    }

    public abstract resourcesDAO resourcesDAO();

    private static resourcesDatabase INSTANCE;

    public static resourcesDatabase getResourcesDatabase(final Context context){
        if(INSTANCE==null){
            synchronized(resourcesDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    resourcesDatabase.class,"resources_database")
                    .fallbackToDestructiveMigration()
                    .build();
                    create();
                }
            }
        }
        return INSTANCE;
    }

    private static void createResourceTable(){
        for(int i = 0; i < 12; i++){
            insert(new resource
                    (i,
                    DefaultContentResource.TAG[i],
                            0,
                            100 * DefaultContentResource.popConsume[i],
                    DefaultContentResource.aiOneProduce[i],
                    DefaultContentResource.aiOneConsume[i],
                    DefaultContentResource.aiTwoProduce[i],
                    DefaultContentResource.aiTwoConsume[i],
                    DefaultContentResource.aiThreeProduce[i],
                    DefaultContentResource.aiThreeConsume[i]));
        }
    }

    public static void getResource(int id, resourcesListener listener){
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                listener.onResourcesReturned((resource) msg.obj);
            }
        };

        (new Thread(()-> {
            Message msg = handler.obtainMessage();
            msg.obj = INSTANCE.resourcesDAO().getById(id);
            handler.sendMessage(msg);
        })).start();
    }

    public static void insert(resource resource){
        (new Thread(()-> INSTANCE.resourcesDAO().insert(resource))).start();
    }
    public static void delete(int id){
        (new Thread(()-> INSTANCE.resourcesDAO().delete(id))).start();
    }
    public static void update(resource resource){
        (new Thread(()->INSTANCE.resourcesDAO().update(resource))).start();
    }
    public static void destroy(){
        (new Thread(()-> INSTANCE.resourcesDAO().nukeTable())).start();
    }

    public static void create(){
        (new Thread(()-> createResourceTable())).start();
    }


}
