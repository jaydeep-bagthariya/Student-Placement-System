package com.example.studentplacementsystem;

public class uploadResume {

    public String name;
    public String url;

    public uploadResume() {}

    public uploadResume(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
