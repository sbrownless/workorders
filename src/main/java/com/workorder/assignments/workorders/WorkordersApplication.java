package com.workorder.assignments.workorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class WorkordersApplication {

  public static void main(String[] args) {
    SpringApplication.run(WorkordersApplication.class, args);
  }
}
