package com.sivalabs.bookstore.orderservice.web.controllers;

public class TestDataFactory {

    public static String createOrderRequestDtoWithMissingPhoneNumber() {
        var payload =
                """
					{
				    "customer": {
				        "name": "Siva",
				        "email": "siva@gmail.com",
				        "phone": ""
				    },
				    "deliveryAddress": {
				        "addressLine1": "Kukatpally",
				        "addressLine2": "KPHB",
				        "city": "Hyderabad",
				        "state": "Telangana",
				        "zipCode": "500072",
				        "country": "HHGH"
				    },
				    "books": [
				        {
				            "name": "Product 1",
				            "price": 34.00,
				            "quantity": 1,
				            "code": "P100"
				        }
				    ]
				}
				""";
        return payload;
    }

    public static String createOrderRequestDtoWithCorrectData() {
        var payload =
                """
					{
				    "customer": {
				        "name": "Siva",
				        "email": "siva@gmail.com",
				        "phone": "999999999"
				    },
				    "deliveryAddress": {
				        "addressLine1": "Kukatpally",
				        "addressLine2": "KPHB",
				        "city": "Hyderabad",
				        "state": "Telangana",
				        "zipCode": "500072",
				        "country": "HHGH"
				    },
				    "books": [
				        {
				            "name": "Product 1",
				            "price": 34.00,
				            "quantity": 1,
				            "code": "P100"
				        }
				    ]
				}
				""";
        return payload;
    }

    public static String createOrderRequestDtoWithMissingPhoneNumberAndEmail() {
        var payload =
                """
					{
				    "customer": {
				        "name": "Siva",
				        "email": "",
				        "phone": ""
				    },
				    "deliveryAddress": {
				        "addressLine1": "Kukatpally",
				        "addressLine2": "KPHB",
				        "city": "Hyderabad",
				        "state": "Telangana",
				        "zipCode": "500072",
				        "country": "HHGH"
				    },
				    "books": [
				        {
				            "name": "Product 1",
				            "price": 34.00,
				            "quantity": 1,
				            "code": "P100"
				        }
				    ]
				}
				""";
        return payload;
    }
}
