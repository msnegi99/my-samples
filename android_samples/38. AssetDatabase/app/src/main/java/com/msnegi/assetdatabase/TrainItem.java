package com.msnegi.assetdatabase;

public class TrainItem {

            int id;
            String destination;
            String timeOfDeparture;

    public TrainItem(){

    }

    public TrainItem(int id, String destination, String timeOfDeparture){
        this.id = id;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
    }

}
