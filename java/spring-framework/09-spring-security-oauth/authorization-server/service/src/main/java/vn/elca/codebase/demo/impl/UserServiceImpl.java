package vn.elca.codebase.demo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.elca.codebase.demo.UserService;
import vn.elca.codebase.dto.CustomUserDetails;
import vn.elca.codebase.dto.UserDto;
import vn.elca.codebase.entity.User;
import vn.elca.codebase.exception.UserNotFoundException;
import vn.elca.codebase.mapper.UserMapper;
import vn.elca.codebase.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Throwable.class})
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto saveOrUpdate(UserDto dto) {
        User user = toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto findOne(Long id) {
        return userRepository.findById(id)
            .map(userMapper::toDto)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public CustomUserDetails findUserDetailsByUsername(String username) {
        User u = userRepository
            .findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " cannot be found"));
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUsername(u.getUsername());
        userDetails.setPassword(u.getPassword());
        userDetails.setAuthorities(u.getRoles()
            .stream()
            .flatMap(r -> r.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getName())))
            .collect(Collectors.toSet()));
        return userDetails;
    }

    private User toEntity(UserDto dto) {
        User user;
        if (dto.getId() != null) {
            User existing = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException(dto.getId()));
            user = userMapper.clone(existing);
        } else {
            user = new User();
        }
        return userMapper.toExistingEntity(dto, user);
    }
}
