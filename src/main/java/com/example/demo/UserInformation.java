package com.example.demo;

public class UserInformation {
    private int id;
    private String affiliatedCompany; //所属企業
    private String name;
    private int score; //0~100

    public UserInformation(int id,String affiliatedCompany,String name,int score){
        this.id = id;
        this.affiliatedCompany = affiliatedCompany;
        this.name = name;
        this.score = score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAffiliatedCompany(String affiliatedCompany) {this.affiliatedCompany = affiliatedCompany;}

    public void setName(String name) {this.name = name;}

    public void setScore(int score) {this.score = score < 0 || score > 100 ? 0 : score;}

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public String getAffiliatedCompany(){return this.affiliatedCompany;}

    public int getScore(){return this.score;}
}
