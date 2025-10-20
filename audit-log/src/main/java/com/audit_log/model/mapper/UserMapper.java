package com.audit_log.model.mapper;

import com.audit_log.model.User;
import com.audit_log.model.UserRequestDTO;
import com.audit_log.model.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRequestDTO userToRequest(User user);

    @Mapping(target = "id", ignore = true)
    User requestToUser(UserRequestDTO requestDTO);

    UserResponseDTO userToResponse(User user);

    List<UserResponseDTO> listUserToResponse(List<User> list);
}
