package io.springoneplatform.dataflow.map;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

/**
 * A sink app that displays living entities onSheep events as paths on a map.
 *
 * @author Eric Bottard
 */
@EnableBinding(Sink.class)
@EnableConfigurationProperties(MapSinkProperties.class)
public class MapSinkConfiguration {

	@Autowired
	private MapSinkProperties properties;

	@StreamListener(Sink.INPUT)
	public void update(String p) throws IOException {
		LivingUpdatePayload livingUpdatePayload = new ObjectMapper().readValue(p, LivingUpdatePayload.class);
		if (livingUpdatePayload.getRed() != null) {
			livingUpdateListener().onSheep(livingUpdatePayload);
		}
		else {
			livingUpdateListener().onPlayer(livingUpdatePayload);
		}
	}

	//	@StreamListener(Sink.INPUT)
//	public void onSheep(LivingUpdatePayload livingUpdatePayload) {
//		livingUpdateListener().onSheep(livingUpdatePayload);
//	}
//
	@Bean
	public LivingUpdateListener livingUpdateListener() {
		return new LivingUpdateListener();
	}

}
