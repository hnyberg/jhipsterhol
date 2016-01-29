package com.mycompany.demo.jhipsterhol.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
@Transactional
public class GeoCodingService {

    private final Logger log = LoggerFactory.getLogger(GeoCodingService.class);
    
    public GeoLocation getGeoCodeForAdress(final String address) {
		Map<String, String> vars = new HashMap<>();
		vars.put("address", address);
		vars.put("sensor", "false");
		GeoLocations result = new RestTemplate().getForObject(
				"http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor={sensor}",
				GeoLocations.class, vars);

		return result.getResults().get(0);

	}

	public static class GeoLocations {
		List<GeoLocation> results = new ArrayList<GeoLocation>();

		public GeoLocations() {
		}

		public List<GeoLocation> getResults() {
			return results;
		}

		public void setResults(List<GeoLocation> results) {
			this.results = results;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GeoLocation {
		Geometry geometry;
		String formatted_address;

		public Geometry getGeometry() {
			return geometry;
		}

		public void setGeometry(Geometry geometry) {
			this.geometry = geometry;
		}

		public String getFormatted_address() {
			return formatted_address;
		}

		public void setFormatted_address(String formattedAddress) {
			this.formatted_address = formattedAddress;
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Geometry {
		Location location;

		public Geometry() {
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Location {
		Double lat;
		Double lng;

		public Location() {
		}

		public Double getLat() {
			return lat;
		}

		public Double getLng() {
			return lng;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}

	}
}
