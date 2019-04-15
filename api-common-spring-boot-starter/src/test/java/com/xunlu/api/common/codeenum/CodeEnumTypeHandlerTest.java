package com.xunlu.api.common.codeenum;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CodeEnumTypeHandlerTest extends BaseTypeHandlerTest {

    private static final TypeHandler<TestSexEnum> TYPE_HANDLER = new CodeEnumTypeHandler<>(TestSexEnum.class);

    @Override
    @Test
    public void shouldSetParameter() throws Exception {
        TYPE_HANDLER.setParameter(ps, 1, TestSexEnum.MAIL, null);
        verify(ps).setInt(1, 1);
    }

    @Test
    public void shouldSetNullParameter() throws Exception {
        TYPE_HANDLER.setParameter(ps, 1, null, JdbcType.VARCHAR);
        verify(ps).setNull(1, JdbcType.VARCHAR.TYPE_CODE);
    }

    @Override
    @Test
    public void shouldGetResultFromResultSetByName() throws Exception {
        when(rs.getInt("column")).thenReturn(0);
        when(rs.wasNull()).thenReturn(false);
        Assert.assertEquals(TestSexEnum.THIRD, TYPE_HANDLER.getResult(rs, "column"));
    }

    @Override
    @Test
    public void shouldGetResultNullFromResultSetByName() throws Exception {
        when(rs.getInt("column")).thenReturn(0);
        when(rs.wasNull()).thenReturn(true);
        assertNull(TYPE_HANDLER.getResult(rs, "column"));
    }

    @Override
    @Test
    public void shouldGetResultFromResultSetByPosition() throws Exception {
        when(rs.getInt(1)).thenReturn(0);
        when(rs.wasNull()).thenReturn(false);
        Assert.assertEquals(TestSexEnum.THIRD, TYPE_HANDLER.getResult(rs, 1));
    }

    @Override
    @Test
    public void shouldGetResultNullFromResultSetByPosition() throws Exception {
        when(rs.getInt(1)).thenReturn(0);
        when(rs.wasNull()).thenReturn(true);
        assertNull(TYPE_HANDLER.getResult(rs, 1));
    }

    @Override
    @Test
    public void shouldGetResultFromCallableStatement() throws Exception {
        when(cs.getInt(1)).thenReturn(0);
        when(cs.wasNull()).thenReturn(false);
        Assert.assertEquals(TestSexEnum.THIRD, TYPE_HANDLER.getResult(cs, 1));
    }

    @Override
    @Test
    public void shouldGetResultNullFromCallableStatement() throws Exception {
        when(cs.getInt(1)).thenReturn(0);
        when(cs.wasNull()).thenReturn(true);
        assertNull(TYPE_HANDLER.getResult(cs, 1));
    }


}