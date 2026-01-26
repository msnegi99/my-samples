package com.msnegi.websearchvolley;

import org.json.JSONObject;

public class SearchItem {
    private String title;
    private String num_result;

    public static SearchItem build(JSONObject jsonObject){
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.setTitle(jsonObject.getString("title"));
                    //.setNum_result(jsonObject.getString("num_results"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return searchItem;
    }

    public String getTitle() {
        return title;
    }

    public SearchItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getNum_result() {
        return num_result;
    }

    public SearchItem setNum_result(String num_result) {
        this.num_result = num_result;
        return this;
    }
}
