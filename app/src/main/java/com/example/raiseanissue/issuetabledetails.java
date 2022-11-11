package com.example.raiseanissue;

public class issuetabledetails
{

    String issuedetails;
    String status;
    String phonenum;
    String issueimage;
    String issueID;

    public String getPhonenum() {
        return phonenum;
    }

    public issuetabledetails(String issueID, String phonenum) {
        //this.phonenum = phonenum;
        this.issueID = issueID;
        this.phonenum = phonenum;
    }
    public issuetabledetails(){

    }
    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public issuetabledetails(String issuedetails, String issueimage, String issueID) {
        this.issuedetails = issuedetails;
        this.issueimage = issueimage;
        this.issueID = issueID;
    }

    public String getStatus() {
        return status;
    }

    public String getIssuedetails() {
        return issuedetails;
    }

    public String getIssueimage() {
        return issueimage;
    }

    public String getIssueID() {
        return issueID;
    }
}
