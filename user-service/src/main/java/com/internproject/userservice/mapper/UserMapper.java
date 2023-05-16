package com.internproject.userservice.mapper;

import com.internproject.userservice.dto.UserUpdateRequest;
import com.internproject.userservice.entity.UserDetail;

import java.sql.Date;

public class UserMapper {
    private static UserMapper INSTANCE;

    public static UserMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }
    public UserDetail toUserDetail(UserUpdateRequest userUpdateRequest) {
        UserDetail userDetail = new UserDetail();

        userDetail.setFullName(userUpdateRequest.getFullName());
        userDetail.setProvinceID(userUpdateRequest.getProvinceId());
        userDetail.setDistrictID(userUpdateRequest.getDistrictId());
        userDetail.setWardId(userUpdateRequest.getWardId());
        userDetail.setPhone(userUpdateRequest.getPhone());
        userDetail.setGender(userUpdateRequest.isGender());
        Date dob = Date.valueOf(userUpdateRequest.getDob());
        userDetail.setDob(dob);

        return userDetail;
    }
}