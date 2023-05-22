package com.internproject.userservice.mapper;

import com.internproject.userservice.dto.RoleDTO;
import com.internproject.userservice.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapstruct {
    RoleDTO toRoleDTO(Role role);
    Role toRole(RoleDTO roleDTO);
}
