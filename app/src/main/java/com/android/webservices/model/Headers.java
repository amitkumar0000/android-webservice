package com.android.webservices.model;

public class Headers{
    String accept;
    String accept_encoding;
    String accept_language;
    String connection;
    String host;
    String Upgrade_Insecure_Requests;
    String User_Agent;

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAccept_encoding() {
        return accept_encoding;
    }

    public void setAccept_encoding(String accept_encoding) {
        this.accept_encoding = accept_encoding;
    }

    public String getAccept_language() {
        return accept_language;
    }

    public void setAccept_language(String accept_language) {
        this.accept_language = accept_language;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUpgrade_Insecure_Requests() {
        return Upgrade_Insecure_Requests;
    }

    public void setUpgrade_Insecure_Requests(String upgrade_Insecure_Requests) {
        Upgrade_Insecure_Requests = upgrade_Insecure_Requests;
    }

    public String getUser_Agent() {
        return User_Agent;
    }

    public void setUser_Agent(String user_Agent) {
        User_Agent = user_Agent;
    }
}
