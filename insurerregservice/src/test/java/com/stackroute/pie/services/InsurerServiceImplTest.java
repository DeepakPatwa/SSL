package com.stackroute.pie.services;


import com.stackroute.pie.domain.Insurer;
import com.stackroute.pie.domain.Policy;
import com.stackroute.pie.repository.InsurerRepository;
import kafka.controller.LeaderIsrAndControllerEpoch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InsurerServiceImplTest {

    private Optional optional;
    @Mock
    InsurerRepository insurerRepository;

    @InjectMocks
    InsurerServiceImpl insurerService;

    Policy policy;
    List<Policy> policy1;
    List<Policy> newPolicy;
    Insurer insurer1;
//    String  insurerName = "Apolo";

//    diseaseList=new ArrayList<>();
//    disease1=new Disease("cancer");
//    disease2=new Disease("diabetes");
//            System.out.println(disease1.getDiseaseName());
//            diseaseList.add(disease1);
//            diseaseList.add(disease2);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        policy1 = new ArrayList<>();

        insurer1 =new Insurer(  "manasa",
                "reliance123",
               "reliance123@gmail.com",
                "reliance123",
                "bangalore",
               "fav food",
                "foooooooooood",policy1);

        policy = new Policy(
                1,
                "manasa",
                "jeevan sathi",


                50000,
                100000,
                40,
                20,
                80,
                3

        );

        policy1.add(policy);

        optional = Optional.of(policy);
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void addNewPolicy() {
        when((insurerRepository.findByInsurerName("manasa")).get().getPolicies()).thenReturn(policy1);
        policy1.add(policy);
        insurer1.setPolicies(policy1);
        when(insurerRepository.save(insurer1)).thenReturn(insurer1);
         Insurer actual = insurerService.addNewPolicy(policy);
        assertEquals(insurer1, actual);
    }




//    @Test
//    public void getPolicies() {
//
//        when(insurerRepository.findByInsurerName("manasa")).thenReturn(insurer1);
//       // when((insurerRepository.findByInsurerName("manasa")).get().getPolicies()).thenReturn(policy1);
//
//        List<Policy> actual=insurerRepository.getPolicies();
//        assertEquals(policy1,actual);
//        verify(searchRepository, Mockito.times(1)).findAll();
//
//    }
//
//    @Test
//    public void deletePolicy() {
//    }

//    @Test
//    public void addInsurer() {
//
//        when(insurerRepository.save(insurer1)).thenReturn(insurer1);
//        Policy actual = insurerRepository.addInsurer(insurer1);
//        assertEquals(insurer1, actual);
//        verify(insurerRepository, times(1)).save(insurer1);
//
//
//    }
}