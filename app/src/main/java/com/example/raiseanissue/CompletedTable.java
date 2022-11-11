package com.example.raiseanissue;

public class CompletedTable {

    String message;
    String issueID;
    public CompletedTable(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public String getIssueID() {
        return issueID;
    }

    public CompletedTable(String message, String issueID) {
        this.message = message;
        this.issueID = issueID;
    }
}
