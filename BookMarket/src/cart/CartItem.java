package cart;

import bookitem.Book;

public class CartItem {
    private Book itemBook; // books info
    private  String bookID; // book id
    private  int quantity; // number of book
    private  int totalPrice; // total price of books

    public CartItem() {
    }

    public CartItem(Book itemBook) {
        this.itemBook = itemBook;
        this.bookID = itemBook.getBookId();
        this.quantity = 1;
        updateTotalPrice();
    }

    public Book getItemBook() {
        return itemBook;
    }

    public void setItemBook(Book itemBook) {
        this.itemBook = itemBook;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
        this.updateTotalPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.updateTotalPrice();
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void updateTotalPrice() {
        this.totalPrice = this.itemBook.getUnitPrice() * this.quantity;
    }
}
