package Service;

import Dao.UserDao;
import Modal.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    static public UserDao userDao = new UserDao();

    static public ArrayList<Account> accounts = (ArrayList<Account>) userDao.selectAllAccounts();
    static public Account findAccount(int id){
        for(int i=0;i<accounts.size();i++){
            if(accounts.get(i).getId()==id){
                return accounts.get(i);
            }
        }
        return null;
    }
    static public int findIdAccountByName(String name){
        for(int i=0;i<accounts.size();i++){
            if(accounts.get(i).getUserName().equals(name)){
                return accounts.get(i).getId();
            }
        }
        return 0;
    }
}
