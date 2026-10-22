package com.msnegi.recyclerviewexpandable;

public class ServiceStatus {

    private String requestTitle;
    private String fixableIssue;
    private String partsRequired;
    private String actionTaken;

    public void setRequestTitle(String requestTitle){
        this.requestTitle = requestTitle;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setFixableIssue(String fixableIssue){
        this.fixableIssue = fixableIssue;
    }

    public String getFixableIssue() {
        return fixableIssue;
    }

    public void setPartsRequired(String partsRequired){
        this.partsRequired = partsRequired;
    }

    public String getPartsRequired() {
        return partsRequired;
    }

    public void setActionTaken(String actionTaken){
        this.actionTaken = actionTaken;
    }

    public String getActionTaken() {
        return actionTaken;
    }
}



