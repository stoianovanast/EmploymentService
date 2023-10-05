package classes;

import java.util.ArrayList;

public class AllAccount {
    private ArrayList<Passport> listAllAccount;
    public AllAccount(ArrayList<Passport> listAllAccount) {
        this.listAllAccount = listAllAccount;
    }
    public boolean isAccount(Passport passport){
        boolean resultOfChecking = false;
        if(listAllAccount.size() != 0){
            for (int i = 0; i < listAllAccount.size(); i++ ){
                if(passport.getSeria()!=null){
                    if(listAllAccount.get(i).getNumber() == passport.getNumber() &&
                            listAllAccount.get(i).getSeria().equals(passport.getSeria())){
                        return true;
                    }
                }else{
                    if(listAllAccount.get(i).getNumber() == passport.getNumber()){
                        return true;
                    }
                }
            }
        }
        return resultOfChecking;
    }
    public void markPassport(Passport passport){
        listAllAccount.add(passport);
    }
}
