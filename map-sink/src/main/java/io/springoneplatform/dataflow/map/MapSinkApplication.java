package io.springoneplatform.dataflow.map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 *
 *
 */
@SpringBootApplication
public class MapSinkApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(MapSinkApplication.class).headless(false).run(args);
	}
}
