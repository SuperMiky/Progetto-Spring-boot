package com.example.webservice;

public class Response {
    String status;
    String result;
    String message;

    public Response(){}

    public String ok(String result)
    {
        return "{status:ok," + "result:" + result + "}";
    }

    public String error(String message)
    {
        return "{status:error," + "message:" + message + "}";
    }


}
