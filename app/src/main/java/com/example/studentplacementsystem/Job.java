package com.example.studentplacementsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {

    private String companyName;
    private String jobPost;
    private String companyDescription;
    private String workType;

    public Job() {
        //empty constructor needed
    }

    public Job(String companyName, String jobPost, String companyDescription, String workType) {
        this.companyName = companyName;
        this.jobPost = jobPost;
        this.companyDescription = companyDescription;
        this.workType = workType;
    }

    protected Job(Parcel in) {
        companyName = in.readString();
        jobPost = in.readString();
        companyDescription = in.readString();
        workType = in.readString();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(companyName);
        parcel.writeString(jobPost);
        parcel.writeString(companyDescription);
        parcel.writeString(workType);
    }
}
