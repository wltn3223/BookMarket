package cart;

import bookitem.Book;

import java.util.ArrayList;

public interface CartInterface {
    void printBookList(ArrayList<Book> bookList) ; // print book list
    boolean isCartInBook(String id); // // check overlap
                                     // if bookID in cartItemList, increase number of same booK
    void insertBook(Book p); // register books info in CartItem
    void removeCart(int numId); // remove, num :numId
    void deleteBook(); // remove all;
}
