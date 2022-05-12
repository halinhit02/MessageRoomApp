package com.nhom6.messageroomapp.data.model.user;

public class UserUpdateRequest {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String aboutMe;
    private String gender;
    private String dob;
    private String avatar;
    private String password;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(Integer id, String name, String email, String phone, String aboutMe, String gender, String dob, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.aboutMe = aboutMe;
        this.gender = gender;
        this.dob = dob;
        this.password = password;
    }

    public UserUpdateRequest(Integer id, String avatar, String password) {
        this.id = id;
        this.avatar = avatar;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

