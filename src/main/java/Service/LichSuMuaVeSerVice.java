package Service;

import Dao.LichSuMuaVeDao;
import Modal.LichSuMuaVe;

import java.util.ArrayList;
import java.util.List;

public class LichSuMuaVeSerVice {
    static public LichSuMuaVeDao lichSuMuaVeDao = new LichSuMuaVeDao();

    static public List<LichSuMuaVe> ves= lichSuMuaVeDao.selectAllVe();

    static public List listVeFromLSMVByIdUser(int id){
        ArrayList<LichSuMuaVe> vesLSMVByIdUser = new ArrayList<>();
        for (int i = 0; i < ves.size(); i++) {
            if (ves.get(i).getAccount().getId() == id){
                vesLSMVByIdUser.add(ves.get(i));
            }
        }
        return vesLSMVByIdUser;
    }
    static public int totalMoneyPaymentBuyId(int id){
        int totalMoney=0;
        for(int i=0;i<ves.size();i++){
            if(ves.get(i).getAccount().getId()==id){
                totalMoney += ves.get(i).getFilm().getGiaPhim();
                if(totalMoney>500000){
                    totalMoney =totalMoney-(5/100)*totalMoney;
                    return totalMoney;
                }
            }
        }
        return totalMoney;
    }
}
