package com.negi.firebaseformsaving;

import com.firebase.client.Firebase;

/**
 * Created by zeeshanhanif on 4/12/2015.
 */
public class FirebaseHandler {

    private static FirebaseHandler ourInstance;
    private Firebase firebaseRef;

    public static FirebaseHandler getInstance() {
        if(ourInstance == null){
            ourInstance = new FirebaseHandler();
        }
        return ourInstance;
    }

    private FirebaseHandler() {
        //firebaseRef = new Firebase("https://to-do-list-c9dfb.firebaseio.com/");
        //firebaseRef = new Firebase("https://form-saving-1060c.firebaseio.com/");
        firebaseRef = new Firebase("https://to-do-list-c9dfb-default-rtdb.firebaseio.com/");
    }

    public Firebase getRootFirebaseRef(){
        return firebaseRef;
    }

    public Firebase getFirebaseRef(String child){
        return firebaseRef.child(child);
    }
}