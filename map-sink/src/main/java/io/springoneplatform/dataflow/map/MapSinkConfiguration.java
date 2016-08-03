package io.springoneplatform.dataflow.map;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A sink app that displays living entities onSheep events as paths on a map.
 *
 * @author Eric Bottard
 */
@Configuration
public class MapSinkConfiguration {

	private ObjectMapper objectMapper = new ObjectMapper();

	public void update(byte[] p) throws IOException {
		LivingUpdatePayload livingUpdatePayload = objectMapper.readValue(p, LivingUpdatePayload.class);
		if (livingUpdatePayload.getEntity() == null) {
			// not an actual livingUpdatePayload
			return;
		}
		if (livingUpdatePayload.getRed() != null) {
			livingUpdateListener().onSheep(livingUpdatePayload);
		}
		else {
			livingUpdateListener().onPlayer(livingUpdatePayload);
		}
	}

	@Bean
	public LivingUpdateListener livingUpdateListener() {
		return new LivingUpdateListener();
	}

}
