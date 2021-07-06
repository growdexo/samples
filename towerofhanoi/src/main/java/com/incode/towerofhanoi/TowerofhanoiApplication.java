package com.incode.towerofhanoi;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
@RestController
public class TowerofhanoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TowerofhanoiApplication.class, args);
	}
	
//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return String.format("Hello %s! test2sd", name);
//	}

	@CrossOrigin
	@GetMapping("/towerofhanoi")
	public ResponseEntity<HashMap<String, ArrayList<TowerofhanoiState>>> 
	towerofhanoi(@RequestParam(value = "n") int n) {
		// Only allow up to n=10.
	    HashMap<String, ArrayList<TowerofhanoiState>> map = new HashMap<>();

		if (n > 10) {
			map.put("data", null);
			return ResponseEntity.badRequest().body(map);
		}

		TowerofhanoiSolver solver = new TowerofhanoiSolver(n);
		
		map.put("data", solver.solve());
		
		return ResponseEntity.ok(map);
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/");	
    }
}
