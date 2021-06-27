package vn.elca.codebase.business.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.elca.codebase.common.AbstractElcaServiceEndpoint;
import vn.elca.codebase.demo.UserService;
import vn.elca.codebase.dto.UserDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AbstractElcaServiceEndpoint.USER_PATH)
@Slf4j
@RequiredArgsConstructor
public class UserEndpoint extends AbstractElcaServiceEndpoint {
    private final UserService userService;

    // Find
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    UserDto findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    // Save or update
    @PostMapping
    UserDto saveOrUpdate(@RequestBody UserDto newUser) {
        return userService.saveOrUpdate(newUser);
    }
}
