package com.projektocr.Service;

import com.projektocr.Model.User;
import com.projektocr.dto.UserDto;

public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);

}
