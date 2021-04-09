package com.example.studentplacementsystem;

public class Apply {

    private String companyName;
    private String jobPost;
    private String companyDescription;
    private String workType;
    private String user_id;
    private String company_id;
    private String status;
    private String studentName;
    private String studentEmail;

    public Apply() {

    }

    public Apply(String companyName, String jobPost, String companyDescription, String workType, String user_id, String company_id, String status, String studentName, String studentEmail) {
        this.companyName = companyName;
        this.jobPost = jobPost;
        this.companyDescription = companyDescription;
        this.workType = workType;
        this.user_id = user_id;
        this.company_id = company_id;
        this.status = status;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobPost() {
        return jobPost;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setJobPost(String jobPost) {
        this.jobPost = jobPost;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
