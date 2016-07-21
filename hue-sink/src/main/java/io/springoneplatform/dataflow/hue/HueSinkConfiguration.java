package io.springoneplatform.dataflow.hue;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.client.RestTemplate;

/**
 * A sink app that is able to interact with a Philips HUE bulb.
 *
 * @author Eric Bottard
 */
@EnableBinding(Sink.class)
@EnableConfigurationProperties(HueSinkProperties.class)
public class HueSinkConfiguration {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HueSinkProperties properties;

	private ObjectMapper objectMapper = new ObjectMapper();

	@ServiceActivator(inputChannel = Sink.INPUT)
	public void changeHue(String payload) throws IOException {
		State state = objectMapper.readValue(payload, State.class);

		State toSend = state.convert();
		System.out.println("PUTing " + state);
		restTemplate.put("http://{host}:{port}/api/{user}/lights/{id}/state", toSend,
				properties.getHost(),
				properties.getPort(),
				properties.getUsername(),
				properties.getLight());
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
