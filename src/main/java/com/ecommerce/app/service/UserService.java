package com.ecommerce.app.service;

import com.ecommerce.app.model.UserModel;

import java.util.List;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


public interface UserService {

    public UserModel create(UserModel userModel);

    public List<UserModel> fetchAll();

    public UserModel fetch(String userId);

}
