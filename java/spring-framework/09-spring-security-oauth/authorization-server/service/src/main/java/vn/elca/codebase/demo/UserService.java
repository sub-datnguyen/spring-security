package vn.elca.codebase.demo;

import vn.elca.codebase.dto.CustomUserDetails;
import vn.elca.codebase.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto saveOrUpdate(UserDto dto);
    UserDto findOne(Long id);
    List<UserDto> findAll();
    CustomUserDetails findUserDetailsByUsername(String username);
}
