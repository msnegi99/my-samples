package com.msnegi.recyclerviewheaderfooter;


public class Data {
    public static final int HEADER_TYPE=0;
    public static final int FOOTER_TYPE=1;
    public static final int ITEM_TYPE=2;

    public int type;
    public String title;
    public String description;
    public int imageId;

    public Data(int type, String title, String description, int imageId)
    {
        this.type=type;
        this.title=title;
        this.description=description;
        this.imageId = imageId;
    }

}

