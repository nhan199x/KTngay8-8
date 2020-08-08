package com.Application.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Application.DTO.DTOCarAccessoryForAddAndUpdate;
import com.Application.DTO.DTOCarAccessoryForView;
import com.Application.DTO.DTOCarForAddNew;
import com.Application.Entity.Accessory;
import com.Application.Entity.Car;
import com.Application.Entity.CarPK;
import com.Application.Service.ICarService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/v1/cars")
public class CarController {
	@Autowired
	private ICarService carService;
	
	@PostMapping
	public ResponseEntity<?> AddCar(@RequestBody DTOCarForAddNew dtoCarForAddNew) throws ParseException {
		Car car=dtoCarForAddNew.toEntity();
		carService.AddCar(car);
		return new ResponseEntity<String>("Save Car successfully!", HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{licensePlate}_{stringRepairDate}/accessories")
	public ResponseEntity<?> getCarAccessories(@PathVariable(name = "licensePlate") String licensePlate, //
			@PathVariable(name = "stringRepairDate") String stringRepairDate) throws ParseException { 
		List<Accessory> accessories=carService.getCarAccessory(new CarPK(licensePlate, new SimpleDateFormat("dd-MM-yyyy").parse(stringRepairDate)));
		List<DTOCarAccessoryForView> dtoCarAccessoryForView=new ArrayList<>();
		for (Accessory accessory : accessories) {
			dtoCarAccessoryForView.add(accessory.toDTO());
		}
		return new ResponseEntity<List<DTOCarAccessoryForView>>(dtoCarAccessoryForView, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{licensePlate}_{stringRepairDate}/accessories")
	public ResponseEntity<?> AddAccessoriesToCar(@PathVariable(name = "licensePlate") String licensePlate, //
			@PathVariable(name = "stringRepairDate") String stringRepairDate,//
			@RequestBody  DTOCarAccessoryForAddAndUpdate dtoCarAccessoryForAddAndUpdate) throws ParseException {
		CarPK carPK=new CarPK(licensePlate, new SimpleDateFormat("dd-MM-yyyy").parse(stringRepairDate));
		Car car= carService.getCarById(carPK);
		Accessory accessory=dtoCarAccessoryForAddAndUpdate.toEntity();
		accessory.setCar(car);
		carService.AddOrUpdateCarAccessories(accessory);
		return new ResponseEntity<String>("save car's accessory successfully!", HttpStatus.CREATED);
	}
	@PutMapping(value = "/{licensePlate}_{stringRepairDate}/accessories/{id}")
	public ResponseEntity<?> updateCarAccessories(@PathVariable(name = "licensePlate") String licensePlate, //
			@PathVariable(name = "stringRepairDate") String stringRepairDate,//
			@PathVariable(name = "id") int id,//
			@RequestBody  DTOCarAccessoryForAddAndUpdate dtoCarAccessoryForAddAndUpdate) throws ParseException {
		CarPK carPK=new CarPK(licensePlate, new SimpleDateFormat("dd-MM-yyyy").parse(stringRepairDate));
		Car car= carService.getCarById(carPK);
		Accessory accessory=dtoCarAccessoryForAddAndUpdate.toEntity();
		accessory.setCar(car);
		accessory.setId(id);
		carService.AddOrUpdateCarAccessories(accessory);
		return new ResponseEntity<String>("update car's accessory successfully!", HttpStatus.CREATED);
	}
	@DeleteMapping(value = "/{licensePlate}_{stringRepairDate}/accessories/{id}")
	public ResponseEntity<?> deleteCarAccessories(@PathVariable(name = "licensePlate") String licensePlate, //
			@PathVariable(name = "stringRepairDate") String stringRepairDate,//
			@PathVariable(name = "id") int id) throws ParseException {
		carService.deleteCarAccessories(id);
		return new ResponseEntity<String>("delete car's accessory successfully!", HttpStatus.CREATED);
	}
}
