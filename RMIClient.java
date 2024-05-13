import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.io.Serializable;
public class RMIClient {
	
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, FileNotFoundException {     
        BookMethod method = (BookMethod)Naming.lookup("rmi://localhost:8989/BookMethod");
		method.initData();
		Scanner input = new Scanner(System.in);
		System.out.println(menu());
		int choose = input.nextInt();
		while (choose != 0) {
			switch(choose) {
				case 1:
					System.out.println("please input book_name to query it\n");
					String queryBookKeyword2 = input.next();
					String queryid = method.queryByName(queryBookKeyword2);
					System.out.println(queryid);
					break;
//						int newBookID = input.nextInt();
//						String newName = input.next();
//						Book newBook = new Book(newBookID, newName,0);
//						if (method.add(newBook)) {
//							System.out.println("add sucessful\n");
//						} else {
//							System.out.println("ID:" + newBookID + " pre_existing, add book failed\n");
//						}
				case 2: System.out.println("please input book_name to borrow it\n");
					String queryBookKeyword = input.next();
					//BookList list = method.queryByName(queryBookKeyword);
					String list = method.borrowByName(queryBookKeyword);
					if (list != null) {
						//System.out.println("*******book list *********\n");
						//list.showInfo();
						System.out.println(list);
					} else {
						System.out.println("this book isnot existing");
					}

//						if (queryid == null) {
//							System.out.println("this book isnot existing");
//						} else {
//							System.out.println("*******book list *********\n");
//							queryid.showInfo();
//						}
						break;
				case 3: System.out.println("please input book_name to return it\n");
					String queryBookKeyword1 = input.next();
					String queryid2 = method.returnByName(queryBookKeyword1);
					System.out.println(queryid2);
						break;
//				case 4: System.out.println("please input book_id to delete it\n");
//						int deleteID = input.nextInt();
//						if (method.delete(deleteID)) {
//							System.out.println("del sucessful\n");
//						} else {
//							System.out.println("del failed\n");
//						}
//						break;
				case 4: System.out.println(method.booksInfo());
						break;
				default: System.out.println("please input again!\n");
						break;
			}
			System.out.println(menu());
			choose = input.nextInt();
		}
		method.clearData();
	}
	public static String menu() {
		return "**********menu**********\n"
			+ "1.query book\n"
			+ "2.borrow Book by Name\n"
			+ "3.return Book By Name\n"
			+ "4.show All Books\n"
			+ "0.quit\n"
			+ "************************\n";
	}
}
