package com.stackroute.pie.service;
import com.stackroute.pie.domain.InsurerPolicy;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

 public interface ExternalDbService {

    List<InsurerPolicy> getPolicies(String insurerName) throws ClassNotFoundException, SQLException, UnsupportedEncodingException, JSONException;


}
