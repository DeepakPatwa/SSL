//package com.stackroute.pie.service;
//
//import com.stackroute.pie.domain.Diseases;
//import com.stackroute.pie.domain.Policy;
//import com.stackroute.pie.repository.SearchRepository;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.internal.verification.VerificationModeFactory.times;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//public class SearchServiceImplTest {
//
//    private Optional optional;
//    @Mock
//    SearchRepository searchRepository;
//
//    @InjectMocks
//    SearchServiceImpl searchService;
//
//    Policy policy;
//    List<Policy> policy1;
//    Diseases disease1,disease2;
//    List<Diseases> diseaseList;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        policy1 = new ArrayList<>();
//        diseaseList=new ArrayList<>();
//        disease1=new Diseases("cancer");
//        disease2=new Diseases("diabetes");
//        System.out.println(disease1.getDiseaseName());
//        diseaseList.add(disease1);
//        diseaseList.add(disease2);
//        policy = new Policy(
//                 1,
//                "JeevanSathi","MaxBupa",
//                 "self",diseaseList,
//
//         0,
//                 5,
//                 400,
//               6000,
//               5000,
//               20,
//                 60,
//                 null,
//              null,
//                 null,
//                "maxbupa",
//             "maxpuba"
//    );
//        policy1.add(policy);
////        track1.add(new Track("track2","comment2"));
//        optional = Optional.of(policy);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void getAllPolicies() {
//        when(searchRepository.findAll()).thenReturn(policy1);
//       List<Policy> actual=searchService.getAllPolicies("cancer");
//       assertEquals(policy1,actual);
//        verify(searchRepository, Mockito.times(1)).findAll();
////        verifyNoMoreInteractions(trackService);
//    }
//
//
////    @Test
////    public void getAllPolicies1() {
////        when(searchRepository.findAll()).thenReturn(policy1);
////        List<Policy> actual=searchService.getAllPolicies("diabetes");
////        assertEquals(policy1,actual);
////        verify(searchRepository, Mockito.times(1)).findAll();
//////        verifyNoMoreInteractions(trackService);
////    }
//
//    @Test
//    public void savePolicy() {
//        when(searchRepository.save(policy)).thenReturn(policy);
//        Policy actual = searchService.savePolicy(policy);
//        assertEquals(policy, actual);
//        verify(searchRepository, times(1)).save(policy);
//
//    }
//}