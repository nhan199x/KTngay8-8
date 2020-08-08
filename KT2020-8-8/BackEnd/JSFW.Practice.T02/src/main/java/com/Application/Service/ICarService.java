package com.Application.Service;

import java.util.List;

import com.Application.Entity.Accessory;
import com.Application.Entity.Car;
import com.Application.Entity.CarPK;

public interface ICarService {
	public void AddCar(Car car);
	public Car getCarById(CarPK carPK);
	public List<Accessory> getCarAccessory(CarPK carPK);
	public void AddOrUpdateCarAccessories(Accessory accessory);
	public void deleteCarAccessories(int id);
}
