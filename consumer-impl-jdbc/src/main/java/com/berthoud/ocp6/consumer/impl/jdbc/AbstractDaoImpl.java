package com.berthoud.ocp6.consumer.impl.jdbc;

import javafx.beans.NamedArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public abstract class AbstractDaoImpl {

    @Autowired
    @Qualifier("dbDataSource")
    private DataSource dataSource;          /*DataSource is an interface! */


    protected DataSource getDataSource() {
        return dataSource;
    }
}