package com.el2.product.device.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.el2.product.device.entity.Device;
import com.el2.product.device.repository.DeviceRepository;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	public List<Device> getAllDevices() {
		return deviceRepository.findAll();
	}

	public Optional<Device> getDeviceById(Long id) {
		return deviceRepository.findById(id);
	}

	public void addDevice(Device device) {
		deviceRepository.save(device);
	}

	public void updateDevice(Long id, Device updatedDevice) {
		if (deviceRepository.existsById(id)) {
			updatedDevice.setId(id);
			deviceRepository.save(updatedDevice);
		}
	}

	public void deleteDevice(Long id) {
		deviceRepository.deleteById(id);
	}
}