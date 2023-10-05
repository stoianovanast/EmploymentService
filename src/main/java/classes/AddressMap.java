package classes;

import java.util.ArrayList;

public class AddressMap {
    private ArrayList<String> addressList;
    public AddressMap(ArrayList<String> addressList) {
        this.addressList = addressList;
    }
    public boolean isAddress(String address){
        String[] words = address.split(" ");
        boolean resultOfChecking = false;
        if(addressList.size() != 0){
            for (String string : addressList){
                if (string.equals(words[0])){
                    return true;
                }
            }
        }
        return resultOfChecking;
    }
}
