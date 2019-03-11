package com.stackroute.pie.service;

import com.mongodb.DBObject;
import com.stackroute.pie.domain.InsurerPolicy;
import com.stackroute.pie.repository.ExternalDbRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;


@Service
public class ExternalDbServiceImpl implements ExternalDbService {


    @Autowired
    public ExternalDbServiceImpl(ExternalDbRepository externalDbRepository) {
        this.externalDbRepository = externalDbRepository;
    }

    @Autowired
    private ExternalDbRepository externalDbRepository;


    @Override
    public List<InsurerPolicy> getPolicies(String insurerName) throws ClassNotFoundException, SQLException, UnsupportedEncodingException, JSONException {

        String origFile = null;

//        String dbURL = "jdbc:mysql://172.23.239.178:3306/insurerFinal";
//        String dbUser = "fish";
//        String dbPass = "12345";

        String dbURL = "jdbc:mysql://172.17.0.2:3306/insurerFinal";
        String dbUser = "root";
        String dbPass = "root123";

        Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

        System.out.println(conn);

        Statement st = conn.createStatement();

        Blob xmlfile = null;
        String query = "Select * from " + insurerName;
        ResultSet rs = st.executeQuery(query);

        // System.out.println(rs.first()+";;;;;;;;");
        byte[] bdata = null;
        InputStream binaryStream = null;
        while (rs.next()) {
            System.out.println("###########");
            xmlfile = rs.getBlob(1);
           // System.out.println(xmlfile);
            binaryStream = xmlfile.getBinaryStream(1, xmlfile.length());
            //System.out.println(binaryStream + "**********");
            String str = binaryStream.toString();
            bdata = str.getBytes();
            origFile = new String(bdata, "UTF-8");

        }

        Scanner s = new Scanner(binaryStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        //System.out.print("*****" + result);
        DBObject dbObject = null;

        JSONObject obj = XML.toJSONObject(result);
        String stringToBeInserted = obj.toString(4);
        System.out.println("%%%%%%%%%%%%"+stringToBeInserted);
        List<InsurerPolicy> policies = new ArrayList<InsurerPolicy>();
        Iterator<String> keys = obj.keys();
        String key = keys.next();
        JSONArray name=(JSONArray)obj.get("policy");

        ArrayList<InsurerPolicy> police = new ArrayList<InsurerPolicy>();

        for(int i=0;i<name.length();i++) {

            String[] policystr = name.getJSONObject(i).toString().split(",");

            InsurerPolicy policy1 = new InsurerPolicy();
            policy1.setPolicyName(policystr[0].split(":")[1].replace("\"",""));
            policy1.setPolicyTerm(Integer.parseInt(policystr[1].split(":")[1]));
            policy1.setWaitingPeriod(Integer.parseInt(policystr[2].split(":")[1]));
            policy1.setMaxSumInsured(Integer.parseInt(policystr[3].split(":")[1]));
            policy1.setInsurerName(policystr[4].split(":")[1].replace("\"",""));
            policy1.setGenderAvail(policystr[5].split(":")[1].replace("\"",""));
            policy1.setPolicyDescription(policystr[6].split(":")[1].replace("\"",""));
            policy1.setInsurerLicense(policystr[7].split(":")[1].replace("\"",""));
            policy1.setPolicyId(Integer.parseInt(policystr[8].split(":")[1]));
            policy1.setMaxAge(Integer.parseInt(policystr[9].split(":")[1]));
            policy1.setMinAge(Integer.parseInt(policystr[10].split(":")[1]));
            policy1.setPolicyType(policystr[11].split(":")[1].replace("\"",""));
            policy1.setMinSumInsured(Integer.parseInt(policystr[12].split(":")[1].replace("\"","").replace("}","")));
            policy1.setUniqueId(policy1.getInsurerName()+policy1.getPolicyName());
            policy1.setInsuredList(Arrays.asList(new String[]{"anusha", "manasa"}));



            police.add(policy1);

        }

        Iterator i = police.iterator();
        while (i.hasNext()){
            System.out.println(i.next());
        }

//        System.out.println("insname"+ insurerName);

//        List<InsurerPolicy> policyList = externalDbRepository.findByInsurerName(insurerName).get();
//        System.out.println("@@@@@@@@@@@@@@@@"+insurer1);

//        if(insurer1.getPolicies() == null){
//            policies.addAll(police);
//        }
//        else {
//            policies.addAll(insurer1.getPolicies());
//            policies.addAll(police);
//        }
//        .setPolicies(policies);
//        externalDbRepository.deleteByInsurerName(insurerName);
//        externalDbRepository.save(insurer1);
        for(InsurerPolicy policy: police)
        externalDbRepository.save(policy);
        return police;

    }

}
