package com.internproject.userservice.mapper;

import com.internproject.userservice.dto.UserDTO;
import com.internproject.userservice.dto.UserDetailDTO;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapstruct {
    @Mapping(source = "userDetail", target = "userDetailDTO")
    UserDTO toUserDTO(User user);
    UserDetailDTO toUserDetailDTO(UserDetail userDetail);
    @Mapping(source = "userDetailDTO", target = "userDetail")
    User toUser(UserDTO userDTO);
    UserDetail toUserDetail(UserDetailDTO userDetailDTO);
}
