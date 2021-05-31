package com.waracle.cakemgr;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CakeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeManagerApplication.class, args);
	}

	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void printDatabaseDetails() {

		log.info("======================================");
		log.info("! DATABASE CONNECTION DETAILS");

		Map<String, String> map = new HashMap<String, String>();
		for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
			if (propertySource instanceof EnumerablePropertySource) {
				for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
					Object valObj = propertySource.getProperty(key);
					if (valObj != null) {
						String val = valObj.toString();
						if (key.startsWith("spring.datasource.")) {
							map.put(key, val);
						} else if (key.startsWith("spring.jpa.")) {
							map.put(key, val);
						}
						if (key.contains("jdbc")) {
							map.put(key, val);
						}
					}
				}
			}
		}
		map.forEach((k, v) -> {
			log.info("! " + k + " : " + v);
		});

		log.info("======================================");
	}

	@Autowired
	private ConfigurableEnvironment	env;

}
