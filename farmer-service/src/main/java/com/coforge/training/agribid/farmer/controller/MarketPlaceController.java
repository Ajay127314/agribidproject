package com.coforge.training.agribid.farmer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.agribid.bidder.service.CropClient;
import com.coforge.training.agribid.farmer.exception.ResourceNotFound;
import com.coforge.training.agribid.farmer.model.Crop;
import com.coforge.training.agribid.farmer.model.DemoCrop;

import com.coforge.training.agribid.farmer.service.CropService;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/farmer-service")
public class MarketPlaceController {

	@Autowired
	private CropService cService;


	private CropClient cropClient;

	//http://localhost:8081/agribid/farmer-service/crops
	@GetMapping("/crops")
	public List<DemoCrop> getSelectedStates() {
		return cService.getCropsInfo();
	}


	//http://localhost:8081/agribid/farmer-service/sell/{fId}
	@PostMapping("/sell/{fId}")
	public ResponseEntity<Crop> sellCrop(
			@PathVariable Long fId,
			@RequestPart("crop") String cropJson,
			@RequestPart("soilPHCertificate") MultipartFile soilPHCertificate) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		Crop crop = objectMapper.readValue(cropJson, Crop.class);

		crop.setSoilPHCertificate(soilPHCertificate.getBytes());

		Crop savedCrop = cService.saveCrop(fId, crop);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop);
	}

	@GetMapping("/crops/{cid}")
	public ResponseEntity<Crop> getCropById(@PathVariable Long cid) throws ResourceNotFound {
		Crop crop = cService.getCropById(cid);
		return ResponseEntity.ok(crop);
	}


	@PatchMapping("/crops/{cid}/currentBid")
	public ResponseEntity<Void> updateCurrentBid(@PathVariable Long cid, @RequestParam double currentBid) throws ResourceNotFound {
		cService.updateCurrentBid(cid, currentBid);
		return ResponseEntity.noContent().build();
	}




}
