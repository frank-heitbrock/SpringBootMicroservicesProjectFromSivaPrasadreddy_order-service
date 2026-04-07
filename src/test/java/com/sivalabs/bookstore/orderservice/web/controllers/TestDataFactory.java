package com.sivalabs.bookstore.orderservice.web.controllers;

import static org.instancio.Select.field;

import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequestDto;
import com.sivalabs.bookstore.orders.domain.models.Customer;
import com.sivalabs.bookstore.orders.domain.models.DeliveryAddress;
import com.sivalabs.bookstore.orders.domain.models.OrderBook;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

public class TestDataFactory {

    static final List<String> VALID_COUNTRIES = List.of("India", "USA", "Germany");
    static final Set<OrderBook> VALID_BOOKS = Set.of(new OrderBook("P100", "Product 1", new BigDecimal("34.00"), 1));
    static final Set<OrderBook> INVALID_BOOKS = Set.of(new OrderBook("PXXX", "Product 1", new BigDecimal("34.00"), 1));

    public static CreateOrderRequestDto createValidOrderRequestDto() {
        return Instancio.of(CreateOrderRequestDto.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#a#a#a#a#a@mail.com"))
                .set(field(CreateOrderRequestDto::books), VALID_BOOKS)
                .generate(field(DeliveryAddress::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderRequestDto createOrderRequestDtoWithInvalidBooks() {
        return Instancio.of(CreateOrderRequestDto.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d@mail.com"))
                .set(field(Customer::phone), "")
                .generate(field(DeliveryAddress::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(CreateOrderRequestDto::books), VALID_BOOKS)
                .create();
    }

    public static CreateOrderRequestDto createOrderRequestDtoWithInvalidDeliveryAddress() {
        return Instancio.of(CreateOrderRequestDto.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d@mail.com"))
                .set(field(DeliveryAddress::country), "")
                .set(field(CreateOrderRequestDto::books), VALID_BOOKS)
                .create();
    }

    public static CreateOrderRequestDto createOrderRequestDtoWithNoItems() {
        return Instancio.of(CreateOrderRequestDto.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d@mail.com"))
                .generate(field(DeliveryAddress::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(CreateOrderRequestDto::books), Set.of())
                .create();
    }

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
