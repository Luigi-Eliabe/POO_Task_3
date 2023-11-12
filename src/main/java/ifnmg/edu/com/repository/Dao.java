package ifnmg.edu.com.repository;

import ifnmg.edu.com.Entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public abstract class Dao <E> implements IDao<E> {
    public static final String DB = "library";

    @Override
    public Long saveOrUpdate(E e) {
        Long id = 0L;

        if (((Entity) e).getId() == null || ((Entity) e).getId() == 0) {


            try ( PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement( getSaveStatement(),Statement.RETURN_GENERATED_KEYS)) {

                composeSaveOrUpdateStatement(preparedStatement, e);

                System.out.println(">> SQL: " + preparedStatement);

                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                // Moves to first retrieved data
                if (resultSet.next()) {


                    id = resultSet.getLong(1);
                }

            } catch (Exception ex) {
                System.out.println(">> " + ex);
            }

        } else {
            // Update existing record
            try ( PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(
                    getUpdateStatement())) {

                // Assemble the SQL statement with the data (->?)
                composeSaveOrUpdateStatement(preparedStatement, e);

                // Show the full sentence
                System.out.println(">> SQL: " + preparedStatement);

                // Performs the update on the database
                preparedStatement.executeUpdate();

                // Keep the primary key
                id = ((Entity) e).getId();

            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
            }
        }
        return id;
    }

    @Override
    public E findById(long id) {
        try ( PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement( getFindByIdStatement())) {
            preparedStatement.setLong(1, id);
            System.out.println(">> SQL: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractObject(resultSet);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    @Override
    public List<E> findAll() {
        try ( PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(getFindAllStatement())) {
            System.out.println(">> SQL: " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            return extractObjects(resultSet);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement( getDeleteStatement())){
            preparedStatement.setLong(1, id);
            System.out.println(">> SQL: " + preparedStatement);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            System.out.println("Exception" + ex);
        }
    }
}
