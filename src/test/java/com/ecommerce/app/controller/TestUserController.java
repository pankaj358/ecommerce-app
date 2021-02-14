package com.ecommerce.app.controller;

import com.ecommerce.app.model.UserModel;
import com.ecommerce.app.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUserController {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createUser() {
        UserModel expected = userModel();
        Mockito.when(userService.create(expected))
                .thenReturn(expected);

        ResponseEntity<UserModel> actual = userController.create(expected);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(userService, Mockito.times(1)).
                create(Mockito.any(UserModel.class));
    }

    @Test
    public void test_fetchUser()
    {
        UserModel expected = userModel();
        UUID userId = UUID.randomUUID();
        Mockito.when(userService.fetch(userId.toString())).thenReturn(expected);

        ResponseEntity<UserModel> actual = userController.fetch(userId.toString());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());

        Mockito.verify(userService, Mockito.times(1)).fetch(Mockito.anyString());

    }


    @Test
    public void test_fetchAllUser()
    {
       List<UserModel> expected = new ArrayList<>();
       expected.add(userModel());

       Mockito.when(userService.fetchAll()).thenReturn(expected);

       ResponseEntity<List<UserModel>> actual = userController.fetchAll();

       Assertions.assertNotNull(actual);
       Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
       Assertions.assertEquals(expected.size(), actual.getBody().size());

       Mockito.verify(userService, Mockito.times(1)).fetchAll();

    }


    private UserModel userModel() {
        return UserModel.builder().build();
    }
}
