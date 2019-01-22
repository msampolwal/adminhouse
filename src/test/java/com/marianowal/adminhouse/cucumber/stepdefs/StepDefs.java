package com.marianowal.adminhouse.cucumber.stepdefs;

import com.marianowal.adminhouse.AdminhouseApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AdminhouseApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
