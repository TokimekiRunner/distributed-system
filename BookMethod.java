import java.util.*;
import java.io.*;
import java.rmi.RemoteException;
public interface BookMethod extends java.rmi.Remote {
	void initData() throws RemoteException, FileNotFoundException;
	void clearData()throws RemoteException;
	void update() throws RemoteException, FileNotFoundException;
	boolean add(Book b) throws RemoteException, FileNotFoundException;
	Book queryByID(int bookID) throws RemoteException;
	String queryByName(String name) throws RemoteException;
	String borrowByName(String name) throws RemoteException, FileNotFoundException;
	String returnByName(String name) throws RemoteException, FileNotFoundException;
	boolean delete(int bookID) throws RemoteException, FileNotFoundException;
	String booksInfo() throws RemoteException;
	void showAllTheBooks() throws RemoteException;
}