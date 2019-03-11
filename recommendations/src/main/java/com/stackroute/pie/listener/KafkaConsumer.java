//package com.stackroute.pie.listener;
//
//import com.stackroute.pie.domain.*;
//import com.stackroute.pie.repository.RecommendationsRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumer {
//
//
//    @Autowired
//    RecommendationsRepo recommendationsRepo;
//
//    String consumed="Consumed JSON Message";
//
//
//
//    //consuming data from kafka & setting it to commonauth
//    @KafkaListener(topics = "userregg_json", groupId = "group_json", containerFactory = "insuredKafkaListenerFactory")
//    public void consumeJson(Insured insured) {
//
//
//
//        recommendationsRepo.newInsured(insured.getInsuredId(), insured.getUsername(), insured.getGender(), insured.getAge(), insured.getExistingDisease(), insured.getNumberOfDependants());
//
//
//    }
//
//    @KafkaListener(topics = "company_1_json", groupId = "group_json", containerFactory = "insurerKafkaListenerFactory")
//    public void consumeJson(Insurer insurer) {
//
//
//        recommendationsRepo.newInsurer(insurer.getInsurerId(), insurer.getInsurerName(), insurer.getInsurerLicense());
//
//
//    }
//
//    @KafkaListener(topics = "policy_added", groupId = "group_json", containerFactory = "policyKafkaListenerFactory")
//    public void consumeJson1(Policy policy) {
//
//
//        recommendationsRepo.newPolicy(policy.getUniqueId(),policy.getPolicyId(), policy.getInsurerName(),policy.getInsurerLicense(), policy.getPolicyName(), policy.getMinAge(), policy.getMaxAge(), policy.getGenderAvail(), policy.getDiseasesCovered(), policy.getPolicyType(),policy.getPolicyDescription());
//
//        recommendationsRepo.insurerPolicy(policy.getInsurerName(), policy.getPolicyId());
//    }
//
//
//    @KafkaListener(topics = "family_json", groupId = "group_json", containerFactory = "familyKafkaListenerFactory")
//    public void consumeJson2(Insured insured) {
//
//
//        for (FamilyMembers familyMembers1: insured.getFamilyMembers()) {
//            recommendationsRepo.newFamilyMembers(familyMembers1.getUsername(), familyMembers1.getMemberName(), familyMembers1.getMemberAge(), familyMembers1.getRelation(), familyMembers1.getMemberGender());
//            recommendationsRepo.addDependant(familyMembers1.getMemberName(), insured.getUsername());
//        }
//    }
//}