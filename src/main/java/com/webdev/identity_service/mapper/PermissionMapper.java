package com.webdev.identity_service.mapper;

import com.webdev.identity_service.dto.request.PermissionRequest;
import com.webdev.identity_service.dto.response.PermissionResponse;
import com.webdev.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
