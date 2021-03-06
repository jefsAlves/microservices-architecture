package com.alvesjefs.hrworker.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alvesjefs.hrworker.domain.Worker;
import com.alvesjefs.hrworker.services.WorkerService;

@RefreshScope
@RestController
@RequestMapping(value = "/api/workers")
public class WorkerResource {

	private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

	@Autowired
	private Environment env;

	@Autowired
	private WorkerService workerService;

	@Value("${test.config}")
	private String configs;

	@GetMapping
	public ResponseEntity<List<Worker>> findAll(Worker workers) {
		List<Worker> worker = workerService.findAll(workers);
		return ResponseEntity.ok().body(worker);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Worker> findById(@PathVariable Long id) {

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info("PORT = " + env.getProperty("local.server.port"));
		Worker worker = workerService.findById(id);

		return ResponseEntity.ok().body(worker);
	}

	@GetMapping(value = "/configs")
	public ResponseEntity<Void> getConfigs() {
		logger.info("CONFIG = " + configs);
		return ResponseEntity.noContent().build();
	}
}
