package main;

import bookitem.Book;
import cart.Cart;
import cart.CartItem;
import exception.CartException;
import member.Admin;
import member.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;


public class WelcomeBookMarket {
    static Cart cart = new Cart();
    static User user; // user


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userName; // name
        int userMobile; // phoneNum
        int numberSelection; // menu number
        ArrayList<Book> bookInfoList;

        System.out.println("Book Market 고객 정보 입력");
        System.out.print("당신의 이름을 입력하세요 : "); // input name
        userName = input.next();
        System.out.print("연락처를 입력하세요 : "); // input phoneNum
        userMobile = input.nextInt();

       user = new User(userName,userMobile);


        boolean quit = false; // loop control


        while (!quit) {
            menuIntroduction();
            try{
                System.out.print("메뉴 번호를 선택해주세요");
                numberSelection = input.nextInt();
                if (numberSelection < 1 || numberSelection > 9) {
                    System.out.println("1부터 8까지의 숫자를 입력하세요.");
                } else {
                    switch (numberSelection) {
                        case 1:
                            // user info
                            menuGuestInfo();
                            break;
                        case 2:
                            // view item in cartList
                            menuCartItemList();
                            break;
                        case 3:
                            // clear cart
                            menuCartClear();
                            break;
                        case 4:
                            // add item in cart
                            bookInfoList = new ArrayList<>(); // book instance list
                            menuCartAddItem(bookInfoList);
                            break;
                        case 5:
                            // decrease number of item in cart
                            menuCartRemoveItemCount();
                            break;
                        case 6:
                            // remove item in cart
                            menuCartRemoveItem();
                            break;
                        case 7:
                            // print bill
                            menuCartBill();
                            break;
                        case 8:
                            // exit
                            menuExit();
                            quit = true;
                            break;
                        case 9:
                            // check admin info
                            menuAdminLogin();
                            break;
                    }

                }
            }catch(CartException e){
                System.out.println(e.getMessage());
                quit = true;

            }
            catch(Exception e){
                System.out.println("잘못된 메뉴 선택으로 종료합니다.");
                quit = true;
            }



        }
    }



    // print menu info
    public static void menuIntroduction() {
        System.out.println("***************************************************");
        System.out.println("\t\t\t\t" + "Book Market Menu");
        System.out.println("***************************************************");
        System.out.println(" 1. 고객 정보 확인하기 \t4. 장바구니에 항목 추가하기");
        System.out.println(" 2. 장바구니 상품 목록 보기\t5. 장바구니의 항목 수량 줄이기");
        System.out.println(" 3. 장바구니 비우기 \t6. 장바구니의 항목 삭제하기");
        System.out.println(" 7. 영수증 표시하기 \t8. 종료");
        System.out.println(" 9. 관리자 로그인");
        System.out.println("***************************************************");

    }
    // print guestInfo
    public static void menuGuestInfo() {
        System.out.println("현재 고객 정보 : ");

        System.out.println("이름 : " + user.getName() + ", 연락처 : " +
                user.getPhone());
    }

    // print Cart ItemList
    public static void menuCartItemList() {
        if (Cart.cartCount >= 0) {
            cart.printCart();
        }
    }

    public static void menuCartClear() throws CartException{
        if (Cart.cartCount == 0) {
        //System.out.println("장바구니에 항목이 없습니다");
            throw new CartException("장바구니에 항목이 없습니다");
        } else {
            System.out.println("장바구니에 모든 항목을 삭제하겠습니까? Y | N ");
            Scanner input = new Scanner(System.in);
            String str = input.nextLine();
            if (str.equalsIgnoreCase("Y")) {
                System.out.println("장바구니에 모든 항목을 삭제했습니다");
                cart.deleteBook();
            }
        }
    }

    public static void menuCartAddItem(ArrayList<Book> book) {
        bookList(book);

        cart.printBookList(book);
        boolean quit = false;
        while (!quit){
            Scanner input = new Scanner(System.in);
            System.out.print("장바구니에 추가할 도서의 ID를 입력하세요:");
            String inputStr = input.nextLine();
            boolean flag = false; // 일치 여부
            int numId = -1; // 인덱스 번호
            for (int i = 0; i < book.size(); i++) {
            // 입력한 도서 ID 와 저장되어 이쓴 도서 정보의 ID 가 일치하면
            // 인덱스 번호와 일치 여부 변수의 값을 변경한다.
                if (inputStr.equals(book.get(i).getBookId())) {
                    numId = i;
                    flag = true;
                    break;
                }
            }
            // 일치하면 장바구니 추가 여부를 묻는다.
            if (flag) {
                System.out.println("장바구니에 추가하겠습니까? Y | N ");
                inputStr = input.nextLine();
                if (inputStr.equalsIgnoreCase("Y") ) {
                    System.out.println(book.get(numId).getBookId() + " 도서가 장바구니에 추가되었습니다.");

                    if (!isCartInBook(book.get(numId).getBookId())) {
                        cart.insertBook(book.get(numId));
                    }
                }
                quit = true;

            } else
                System.out.println("다시 입력해 주세요");






        }

    }

    public static void menuCartRemoveItemCount() throws CartException  {
        if (Cart.cartCount == 0) {
            throw new CartException("장바구니에 항목이 없습니다");
        }
       CartItem book;
        // if book id is in cartitem,
        // bookNumber >= 2 , decrease number of book
        // bookNumber == 1, remove item in cart
        while(true){
        System.out.print("장바구니에서 수량을 줄일 도서의 Id를 입력하세요.");
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();

            Optional<CartItem> bookId = cart.cartItem.stream().
                    filter(item -> (item.getBookID().equals(str))).
                    findAny();
            if(!(bookId.isPresent())){
                System.out.println("잘못된 id를 입력해주셨습니다. 다시 입력해주세요.");
                continue;
            }

            book = bookId.get();
            break;
        }


            if(book.getQuantity()>=2){
                book.setQuantity(book.getQuantity()-1);
            }
            else {
                System.out.println("해당 도서는 현재 1개만 있습니다. 항목을 지웁니다.");
                cart.cartItem.remove(book);
            }




    }

    public static void menuCartRemoveItem() throws CartException {
        if (Cart.cartCount == 0) {
            throw new CartException("장바구니에 항목이 없습니다");
        }else {
            menuCartItemList();
            boolean quit = false;
            while (!quit) {
                System.out.print("장바구니에서 삭제할 도서의 ID 를 입력하세요 :");
                Scanner input = new Scanner(System.in);
                String str = input.nextLine();
                boolean flag = false;
                int numId = -1;
                for (int i = 0; i < Cart.cartCount; i++) {
                    if (str.equals(cart.cartItem.get(i).getBookID())) {
                        numId = i;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    System.out.println("장바구니의 항목을 삭제하겠습니까? Y | N ");
                    str = input.nextLine();
                    if (str.equalsIgnoreCase("Y") ){
                        System.out.println(cart.cartItem.get(numId).getBookID() + "도서가 장바구니에서 삭제되었습니다.");
                    }

                    quit = true;
                } else {
                    System.out.println("다시 입력해 주세요");
                }
            }
        }
    }



    public static void menuExit() {
        System.out.println("8. 종료");
    }
    public static void bookList(ArrayList<Book> book) {
        setFileToBookList(book);
    }
    // add book in Cart
    public  static  boolean isCartInBook(String bookId){

        return cart.isCartInBook(bookId);
    }

    // check admin info, add new book
    public  static void  menuAdminLogin(){
        System.out.println("관리자 정보를 입력하세요");
        Scanner input = new Scanner(System.in);
        System.out.print("아이디 : ");
        String adminId = input.next();
        System.out.print("비밀번호 : ");
        String adminPW = input.next();
        Admin admin = new Admin(user.getName(), user.getPhone());

        if (adminId.equals(admin.getId()) &&
                adminPW.equals(admin.getPassword())) {
            System.out.println("이름 : " + admin.getName() + ", 연락처 : " +
                    admin.getPhone());
            System.out.println("아이디 : " + admin.getId() + ", 비밀번호 : " +
                    admin.getPassword());
            String[] writeBook = new String[8];
            System.out.println("도서 정보를 추가하겠습니까? Y | N "); // input y add new book
            String str = input.next();
            if (str.equalsIgnoreCase("Y")) {
                Date date = new Date();
                SimpleDateFormat formatter = new
                        SimpleDateFormat("yyMMddhhmmss");  // Today`s date
                String strDate = formatter.format(date);
                writeBook[0] = "book" + strDate;  // info of new book
                System.out.println("도서 ID : " + writeBook[0]);
                System.out.println("번호 부분만 입력 예) 123-12-12345-01-1");
                System.out.print("ISBN : ");
                writeBook[1] = input.nextLine();
                writeBook[1] = "ISBN " + writeBook[1];
                System.out.print("도서명 : ");
                writeBook[2] = input.nextLine();
                System.out.print("가격(숫자) : ");
                writeBook[3] = input.nextLine();
                System.out.print("저자 : ");
                writeBook[4] = input.nextLine();
                System.out.print("설명 : ");
                writeBook[5] = input.nextLine();
                System.out.print("분야 : ");
                writeBook[6] = input.nextLine();
                System.out.print("출판일(예:2023/01/01): ");
                writeBook[7] = input.nextLine();
                try {                                                //received information in a book to save it
                    FileWriter fw = new FileWriter("book.txt", true);
                    for (int i = 0; i < 8; i++) {
                        fw.write(writeBook[i] + "\n");
                    }

                    fw.close();

                    System.out.println("새 도서 정보가 저장되었습니다.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("이름 : " + admin.getName() + ", 연락처 : " +
                        admin.getPhone());
                System.out.println("아이디 : " + admin.getId() + ", 비밀번호 : " +
                        admin.getPassword());
            }




        } else {
            System.out.println("관리자 정보가 일치하지 않습니다.");
        }


    }
    public static void menuCartBill() throws CartException{
        if(Cart.cartCount == 0){
            throw new CartException("장바구니에 항목이 없습니다");
        }else{
            System.out.println("배송받을 분은 고객정보와 같습니까? Y | N ");
            Scanner input = new Scanner(System.in);
            String str = input.nextLine();
            if(str.equalsIgnoreCase("Y")){
                System.out.println("배송지를 입력해주세요");
                String address = input.nextLine();
                // after input shipping date, input order info in printBill

                printBill(user.getName(), String.valueOf(user.getPhone()),
                        address);
            }
            // input n, input new info
            else {
                System.out.print("배송받을 고객명을 입력하세요 ");
                String name = input.nextLine();
                System.out.print("배송받을 고객의 연락처를 입력하세요 ");
                String phone = input.nextLine();
                System.out.print("배송받을 고객의 배송지를 입력해주세요 ");
                String address = input.nextLine();
// 주문 처리 후 영수증 출력 메서드 호출
                printBill(name, phone, address);
            }

        }
    }
    // print order info(name, phone, address, shipping date)
    public static void printBill(String name, String phone, String address){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);
        System.out.println();
        System.out.println("---------------배송 받을 고객 정보----------------");
        System.out.println("고객명 : " + name + " \t\t 연락처 : " + phone);
        System.out.println("배송지 : " + address + "\t 발송일 : " + strDate);
        // print item in cart
        cart.printCart();
        // calculate item
        int sum = 0;
        for (int i = 0; i < Cart.cartCount; i++) {
            sum += cart.cartItem.get(i).getTotalPrice();
        }
        System.out.println("\t\t\t 주문 총금액 : " + sum + "원\n");
        System.out.println("----------------------------------------------");
        System.out.println();

    }
    // check number of book

    public static void  setFileToBookList(ArrayList<Book> bookList){
        try {
            FileReader fr = new FileReader("book.txt");
            BufferedReader reader = new BufferedReader(fr);

            String bookId;
            String[] readBook = new String[8]; // number of book`s info
            while ((bookId = reader.readLine()) != null) {
                if (bookId.contains("book")) {
                    readBook[0] = bookId;
                    readBook[1] = reader.readLine();
                    readBook[2] = reader.readLine();
                    readBook[3] = reader.readLine();
                    readBook[4] = reader.readLine();
                    readBook[5] = reader.readLine();
                    readBook[6] = reader.readLine();
                    readBook[7] = reader.readLine();
                }
                Book book = new Book(readBook[0], readBook[1], readBook[2],
                        Integer.parseInt(readBook[3]), readBook[4], readBook[5], readBook[6],
                        readBook[7]);
                bookList.add(book);
            }
            reader.close();
            fr.close();


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
