package com.sivalabs.bookstore.orderservice.web.controllers;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.sivalabs.bookstore.orderservice.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTests extends AbstractIntegrationTest {

    @Nested
    class CreateOrderTests {

        @Test
        void shouldCreateOrderSuccessfully() {
            var payload = TestDataFactory.createOrderRequestDtoWithCorrectData();
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryCustomerPhoneIsMissing() {
            var payload = TestDataFactory.createOrderRequestDtoWithMissingPhoneNumber();
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .body("errors", hasItem("Customer phone is required"))
                    .body("errors.size()", is(1))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryCustomerPhoneAndEmailIsMissing() {
            var payload = TestDataFactory.createOrderRequestDtoWithMissingPhoneNumberAndEmail();
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .body("errors", hasItem("Customer phone is required"))
                    .body("errors", hasItem("Customer email is required"))
                    .body("errors.size()", is(2))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
