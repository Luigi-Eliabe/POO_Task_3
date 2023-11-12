package ifnmg.edu.com;

import ifnmg.edu.com.book.Book;
import ifnmg.edu.com.book.BookDao;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        try { // Trying to create book with invalid date
            Book failbook = new Book(null, "book1", "Luigi Eliabe", LocalDate.now().plusDays((long) 3), (short) 46, (short) 2014, (byte) 1, BigDecimal.valueOf(123.32));
            Long bookID = new BookDao().saveOrUpdate(failbook);
            failbook.setId(bookID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Creating 3 valid books to be inserted in the DB
        try {

            Book book = new Book(null, "Livro Qualquer", "Desconhecido", LocalDate.of(2023, Month.JULY, 8), (short) 35, (short) 2003, (byte) 84, BigDecimal.valueOf(85.43));
            Long bookID = new BookDao().saveOrUpdate(book);
            book.setId(bookID);

            Book book1 = new Book(null, "Lord of the Rings", "J. R. R. Tolkien", LocalDate.of(2016, Month.JULY, 29), (short) 1178, (short) 2004, (byte) 1, BigDecimal.valueOf(182.5));
            Long book1ID = new BookDao().saveOrUpdate(book1);
            book1.setId(book1ID);

            Book book2 = new Book(null, "The Hobbit", "J. R. R. Tolkien", LocalDate.of(2022, Month.SEPTEMBER, 21), (short) 	320, (short) 2014, (byte) 1, BigDecimal.valueOf(95.45));
            Long book2ID = new BookDao().saveOrUpdate(book2);
            book2.setId(book2ID);

            //Editando book
            Book bookEdited = new BookDao().findById(book2ID);
            bookEdited.setTitle("The Hobbit ou There and Back Again");
            new BookDao().saveOrUpdate(bookEdited);

            //Procurando por 2 livros
            Book searchedBook = new BookDao().findById(book.getId());
            System.out.println("book id: " + book.getId() + " - Book Title: " + searchedBook.getTitle());

            searchedBook = new BookDao().findById(book2.getId());
            System.out.println("book id: " + book2.getId() + " - Book Title: " + searchedBook.getTitle());

            //Recuperando todos os books
            List<Book> bookList = new BookDao().findAll();
            System.out.println("All Books: ");
            for (var x : bookList){
              System.out.println(x.getTitle());
            }

            //Apagando book
            System.out.println("Deleting book by ID: " + book1.getId());
            new BookDao().delete(book1.getId());
            List<Book> bookListErased = new BookDao().findAll();
            System.out.println("All books: ");
            for (var x : bookListErased){
                System.out.println(x.getTitle());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
