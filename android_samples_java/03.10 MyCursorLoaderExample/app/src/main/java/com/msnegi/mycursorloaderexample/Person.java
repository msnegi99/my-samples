package com.msnegi.mycursorloaderexample;

public class Person {

    String _id;
    String name;
    String birthday;

    public Person(String _id, String name, String birthday){
        this._id = _id;
        this.name = name;
        this.birthday = birthday;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
