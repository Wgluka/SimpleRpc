package com.wgluka.connect.zookeeper.container;

import com.wgluka.connect.zookeeper.exception.AddressNotFoundException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataContainer {
    private List<String> addressList = new ArrayList<>();

    public void modifyAddressList(List<String> newAddressList) {
        addressList = Optional.ofNullable(newAddressList).orElse(addressList);
    }

    public String getAddress() {
        if (addressList.isEmpty()) {
            throw new AddressNotFoundException("address is empty.");
        }

        int length = addressList.size();
        SecureRandom random = new SecureRandom();
        int index = random.nextInt(length);
        return addressList.get(index);
    }
}
