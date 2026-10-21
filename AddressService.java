package com.shopsphere.service;
import com.shopsphere.dao.AddressDAO;
import com.shopsphere.model.Address;
import java.util.List;
public class AddressService {
    private final AddressDAO dao = new AddressDAO();
    public List<Address> getUserAddresses(Long userId) { return dao.findByUserId(userId); }
    public boolean addAddress(Address a) { return dao.create(a); }
    public boolean setDefaultAddress(Long userId, Long id) { return dao.setDefault(userId, id); }
    public boolean deleteAddress(Long id, Long userId) { return dao.delete(id, userId); }
}
