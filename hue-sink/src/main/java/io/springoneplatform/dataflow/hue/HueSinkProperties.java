package io.springoneplatform.dataflow.hue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Used to configure the HUE sink application.
 *
 * @author Eric Bottard
 */
@ConfigurationProperties("hue")
public class HueSinkProperties {

	/**
	 * The id of the light to alter.
	 */
	@Value("${INSTANCE_INDEX:1}")
	private String light;

	/**
	 * The host address of the HUE bridge.
	 */
	private String host = "localhost";

	/**
	 * The port of the HUE bridge.
	 */
	private int port = 80;

	/**
	 * The username (or key) to authenticate with the HUE bridge.
	 */
	private String username = "DcVGNJn0BRlICjNV8UKvPxbdIXp9IVDJyG6Fn9yP";

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}
}
