package com.ecommerce.app.service;

import com.ecommerce.app.contant.ErrorMessages;
import com.ecommerce.app.domain.User;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.UserModel;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.util.EcommerceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ValidationService validationService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private EcommerceUtil ecommerceUtil;

    @Override
    public UserModel create(UserModel userModel) {
        validationService.isValidUser(userModel);
        User user = objectMapper.convertValue(userModel, User.class);
        User saveUser = userRepository.save(user);
        userModel = objectMapper.convertValue(saveUser, UserModel.class);
        return userModel;
    }

    @Override
    public List<UserModel> fetchAll() {
        List<User> userList = (List<User>) userRepository.findAll();
        List<UserModel> modelList = userList.stream().map(user ->
                ecommerceUtil.toUserModel(user)
        ).collect(Collectors.toList());
        return modelList;
    }

    @Override
    public UserModel fetch(String userId) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(userId));
        if (optionalUser.isPresent()) {
            return ecommerceUtil.toUserModel(optionalUser.get());
        }
        throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND.getText(), userId));
    }
}
