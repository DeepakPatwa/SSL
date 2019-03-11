package com.stackroute.pie.services;

import com.stackroute.pie.domain.Request;
import com.stackroute.pie.domain.Insured;
import com.stackroute.pie.repository.UserRepository;
import com.stackroute.pie.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class InsuredDetailsServiceImplTest {

    private Optional optional;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userProfileService;
    private List<Request> requestList = new ArrayList<>();
    private Insured insured;
    private int userId = 0;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        requestList.add(0,new Request(2,"acc","xyz","abc"));
        insured = new Insured(1,requestList);
        optional = Optional.of(insured);
    }
    @Test
    public void getProfileSuccess() throws UserNotFoundException,Exception {
        when(userRepository.existsByUsername(insured.getUsername())).thenReturn(true);
        when(userRepository.findByUsername(insured.getUsername())).thenReturn(optional);
        Insured expectedOutput = userProfileService.getProfile(insured.getUsername());
        assertEquals(optional.get(),expectedOutput);
    }
    @Test
    public void updateProfileSuccess() throws UserNotFoundException,Exception {

        when(userRepository.existsByUsername(insured.getUsername())).thenReturn(true);
        when(userRepository.findByUsername(insured.getUsername())).thenReturn(optional);
        Insured actual = userProfileService.updateProfile(insured.getUsername(),insured);
        assertEquals(insured,actual);
    }
}
