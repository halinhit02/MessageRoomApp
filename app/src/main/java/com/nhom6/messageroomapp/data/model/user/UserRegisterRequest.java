package com.nhom6.messageroomapp.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DateTimeUtils;

public class UserRegisterRequest {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("aboutMe")
    @Expose
    private String aboutMe = "";
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("gender")
    @Expose
    private String gender = "Nam";
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("isAdmin")
    @Expose
    private Boolean isAdmin = false;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId = 0;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId = 0;
    @SerializedName("branchId")
    @Expose
    private Integer branchId = 0;
    @SerializedName("creatorId")
    @Expose
    private Integer creatorId = 0;
    @SerializedName("staffId")
    @Expose
    private Integer staffId = 0;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String name, String aboutMe, String dob, String gender, String phone, String email, String password) {
        this.name = name;
        this.aboutMe = aboutMe;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getDob() {
        return dob != null ? DateTimeUtils.getFullDateFromServer(dob) : "";
    }

    public void setDob(String dob) {
        this.dob = DateTimeUtils.convertToServer(dob, Constant.FullDatePattern);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}

