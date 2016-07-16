package io.springoneplatform.dataflow.hue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents (a subset of) the acceptable paylaod for changing state of a bulb.
 *
 * @see <a href="http://www.developers.meethue.com/documentation/lights-api">Philips Hue doc</a>
 *
 * @author Eric Bottard
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class State {

	private Boolean on;

	@JsonProperty("bri")
	private Integer brightness;

	@JsonProperty
	private Integer hue;

	@JsonProperty("sat")
	private Integer saturation;

	@JsonProperty("ct")
	private Integer temperature;

	public Boolean getOn() {
		return on;
	}

	public void setOn(Boolean on) {
		this.on = on;
	}

	public Integer getBrightness() {
		return brightness;
	}

	public void setBrightness(Integer brightness) {
		this.brightness = brightness;
	}

	public Integer getHue() {
		return hue;
	}

	public void setHue(Integer hue) {
		this.hue = hue;
	}

	public Integer getSaturation() {
		return saturation;
	}

	public void setSaturation(Integer saturation) {
		this.saturation = saturation;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "State{" +
				"on=" + on +
				", brightness=" + brightness +
				", hue=" + hue +
				", saturation=" + saturation +
				", temperature=" + temperature +
				'}';
	}
}
