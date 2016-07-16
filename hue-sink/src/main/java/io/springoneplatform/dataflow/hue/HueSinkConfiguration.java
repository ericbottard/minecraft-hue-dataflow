package io.springoneplatform.dataflow.hue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
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

	@StreamListener(Sink.INPUT)
	public void changeHue(State state) {
		System.out.println("Received: " + state);

		restTemplate.put("http://{host}:{port}/api/{user}/lights/{id}/state", state,
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
