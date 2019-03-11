//package com.stackroute.pie.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stackroute.pie.domain.Diseases;
//import com.stackroute.pie.domain.Policy;
//import com.stackroute.pie.service.SearchServiceImpl;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(SearchController.class)
//public class SearchControllerTest {
//    @Autowired
//    private MockMvc mockMVC;
//
//    @MockBean
//    private SearchServiceImpl searchService;
//
//    @InjectMocks
//    private SearchController searchController;
//
//    private Policy policy;
//    List<Policy>policy1;
//    Diseases disease1;
//    Diseases disease2;
//    List<Diseases> diseaseList;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        mockMVC = MockMvcBuilders.standaloneSetup(searchController).build();
//        policy1 = new ArrayList<>();
//        diseaseList=new ArrayList<>();
//        disease1=new Diseases("cancer");
//        disease2=new Diseases("diabetes");
//        System.out.println(disease1.getDiseaseName());
//        diseaseList.add(disease1);
//        diseaseList.add(disease2);
//        policy = new Policy(   1,
//                "JeevanSathi","MaxBupa",
//                "self",diseaseList,
//
//                0,
//                5,
//                400,
//                6000,
//                5000,
//                20,
//                60,
//                null,
//                null,
//                null,
//                "maxbupa",
//                "maxpuba");
//    }
//    private static String jsonToString(final Object obj) throws JsonProcessingException {
//        String result;
//        try {
//
//            final ObjectMapper mapper = new ObjectMapper();
//            final String jsonContent = mapper.writeValueAsString(obj);
//            result = jsonContent;
//        } catch (JsonProcessingException e) {
//            result = "Json processing error";
//        }
//        return result;
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void savePolicy() throws Exception {
//        this.mockMVC.perform(post("/api/v1/policy")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(jsonToString(policy)))
//                .andExpect(status().isCreated());
//
////        verify(searchService, Mockito.times(1)).savePolicy(Mockito.any(Policy.class));
////        verifyNoMoreInteractions(searchService);
//
//    }
//
//    @Test
//    public void getPolicies() throws Exception {
//        String uri = "/api/v1/policies/cancer";
//        mockMVC
//                .perform(get(uri)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(jsonToString(policy)))
//                .andExpect(status().isOk());
//    }
//}