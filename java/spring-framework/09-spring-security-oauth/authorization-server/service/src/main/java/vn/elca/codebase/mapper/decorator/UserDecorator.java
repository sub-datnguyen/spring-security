package vn.elca.codebase.mapper.decorator;

import vn.elca.codebase.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserDecorator implements UserMapper {
    
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;
}
