//package com.stackroute.pie.listner;
//
////package com.stackroute.pie.listner;
//
//import com.stackroute.pie.domain.*;
////import com.stackroute.pie.repository.RoleRepository;
////import com.stackroute.pie.repository.CommonAuthRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import javax.swing.text.html.Option;
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class KafkaConsumer {
//
////    @Autowired
////    RoleRepository roleRepository;
////
////    @Autowired
////    CommonAuthRepository commonAuthRepository;
//
//
//    @KafkaListener(topics = "deepak", groupId = "group_id")
//    public void consume(String message) {
//        System.out.println("String wala method");
//        System.out.println("Consumed message1: " + message);
//    }
////    @KafkaListener(topics = "Kafka_Example_json", groupId = "group_json",
////            containerFactory = "userKafkaListenerFactory")
//
//    //consuming data from kafka & setting it to commonauth
//    @KafkaListener(topics = "userregg_json", groupId = "group_json", containerFactory = "userKafkaListenerFactory")
//    public void consumeJson(OptionalFields optionalFields) {
//
//        PortingRequest portingRequest = new PortingRequest();
//    }
////V
////    @KafkaListener(topics = "company_1_json", groupId = "group_json", containerFactory = "userKafkaListenerFactory")
////    public void consumeJson(Insurer user) {
////        System.out.println("yoyoyoyoyo");
////        System.out.println("Consumed JSON Message: " + user);
////
////
////        Set<Role> roles = new HashSet<>();
////
////
////        Role insurerRole = roleRepository.findByName(RoleName.ROLE_INSURER)
////                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Insured Role not find."));
////        roles.add(insurerRole);
////        user.setRoles(roles);
////        CommonAuth commonAuth = new CommonAuth();
////        commonAuth.setRoles(user.getRoles());
////        commonAuth.setEmail(user.getInsurerEmail());
////        commonAuth.setName(user.getInsurerName());
////        commonAuth.setPassword(user.getPassword());
////        commonAuth.setRoles(roles);
////        commonAuth.setUsername(user.getInsurerLicense());
////        commonAuthRepository.save(commonAuth);
////    }
////
////
////
////
////
////}
//
//}
