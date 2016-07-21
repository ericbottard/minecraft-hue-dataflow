package io.springoneplatform.dataflow.hue;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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

	private Integer red;

	private Integer green;

	private Integer blue;

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

	public void setRed(Integer red) {
		this.red = red;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public void setBlue(Integer blue) {
		this.blue = blue;
	}

	/**
	 * If RGB values have been set, convert those to HSB so they take effect.
	 */
	public State convert() {
		if (red != null && green != null && blue != null) {
			float[] hsb = new float[3];
			Color.RGBtoHSB(red, green, blue, hsb);
			hue = (int) (65535 * hsb[0]);
			saturation = (int) (254 * hsb[1]);
			brightness = (int) (254 * hsb[2]);
		}
		return this;
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
