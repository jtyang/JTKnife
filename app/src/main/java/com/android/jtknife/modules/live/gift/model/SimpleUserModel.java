package com.android.jtknife.modules.live.gift.model;

import java.io.Serializable;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class SimpleUserModel implements Serializable {
    //    @c(a = "verify")
//    protected AuthModel authModel;
//    @c(a = "avatar_url")
    protected String avatarUrl;
    //    @c(a = "country")
    protected String country;
    //    @c(a = "level")
    protected int level;
    //    @c(a = "medal")
    protected String medal;
    //    @c(a = "nickname")
    protected String nickname;
    //    @c(a = "thumbnail")
//    protected ThumbnailModel thumbnailModel;
//    @c(a = "user_id")
    protected int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String str) {
        this.avatarUrl = str;
    }

//    public AuthModel getAuthModel() {
//        return this.authModel;
//    }
//
//    public void setAuthModel(AuthModel authModel) {
//        this.authModel = authModel;
//    }
//
//    public ThumbnailModel getThumbnailModel() {
//        return this.thumbnailModel;
//    }
//
//    public void setThumbnailModel(ThumbnailModel thumbnailModel) {
//        this.thumbnailModel = thumbnailModel;
//    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getMedal() {
        return this.medal;
    }

    public void setMedal(String str) {
        this.medal = str;
    }

    public String toString() {
        return "SimpleUserModel{userId=" + this.userId + ", nickname='" + this.nickname + '\'' + ", level=" + this.level + ", avatarUrl='" + this.avatarUrl + '\'' + ", medal='" + this.medal + '\'' + '}';
    }

    public boolean isOfficialAccount() {
        return this.userId <= 100000;
    }

//    public boolean isVerifiedAccount() {
//        if (this.authModel == null) {
//            return false;
//        }
//        return this.authModel.isVerified();
//    }

    public boolean isAwardMedal() {
        if (this.medal == null) {
            return false;
        }
        return true;
    }
}