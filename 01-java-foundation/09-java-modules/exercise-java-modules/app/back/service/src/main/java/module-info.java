module projectCodeBaseService {
    requires lombok;
    requires org.mapstruct;
    requires org.apache.commons.lang3;
    requires org.hibernate.orm.core;
    requires guava;
    requires querydsl.core;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context;
    requires spring.tx;
    requires java.persistence;
    requires java.validation;
    requires java.desktop;

    exports vn.elca.codebase.dto;
}