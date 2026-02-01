
package com.webdev.identity_service.mapper;

import com.webdev.identity_service.dto.request.PermissionRequest;
import com.webdev.identity_service.dto.request.RoleRequest;
import com.webdev.identity_service.dto.response.PermissionResponse;
import com.webdev.identity_service.dto.response.RoleResponse;
import com.webdev.identity_service.entity.Permission;
import com.webdev.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)//khi map se bo qua permission
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
