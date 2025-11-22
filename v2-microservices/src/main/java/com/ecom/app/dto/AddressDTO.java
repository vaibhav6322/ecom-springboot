package com.ecom.app.dto;

import lombok.Data;

@Data
public class AddressDTO {
//    private long id;

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
