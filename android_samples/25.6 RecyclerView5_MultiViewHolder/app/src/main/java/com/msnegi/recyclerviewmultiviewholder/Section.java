package com.msnegi.recyclerviewmultiviewholder;

import org.json.JSONObject;

public class Section {

    public String SectionId = "";
    public int SectionType = 0;
    public String SectionName = "";
    public String Title = "";
    public String Link = "";
    public String ImageUrl = "";
    public String Data = "";

    public static Section build(String jsonStr){

        Section section = new Section();
        try {
            section = build(new JSONObject(jsonStr));
            return section;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Section build(JSONObject jsonObject){

        Section section = new Section();
        try {
            section.SectionId = jsonObject.getString("SectionId");
            section.SectionType = jsonObject.getInt("SectionType");
            section.SectionName = jsonObject.getString("SectionName");
            section.Title = jsonObject.getString("Title");
            section.Link = jsonObject.getString("Link");
            section.ImageUrl = jsonObject.getString("ImageUrl");
            //section.Data = jsonObject.getString("Data");

            return section;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getSectionType() {
        return SectionType;
    }

    public void setSectionType(int SectionType) {
        this.SectionType = SectionType;
    }
}
