package com.coforge.training.agribid.bidder.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coforge.training.agribid.farmer.exception.ResourceNotFound;
import com.coforge.training.agribid.farmer.model.Crop;
import com.coforge.training.agribid.farmer.model.DemoCrop;


@FeignClient(name="farmer-service")
@Service
public interface CropClient {
	
	//public ResponseEntity<Crop> getCropById(@PathVariable Long cid) throws ResourceNotFound {

	@GetMapping("/agribid/farmer-service/crops/{cid}")
	public ResponseEntity<Crop> getCropById(@PathVariable Long cid);
		
	    
  //http://localhost:8081/agribid/farmer-service/crops
    @GetMapping("/agribid/farmer-service/crops")
    public List<DemoCrop> getSelectedStates(); 
  
    
	//@PatchMapping("/crops/{cid}/currentBid")

    @PatchMapping("/crops/{cid}/currentBid")
	public ResponseEntity<Void> updateCurrentBid(@PathVariable Long cid, @RequestParam double currentBid);    
    
}
      

	
	
	
	




