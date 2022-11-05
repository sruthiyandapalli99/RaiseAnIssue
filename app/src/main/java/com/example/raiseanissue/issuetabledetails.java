package com.example.raiseanissue;

public class issuetabledetails
{

    String issuedetails;
    String issueimage;
    String issueID;

    public issuetabledetails(String issuedetails, String issueimage, String issueID) {
        this.issuedetails = issuedetails;
        this.issueimage = issueimage;
        this.issueID = issueID;
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
