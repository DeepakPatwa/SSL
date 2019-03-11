package com.stackroute.pie.services;

import com.stackroute.pie.domain.PortingRequest;
import com.stackroute.pie.exceptions.RequestNotFoundException;
import com.stackroute.pie.repository.PortingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortingRequestServiceImpl implements PortingRequestService{
    private PortingRequestRepository requestRepository;
    private PortingRequest request2;
    @Autowired
    public PortingRequestServiceImpl(PortingRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    //To post a request
    public PortingRequest postRequest(PortingRequest request){

        List<PortingRequest> portingRequests = requestRepository.findAll();
        if(portingRequests.isEmpty()) {
            request.setPortingRequestId(1);
        }
        else {
            request.setPortingRequestId(portingRequests.get(portingRequests.size()-1).getPortingRequestId() + 1);
        }
        PortingRequest request1 = requestRepository.save(request);
        return request1;
    }

    //To edit the request details
    public PortingRequest updateRequest(PortingRequest request) throws RequestNotFoundException{
        PortingRequest request1;
        if(requestRepository.existsByPortingRequestId(request.getPortingRequestId()) ){
            requestRepository.deleteByPortingRequestId(request.getPortingRequestId());
            request1 = requestRepository.save(request);
            return request1;
        }
        else
            throw new RequestNotFoundException();
    }

    //To delete a request
    public PortingRequest deleteRequest(int requestId) throws RequestNotFoundException{
        Optional<PortingRequest> request1 = requestRepository.findByPortingRequestId(requestId);
        if(requestRepository.existsByPortingRequestId(requestId)){
            requestRepository.deleteByPortingRequestId(requestId);
        }
        else
            throw new RequestNotFoundException();
        return request1.get();
    }

    //To display requests
    public List<PortingRequest> getRequests(String insuredName) throws RequestNotFoundException {
        if(requestRepository.existsByInsuredName(insuredName)) {

            List<PortingRequest> request1 = requestRepository.findByInsuredName(insuredName);
            return request1;
        }
        else
            throw new RequestNotFoundException();
    }

    @Override
    public Optional<PortingRequest> getPortingRequestByPortingRequestId(int portingRequestId) {
        return  requestRepository.findByPortingRequestId(portingRequestId);
    }

    //To get incoming porting requests
    public List<PortingRequest> getIncomingPortingRequest(String newInsurerName) {
        List<PortingRequest>portingRequest1 = requestRepository.findByNewInsurerName(newInsurerName).get();
        return portingRequest1;
    }

    //To get outgoing requests
    public List<PortingRequest> getOutgoingPortingRequest(String insurerName) {
        List<PortingRequest>portingRequest1 = requestRepository.findByInsurerName(insurerName).get();
        return portingRequest1;
    }

    //To accept outgoing requests
    public PortingRequest acceptOutgoingPortingRequest(PortingRequest portingRequest) {
        PortingRequest portingRequest1 = requestRepository.findByInsuredNameAndCreateDate(portingRequest.getInsuredName(),portingRequest.getCreateDate()).get();
        return portingRequest1;
    }

    //To accept incoming requests
    public PortingRequest acceptIncomingPortingRequest(PortingRequest portingRequest) {
        PortingRequest portingRequest1 = requestRepository.findByInsuredNameAndCreateDate(portingRequest.getInsuredName(),portingRequest.getCreateDate()).get();
        return portingRequest1;
    }

    //To delete a request
    public void deletePortingRequest(PortingRequest portingRequest) {
        requestRepository.deleteByInsuredNameAndCreateDate(portingRequest.getInsuredName(),portingRequest.getCreateDate());
    }

    //To reject incoming request
    public PortingRequest rejectIncomingPortingRequest(PortingRequest portingRequest) {
        PortingRequest portingRequest1 = requestRepository.findByInsuredNameAndCreateDate(portingRequest.getInsuredName(),portingRequest.getCreateDate()).get();
        return portingRequest1;
    }
}
