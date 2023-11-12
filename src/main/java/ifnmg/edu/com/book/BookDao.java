package ifnmg.edu.com.book;

import ifnmg.edu.com.repository.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDao extends Dao<Book> {
    public static final String TABLE = "book";

    @Override
    public String getSaveStatement() {
        return "insert into " + TABLE + "(title, authors, acquisition, pages, year, edition, price) values(?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public String getUpdateStatement() {
        return " update "+ TABLE + " set title = ?, authors = ?, acquisition = ?, pages = ?, year = ?, edition = ?, price = ? where id = ?";
    }

    @Override
    public String getFindByIdStatement() {
        return "select id, title, authors, acquisition, pages, year, edition, price" + " from book where id = ?";
    }

    @Override
    public String getFindAllStatement() {
        return "select id, title, authors, acquisition, pages, year, edition, price" + " from book";
    }

    @Override
    public String getDeleteStatement() {
        return "Delete from " + TABLE + " where id = ?";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, Book e) {
        try {
            pstmt.setString(1, e.getTitle());
            pstmt.setString(2, e.getAuthors());
            pstmt.setObject(3, e.getAcquisition());
            pstmt.setShort(4, e.getPages());
            pstmt.setShort(5, e.getYear());
            pstmt.setByte(6, e.getEdition());
            pstmt.setBigDecimal(7, e.getPrice());

            // Se for uma atualização (update), você também precisa definir o ID.
            if (e.getId() != null) {
                pstmt.setLong(8, e.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Book extractObject(ResultSet resultSet) {

        Book book = null;

        try {
            book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthors(resultSet.getString("authors"));
            book.setAcquisition( resultSet.getObject("acquisition", LocalDate.class));
            book.setPages(resultSet.getShort("pages"));
            book.setYear(resultSet.getShort("year"));
            book.setEdition(resultSet.getByte("edition"));
            book.setPrice(resultSet.getBigDecimal("price"));
        }catch (Exception ex) {
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return book;
    }

    @Override
    public List<Book> extractObjects(ResultSet resultSet) {
        List<Book> bookList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthors(resultSet.getString("authors"));
                book.setAcquisition(resultSet.getObject("acquisition", LocalDate.class));
                book.setPages(resultSet.getShort("pages"));
                book.setYear(resultSet.getShort("year"));
                book.setEdition(resultSet.getByte("edition"));
                book.setPrice(resultSet.getBigDecimal("price"));
                bookList.add(book);
            }
        } catch (Exception ex) {
            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookList;
    }

}
