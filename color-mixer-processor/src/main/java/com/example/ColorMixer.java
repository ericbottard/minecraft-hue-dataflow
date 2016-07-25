package com.example;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Created by ericbottard on 13/07/16.
 */
@EnableBinding(Processor.class)
public class ColorMixer {

	private ObjectMapper objectMapper = new ObjectMapper();

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Map<String, Object> mix(String event) throws IOException {
		System.out.println("Received " + event);
		Map<String, Object> json = objectMapper.readValue(event, Map.class);

		Map<String, Object> result = new LinkedHashMap<>();
		float[] hsb = new float[3];
		Color.RGBtoHSB((int) json.get("red"), (int) json.get("green"), (int) json.get("blue"), hsb);
		result.put("hue", (int)(65535*hsb[0]));
		result.put("sat", (int)(254*hsb[1]));
		result.put("bri", (int)(354*hsb[2]));
		return result;
	}
}
