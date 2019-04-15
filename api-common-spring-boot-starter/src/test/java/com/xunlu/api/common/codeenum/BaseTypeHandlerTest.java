package com.xunlu.api.common.codeenum;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

@RunWith(MockitoJUnitRunner.class)
abstract class BaseTypeHandlerTest {
    @Mock
    protected ResultSet rs;
    @Mock
    protected PreparedStatement ps;
    @Mock
    protected CallableStatement cs;
    @Mock
    protected ResultSetMetaData rsmd;

    public abstract void shouldSetParameter() throws Exception;

    public abstract void shouldGetResultFromResultSetByName() throws Exception;

    public abstract void shouldGetResultNullFromResultSetByName() throws Exception;

    public abstract void shouldGetResultFromResultSetByPosition() throws Exception;

    public abstract void shouldGetResultNullFromResultSetByPosition() throws Exception;

    public abstract void shouldGetResultFromCallableStatement() throws Exception;

    public abstract void shouldGetResultNullFromCallableStatement() throws Exception;
}
