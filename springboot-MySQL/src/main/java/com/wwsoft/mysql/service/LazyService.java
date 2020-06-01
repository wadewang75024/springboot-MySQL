package com.wwsoft.mysql.service;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * When the @Lazy annotation is present together with the @Component annotation on class level, 
 * then the component will not be eagerly created by the container, but when the component 
 * is first requested. Together with the @Autowired injection point, you need to mark it with 
 * @Lazy annotation.
 *
 */
@Lazy
@Service
public class LazyService {
	private static Logger logger = Logger.getLogger("LazyService");
	public LazyService() {
		logger.info("**************** LazyService constructed ");
	}
	
	@PostConstruct
	public void init() {
		logger.info("**************** LazyService initialized ");
	}
	
}
