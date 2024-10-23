//package com.prmproject.clothesstoremobileandroid.Data.model;
//
//import com.prmproject.clothesstoremobileandroid.Data.model.type.UserType;
//
//public class User {
//    private int userId;
//    private String username;
//    private String password;
//    private String email;
//    private String phone;
//    private boolean status;
//    private int userTypeValue; // The raw int value from API
//
//    private UserType userType; // The enum that will be derived from the int
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
////    public int getUserTypeValue() {
////        return userTypeValue;
////    }
////
////    public void setUserTypeValue(int userTypeValue) {
////        this.userTypeValue = userTypeValue;
////        this.userType = UserType.fromInt(userTypeValue); // Convert the int to UserType enum
////    }
//
//    public UserType getUserType() {
//        return userType;
//    }
//}
package com.prmproject.clothesstoremobileandroid.Data.model;

import com.prmproject.clothesstoremobileandroid.Data.model.type.UserType;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("UserId")
    private int userId;

    @SerializedName("Username")
    private String username;

    @SerializedName("Password")
    private String password;

    @SerializedName("Email")
    private String email;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Status")
    private boolean status;

    @SerializedName("UserType")
    private int userType;

    // Getter và Setter

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserType getUserType() {
        return UserType.values()[userType];
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
