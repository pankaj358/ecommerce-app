package com.ecommerce.app.controller.service;

import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.domain.User;
import com.ecommerce.app.model.UserModel;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.service.UserServiceImpl;
import com.ecommerce.app.service.ValidationService;
import com.ecommerce.app.util.EcommerceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EcommerceUtil ecommerceUtil;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setUP() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createUser() {
        UserModel expected = userModel();

        User userDomain = User.builder()
                .userId(UUID.randomUUID())
                .userType(expected.getUserType())
                .lastName(expected.getLastName())
                .firstName(expected.getFirstName())
                .build();
        Mockito.when(validationService.isValidUser(expected))
                .thenReturn(true);
        Mockito.when(objectMapper.convertValue(expected, User.class)).thenReturn(userDomain);
        Mockito.when(userRepository.save(userDomain)).thenReturn(userDomain);
        Mockito.when(objectMapper.convertValue(userDomain, UserModel.class)).thenReturn(expected);
        UserModel actual = userService.create(expected);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(validationService, Mockito.times(1))
                .isValidUser(Mockito.any(UserModel.class));
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
       Mockito.verify(objectMapper, Mockito.times(1))
               .convertValue(userDomain, UserModel.class);
       Mockito.verify(objectMapper, Mockito.times(1))
               .convertValue(expected, User.class);
    }



    @Test
    public void test_fetchUser()
    {
        UUID userId = UUID.randomUUID();
        User user = User.builder().userId(userId).build();
        UserModel expected = userModel();
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        Mockito.when(ecommerceUtil.toUserModel(user)).thenReturn(expected);
        UserModel actual = userService.fetch(userId.toString());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(Mockito.any(UUID.class));
        Mockito.verify(ecommerceUtil,Mockito.times(1))
                .toUserModel(Mockito.any(User.class));
    }

    @Test
    public void test_fetchUserAll()
    {
        String userId = UUID.randomUUID().toString();
        List<UserModel> list = new ArrayList<>();
        UserModel userModel = userModel();
        userModel.setUserId(UUID.fromString(userId));
        list.add(userModel);
        List<User> domainList = new ArrayList<>();
        domainList.add(User.builder().userId(userModel.getUserId()).userType(UserType.BUYER.getText()).build());
        Mockito.when(userRepository.findAll()).thenReturn(domainList);
        List<UserModel> actual = userService.fetchAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(list.size(), actual.size());
        Mockito.verify(ecommerceUtil, Mockito.times(domainList.size()))
                .toUserModel(Mockito.any(User.class));
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }


    private UserModel userModel() {
        return UserModel.builder()
                .userType(UserType.BUYER.getText())
                .firstName("tom")
                .lastName("tom")
                .build();
    }
}
