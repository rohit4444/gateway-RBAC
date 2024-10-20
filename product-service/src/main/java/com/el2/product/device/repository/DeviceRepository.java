package com.el2.product.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.el2.product.device.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}