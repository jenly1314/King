package org.king.entity;

import java.io.Serializable;

public class LocationInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Location location;
	
	private String formatted_address;
	
	private String business;
	
	private AddressComponent addressComponent;
	
	private String cityCode;
	
	/**
	 * ππ‘Ï
	 */
	public LocationInfo(){
		
		location = new Location();
		addressComponent = new AddressComponent();
		
	}
	


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}





	public static class Location implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private String lat;
		private String lng;
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getLng() {
			return lng;
		}
		public void setLng(String lng) {
			this.lng = lng;
		}
		
	}
	
	public static class AddressComponent implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private String streetNumber;
		private String street;
		private String district;
		private String city;
		private String province;
		
		
		public String getStreetNumber() {
			return streetNumber;
		}
		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		
	}
	
	

}
