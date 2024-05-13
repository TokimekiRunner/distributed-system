import java.util.*;
import java.io.*;
import java.io.Serializable;
import java.rmi.RemoteException;
class Book  implements Serializable{
	public int book_id;
	public String book_name;
	public int book_num;
    public Book(int ID, String name,int num){
		this.book_id = ID;
		this.book_name = name;
		this.book_num = num;
    }
	public int getID() {
		return book_id;
	}
	public void lendbook(){book_num--;}

	public void returnbook(){book_num++;}
	public String getName() {
		return book_name;
	}
	public int getBook_num(){return  book_num;}
	public String getInfo() {
		return  "ID: " + book_id 
			+ " name:" + book_name +" Exist num:"+ book_num+"\n";
	}
	public void showInfo() {
		System.out.println(getInfo());
	}

}
class BookList implements Serializable {
	ArrayList<Book> booklist = new ArrayList<Book>();
	public String getInfo() {
		String info ="";
		for (int i = 0; i < booklist.size(); i++) {
			info += ("ID: " + booklist.get(i).getID()
			+ " name:" + booklist.get(i).getName() + " Exist num:" + booklist.get(i).getBook_num() +"\n");
		}
		return info;
	}
	public void showInfo() {
		System.out.println(getInfo());
	}
}
class BookMethodImpl extends java.rmi.server.UnicastRemoteObject implements BookMethod{
	BookMethodImpl() throws RemoteException  {
		super();
	}
	ArrayList<Book> books = new ArrayList<Book>();
	BookList query = new BookList();

	public void clearData()throws RemoteException{
		books.clear();
	}
	@Override
	public void initData() throws RemoteException, FileNotFoundException {
		File file = new File("content.txt");
		Scanner input = new Scanner(file);	
		while (input.hasNext()) {
			int id = input.nextInt();
			String name = input.next();
			int num = input.nextInt();
			books.add(new Book(id, name,num));
		}
		input.close();
	}
	@Override
	public void update() throws RemoteException, FileNotFoundException {
		File file = new File("content.txt");
		PrintWriter output = new PrintWriter(file);
		for (int i = 0; i < books.size(); i++) {
			output.print(books.get(i).getID() + " ");
			output.print(books.get(i).getName()+ " ");
			output.println(books.get(i).getBook_num());
		}
		output.close();
	}
	@Override
	public boolean add(Book b) throws RemoteException, FileNotFoundException{
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getID() == b.getID()) {
				return false;
			}
		}
		books.add(b);
		update();
		return true;
	}
	@Override
	public Book queryByID(int bookID) throws RemoteException{
		System.out.println("hello");

		Book b = null;
		for (int i = 0; i < books.size(); i++) {
			System.out.println(i);
			if (books.get(i).getID() == bookID) {
				b = books.get(i);
			//	b.lendbook();
				return b;
			}
		}
		return null;
	}
	@Override
	public String queryByName(String name) throws RemoteException {
		String tmp = "Not exist";
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getName().indexOf(name) != -1) {
				tmp = books.get(i).getName() + ":now exist in " + books.get(i).getBook_num();
				return tmp;
			}
		}
		return name + " " + tmp;
	}

	public String borrowByName(String name) throws RemoteException, FileNotFoundException{
		String tmp = "Not exist";
		for (int i = 0; i < books.size(); i++) {
			if(books.get(i).getName().indexOf(name)!=-1){
				if (books.get(i).book_num>0){
					tmp = books.get(i).getName();
					books.get(i).lendbook();}
			}
		}
		update();

		return "borrow "+tmp;

	}
	public String returnByName(String name)  throws RemoteException, FileNotFoundException{
		for (int i = 0; i < books.size(); i++) {
			if(books.get(i).getName().indexOf(name)!=-1){
					books.get(i).returnbook();
					update();
					return "return "+name;

			}
		}
		Book newbook = new Book(books.size()+1,name,1);
		books.add(newbook);
		update();
		return "return "+name;

	}
	@Override
	public boolean delete(int bookID) throws RemoteException, FileNotFoundException {
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getID() == bookID) {
				books.remove(books.get(i));  
				update();
				return true;
			}
		}
		return false;
	}
	@Override
	public String booksInfo() throws RemoteException {
		String info = "********current books**********\n";
		for (int i = 0; i < books.size(); i++) {
			info += ("ID: " + books.get(i).getID() 
			+ " name:" + books.get(i).getName() + " Exist num:" + books.get(i).getBook_num() +"\n");
		}
		return info;
	}
	@Override
	public void showAllTheBooks() throws RemoteException {
		System.out.println(booksInfo());
	}
}
