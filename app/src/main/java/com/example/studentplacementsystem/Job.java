package com.example.studentplacementsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class Job /*implements Parcelable*/ {

    private String companyName;
    private String jobPost;
    private String companyDescription;
    private String workType;
    private String date;
    private String time;
    private String user_id;
    private String company_id;

    public Job() {
        //empty constructor needed
    }

    public Job(String companyName, String jobPost, String companyDescription, String workType, String date, String time, String user_id, String company_id) {
        this.companyName = companyName;
        this.jobPost = jobPost;
        this.companyDescription = companyDescription;
        this.workType = workType;
        this.date = date;
        this.time = time;
        this.user_id = user_id;
        this.company_id = company_id;
    }

//    protected Job(Parcel in) {
//        companyName = in.readString();
//        jobPost = in.readString();
//        companyDescription = in.readString();
//        workType = in.readString();
//        time = in.readString();
//        date = in.readString();
//        user_id = in.readString();
//        company_id = in.readString();
//    }

//    public static final Creator<Job> CREATOR = new Creator<Job>() {
//        @Override
//        public Job createFromParcel(Parcel in) {
//            return new Job(in);
//        }
//
//        @Override
//        public Job[] newArray(int size) {
//            return new Job[size];
//        }
//    };

    public String getCompanyName() {
        return companyName;
    }

    public String getJobPost() {
        return jobPost;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public String getWorkType() {
        return workType;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUser_id() {
        return user_id;
    }


    public String getCompany_id() {
        return company_id;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(companyName);
//        parcel.writeString(jobPost);
//        parcel.writeString(companyDescription);
//        parcel.writeString(workType);
//        parcel.writeString(time);
//        parcel.writeString(date);
//        parcel.writeString(user_id);
//        parcel.writeString(company_id);
//    }
}
