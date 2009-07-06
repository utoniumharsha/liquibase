package liquibase.change.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import liquibase.change.AbstractChangeTest;
import liquibase.database.Database;
import liquibase.database.core.CacheDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MockDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AlterSequenceStatement;

import org.junit.Test;

/**
 * Tests for {@link AlterSequenceChange}
 */
public class AlterSequenceChangeTest extends AbstractChangeTest {

    @Override
    @Test
    public void getRefactoringName() throws Exception {
        assertEquals("Alter Sequence", new AlterSequenceChange().getChangeMetaData().getDescription());
    }

    @Override
    @Test
    public void generateStatement() throws Exception {
        AlterSequenceChange refactoring = new AlterSequenceChange();
        refactoring.setSchemaName("SCHEMA_NAME");
        refactoring.setSequenceName("SEQ_NAME");
        refactoring.setMinValue(new BigInteger("100"));
        refactoring.setMaxValue(new BigInteger("1000"));
        refactoring.setIncrementBy(new BigInteger("50"));
        refactoring.setOrdered(true);

        SqlStatement[] sqlStatements = refactoring.generateStatements(new MockDatabase());

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof AlterSequenceStatement);
        assertEquals("SCHEMA_NAME", ((AlterSequenceStatement) sqlStatements[0]).getSchemaName());
        assertEquals("SEQ_NAME", ((AlterSequenceStatement) sqlStatements[0]).getSequenceName());
        assertEquals(new BigInteger("100"), ((AlterSequenceStatement) sqlStatements[0]).getMinValue());
        assertEquals(new BigInteger("1000"), ((AlterSequenceStatement) sqlStatements[0]).getMaxValue());
        assertEquals(new BigInteger("50"), ((AlterSequenceStatement) sqlStatements[0]).getIncrementBy());
        assertEquals(true, ((AlterSequenceStatement) sqlStatements[0]).getOrdered());

    }

    @Override
    @Test
    public void getConfirmationMessage() throws Exception {
        AlterSequenceChange refactoring = new AlterSequenceChange();
        refactoring.setSequenceName("SEQ_NAME");

        assertEquals("Sequence SEQ_NAME altered", refactoring.getConfirmationMessage());
    }

    @Override
    protected boolean changeIsUnsupported(Database database) {
        return database instanceof SQLiteDatabase
                || database instanceof SybaseASADatabase
                || database instanceof MSSQLDatabase
                || database instanceof MySQLDatabase
                || database instanceof DerbyDatabase
                || database instanceof SybaseDatabase
                || database instanceof CacheDatabase;
    }
}
