package vn.elca.codebase.common.mapper.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import vn.elca.codebase.common.mapper.CommonWebMapper;

public abstract class CommonWebMapperDecorator implements CommonWebMapper {
    @Autowired
    @Qualifier("delegate")
    private CommonWebMapper delegate;
}
