package com.audit_log.controller;

import com.audit_log.model.User;
import com.audit_log.model.UserRequestDTO;
import com.audit_log.model.UserResponseDTO;
import com.audit_log.model.mapper.UserMapper;
import com.audit_log.repository.UserRepositoryImpl;
import com.audit_log.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {


    private final UserService service;
    private final UserMapper userMapper;

    public UserController(UserService service, UserMapper userMapper) {
        this.service = service;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequest){
        UserResponseDTO savedUser = service.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUser(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size
                                                         ){
        return ResponseEntity.status(HttpStatus.OK).body(service.userPage(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO requestDTO){
        requestDTO.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(service.update(requestDTO, id));
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> getUserXLSX(){
        byte[] fileBytes = service.exportUserToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "Users.xlsx");
        headers.setContentLength(fileBytes.length);


        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/teste")
    public String teste(){
        return service.finByID(70L).toString();
    }
}
