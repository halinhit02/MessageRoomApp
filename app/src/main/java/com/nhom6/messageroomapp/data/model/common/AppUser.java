package com.nhom6.messageroomapp.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DateTimeUtils;

import java.io.Serializable;

public class AppUser implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("staffId")
    @Expose
    private Integer staffId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("aboutMe")
    @Expose
    private String aboutMe;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("isAdmin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("branchId")
    @Expose
    private Integer branchId;
    @SerializedName("creatorId")
    @Expose
    private Integer creatorId;
    @SerializedName("lastAccess")
    @Expose
    private String lastAccess;

    public AppUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
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
        return DateTimeUtils.getFullDateFromServer(dob);
    }

    public void setDob(String dob) {
        this.dob = DateTimeUtils.convertToServer(dob, Constant.FullDatePattern); //"dd/MM/yyyy"
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

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
    }
}
