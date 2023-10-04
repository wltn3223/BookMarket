package cart;

import bookitem.Book;

import java.util.ArrayList;

public class Cart  implements  CartInterface{
    public ArrayList<CartItem> cartItem = new ArrayList<CartItem>();
    public static int cartCount = 0;

    public Cart() {
    }

    @Override
    public void printBookList(ArrayList<Book> bookList) {
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            System.out.print(book.getBookId() + " | ");
            System.out.print(book.getIsbn() + " | ");
            System.out.print(book.getName() + " | ");
            System.out.print(book.getUnitPrice() + " | ");
            System.out.print(book.getAuthor() + " | ");
            System.out.print(book.getDescription() + " | ");
            System.out.print(book.getCategory() + " | ");

            System.out.print(book.getReleaseDate());
            System.out.println("");
        }
    }
    @Override
    public boolean isCartInBook(String bookId){
        // check overlap
        // if bookID in cartItemList, increase number of same booK
        boolean flag = false;
        for(int i = 0; i < cartItem.size(); i++){
            if (bookId.equals(cartItem.get(i).getBookID())){
                cartItem.get(i).setQuantity(cartItem.get(i).getQuantity()+1);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void insertBook(Book book) {
        CartItem bookItem = new CartItem(book);
        cartItem.add(bookItem);
        cartCount = cartItem.size();

    }

    @Override
    public void removeCart(int numId) {
        cartItem.remove(numId);
        cartCount = cartItem.size();

    }

    @Override
    public void deleteBook() {
        cartItem.clear();
        cartCount = 0;

    }
    public void printCart() {
        System.out.println("장바구니 상품 목록 :");
        System.out.println("---------------------------------------------");
        System.out.println(" 도서 ID \t\t\t\t| 수량 \t| 합계");
        for( int i = 0; i < cartItem.size(); i++) {
            System.out.print(" " + cartItem.get(i).getBookID() + " \t| ");
            System.out.print(" " + cartItem.get(i).getQuantity() + " \t| ");
            System.out.print(" " + cartItem.get(i).getTotalPrice());
            System.out.println(" ");
        }
        System.out.println("---------------------------------------------");
    }
}
