package com.audit_log.service;

import com.audit_log.exception.AlreadyExistException;
import com.audit_log.exception.UserNotFoundException;
import com.audit_log.model.User;
import com.audit_log.model.UserRequestDTO;
import com.audit_log.model.UserResponseDTO;
import com.audit_log.model.mapper.UserMapper;
import com.audit_log.model.mapper.UsuarioUpdateMapper;
import com.audit_log.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryImpl userRepository;
    private final UsuarioUpdateMapper usuarioUpdateMapper;
    private final UserMapper mapper;
    private final GenerateExcelInputPort generateExcelInputPort;

    public UserService(UserRepositoryImpl userRepository, UsuarioUpdateMapper usuarioUpdateMapper, UserMapper mapper, GenerateExcelInputPort generateExcelInputPort){
        this.userRepository = userRepository;
        this.usuarioUpdateMapper = usuarioUpdateMapper;
        this.mapper = mapper;
        this.generateExcelInputPort = generateExcelInputPort;
    }

    public UserResponseDTO finByID(Long id ){
        return mapper.userToResponse(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public UserResponseDTO findByEmail(String email){
        return mapper.userToResponse(userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    public byte[] exportUserToExcel(){
        return generateExcelInputPort.generate(mapper.listUserToResponse(userRepository.findAll()));
    }

    public UserResponseDTO create(UserRequestDTO requestDTO){

        userRepository.findById(requestDTO.getId()).ifPresent(existUser -> {throw new AlreadyExistException(existUser.getId());});
        userRepository.findByEmail(requestDTO.getEmail()).ifPresent( existUser -> {throw new AlreadyExistException(existUser.getEmail());});
        User user = mapper.requestToUser(requestDTO);
        userRepository.save(user);

        return mapper.userToResponse(user);
    }

    public UserResponseDTO update(UserRequestDTO userDTO, Long id){
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        User user = mapper.requestToUser(userDTO);
        userRepository.update(id, user);
        usuarioUpdateMapper.userUpdateMap(userDTO, user);
        user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Erro, usuario nao atualizado"));

        return mapper.userToResponse(user);
    }

    public List<UserResponseDTO> userPage(int page, int size){
        return mapper.listUserToResponse(userRepository.findPaged(page, size));
    }

    public boolean delete(Long id){
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        try {
            userRepository.deleteById(id);
            return true;
        }catch(RuntimeException runtimeException){
            return false;
        }
    }
}
