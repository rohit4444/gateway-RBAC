package com.el2.activitymanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/activity-service")
public class ActivityController1 {

    @GetMapping("/activity1")
  //  @ResponseStatus(HttpStatus.OK)
    public String getOrder1() {
        return "Hello, activity 1 successfully executed !";
    }
} 