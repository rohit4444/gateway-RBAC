package com.el2.product.device.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.el2.product.device.entity.Device;
import com.el2.product.device.exception.DeviceNotFoundException;
import com.el2.product.device.service.DeviceService;

@RestController
@RequestMapping("/api/product-service/con")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;

	// Get all devices
	// @RolesAllowed("BP")
	@GetMapping("/allDevices")
	public ResponseEntity<List<Device>> getAllDevices() {
		List<Device> devices = deviceService.getAllDevices();
		return new ResponseEntity<>(devices, HttpStatus.OK);
	}

	@GetMapping("/getDevice")
	public ResponseEntity<Device> getDeviceById1(@RequestParam("id") Long id) {
		try {
			Optional<Device> device = deviceService.getDeviceById(id);
			return device.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
					.orElseThrow(() -> new DeviceNotFoundException("Device not found with id: " + id));
		} catch (DeviceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Add a new device
	@PostMapping("/addDevice")
	public ResponseEntity<Device> addDevice(@RequestBody Device device) {
		deviceService.addDevice(device);
		return new ResponseEntity<>(device, HttpStatus.CREATED);
	}

	// Update an existing device
	@PutMapping
	public ResponseEntity<Device> updateDevice(@RequestParam("id") Long id, @RequestBody Device updatedDevice) {
		try {
			deviceService.updateDevice(id, updatedDevice);
			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
		} catch (DeviceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Delete a device
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
		try {
			deviceService.deleteDevice(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (DeviceNotFoundException e) {
			// logger.error("Unable to delete. Device with id {} not found", id, e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Handle exceptions
	@ExceptionHandler(DeviceNotFoundException.class)
	public ResponseEntity<String> handleDeviceNotFoundException(DeviceNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/callfromOrderDevice")
	public String orderProduct() {
		return "This is product service , Called from Order-Service by using Client-Credential Flow!";
	}
}