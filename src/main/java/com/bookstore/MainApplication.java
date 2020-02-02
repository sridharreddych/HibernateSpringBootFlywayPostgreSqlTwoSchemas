package com.bookstore;

import com.bookstore.ds1.BookService;
import com.bookstore.ds2.AuthorService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookService bookService;
    private final AuthorService authorService;

    public MainApplication(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            System.out.println("\n Saving an author (check the MySQL database) ...");
            authorService.persistAuthor();

            System.out.println("\n Saving a book (check the PostgreSQL database) ...");
            bookService.persistBook();
        };
    }
}
/*
 * How To Auto-Create And Migrate Two Schemas In PostgreSQL Using Flyway

Note: For production, don't rely on hibernate.ddl-auto (or counterparts) to export schema DDL to the database. Simply remove (disable) hibernate.ddl-auto or set it to validate. Rely on Flyway or Liquibase.

Description: This application is an example of auto-creating and migrating two schemas in PostgreSQL using Flyway. In addition, each data source uses its own HikariCP connection pool. In case of PostgreSQL, where a database can have multiple schemas, we use the default postgres database and auto-create two schemas, authors and books. For this we rely on Flyway, which is capable to create the missing schemas.

Key points:

for Maven, in pom.xml, add the Flyway dependency
remove (disable) spring.jpa.hibernate.ddl-auto or set it to validate
in application.properties, configure the JDBC URL for books as jdbc:postgresql://localhost:5432/postgres?currentSchema=books and for authors as jdbc:postgresql://localhost:5432/postgres?currentSchema=authors
in application.properties, set spring.flyway.enabled=false to disable default behavior
programmatically create two DataSource, one for books and one for authors
programmatically create two FlywayDataSource, one for books and one for authors
programmatically create two EntityManagerFactory, one for books and one for authors
for books, place the migration SQLs files in db\migration\books
for authors, place the migration SQLs files in db\migration\authors
 */
