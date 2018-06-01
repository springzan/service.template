package com.example.service.template.boot.api.user;

public class UserIdentity {
    private String userId;
    private String token;

    public int getUserIdAsInt(){
        try {
            return Integer.parseInt(userId);
        } catch (Exception e) {
            return 0;
        }
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static UserIdentity build(String userId,String token){
        UserIdentity userIdentity=new UserIdentity();
        userIdentity.setUserId(userId);
        userIdentity.setToken(token);
        return userIdentity;
    }
}
