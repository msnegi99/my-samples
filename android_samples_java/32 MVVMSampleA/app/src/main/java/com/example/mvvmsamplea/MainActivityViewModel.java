package com.example.mvvmsamplea;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel implements LifecycleObserver {

    Thread counterThread;
    int count = 0;
    boolean isCounterInProgress;

    MutableLiveData<Integer> counter;

    public MainActivityViewModel(){
        isCounterInProgress = false;

        counter = new MutableLiveData<>();

        counterThread = new Thread((Runnable) () -> {
            while (isCounterInProgress){
                try{
                    Thread.sleep(1000);
                    count++;
                    counter.postValue(count);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<Integer> getCounter(){
        return counter;
    }

    public void startCounter(){
        if(!isCounterInProgress){
            isCounterInProgress = true;
            counterThread.start();
        }
    }

    public void stopCounter(){
        isCounterInProgress = false;
    }

    //-- life cycle observer method
    /*@Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        System.out.println("onStateChanged is executed.");
        switch (event){
            case ON_CREATE:
                break;
            case ON_START:
                break;
            case ON_RESUME:
                System.out.println("Resume method executed.");
                break;
            case ON_PAUSE:
                break;
            case ON_STOP:
                break;
            case ON_DESTROY:
                break;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
        }
    }*/

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onRefresh(){
        System.out.println("Resume method executed.");
    }
}
