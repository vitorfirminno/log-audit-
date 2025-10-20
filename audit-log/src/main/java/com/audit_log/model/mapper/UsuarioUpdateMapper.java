package com.audit_log.model.mapper;

import com.audit_log.model.User;
import com.audit_log.model.UserRequestDTO;
import com.audit_log.model.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioUpdateMapper {

    void userUpdateMap(UserRequestDTO dto, @MappingTarget User user);
}
