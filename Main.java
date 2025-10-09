import java.util.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private int copies;

    public Book(String title, String author, String isbn, int copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getCopies() {
        return copies;
    }

    public void addCopies(int count) {
        this.copies += count;
    }

    public boolean borrowBook() {
        if (copies > 0) {
            copies--;
            return true;
        }
        return false;
    }

    public void returnBook() {
        copies++;
    }

    @Override
    public String toString() {
        return title + " | author: " + author + " | ISBN: " + isbn + " | example: " + copies;
    }
}

class Reader {
    private String name;
    private String readerId;
    private List<Book> borrowedBooks = new ArrayList<>();

    public Reader(String name, String readerId) {
        this.name = name;
        this.readerId = readerId;
    }

    public String getName() {
        return name;
    }

    public String getReaderId() {
        return readerId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "reader: " + name + " (ID: " + readerId + ")";
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("+ book: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        books.removeIf(b -> b.getIsbn().equals(isbn));
        System.out.println("ISBN book delete: " + isbn);
    }

    public void registerReader(Reader reader) {
        readers.add(reader);
        System.out.println("subscribe: " + reader.getName());
    }

    public void removeReader(String readerId) {
        readers.removeIf(r -> r.getReaderId().equals(readerId));
        System.out.println("reader remove: " + readerId);
    }


    public void lendBook(String isbn, String readerId) {
        Book book = findBook(isbn);
        Reader reader = findReader(readerId);

        if (book != null && reader != null) {
            if (book.borrowBook()) {
                reader.borrowBook(book);
                System.out.println(reader.getName() + " taken: " + book.getTitle());
            } else {
                System.out.println("Book ready");
            }
        }
    }

    public void returnBook(String isbn, String readerId) {
        Book book = findBook(isbn);
        Reader reader = findReader(readerId);

        if (book != null && reader != null && reader.getBorrowedBooks().contains(book)) {
            book.returnBook();
            reader.returnBook(book);
            System.out.println(reader.getName() + " return: " + book.getTitle());
        } else {
            System.out.println("Wrong book is not find!.");
        }
    }

    private Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    private Reader findReader(String readerId) {
        for (Reader r : readers) {
            if (r.getReaderId().equals(readerId)) {
                return r;
            }
        }
        return null;
    }

    public void listBooks() {
        System.out.println("\n📚 books list:");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    public void listReaders() {
        System.out.println("\n👤 readers list:");
        for (Reader r : readers) {
            System.out.println(r);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        Book b1 = new Book("Java Basics", "John Doe", "111", 3);
        Book b2 = new Book("Design Patterns", "Erich Gamma", "222", 2);
        Book b3 = new Book("Clean Code", "Robert C. Martin", "333", 1);

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);

        Reader r1 = new Reader("Айдос", "R001");
        Reader r2 = new Reader("Мария", "R002");

        library.registerReader(r1);
        library.registerReader(r2);

        library.listBooks();
        library.listReaders();

        library.lendBook("111", "R001");
        library.lendBook("333", "R001");
        library.lendBook("333", "R002");

        library.returnBook("111", "R001");

        library.listBooks();
    }
}
