package com.sivalabs.bookstore.orderservice.web.controllers;

import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createOrderRequestDtoWithCorrectData;
import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createOrderRequestDtoWithInvalidBooks;
import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createOrderRequestDtoWithInvalidDeliveryAddress;
import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createOrderRequestDtoWithMissingPhoneNumber;
import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createOrderRequestDtoWithMissingPhoneNumberAndEmail;
import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createOrderRequestDtoWithNoItems;
import static com.sivalabs.bookstore.orderservice.web.controllers.TestDataFactory.createValidOrderRequestDto;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.bookstore.orders.domain.OrderService;
import com.sivalabs.bookstore.orders.domain.SecurityService;
import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequestDto;
import com.sivalabs.bookstore.orders.web.controllers.OrderController;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerUnitTests {

    @MockitoBean // we are not interested in testing the order service here, so we can mock it
    private OrderService orderService;

    @MockitoBean // We are not interested in testing the security service here, so we can mock it
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // setup the mock security service to return a fixed username
        given(securityService.getLoginUser()).willReturn("testuser");
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    void shouldReturnBadRequestWhenCreateOrderRequestIsInvalid(CreateOrderRequestDto requestDto) throws Exception {
        given(orderService.createOrder(any(CreateOrderRequestDto.class), eq("testuser")))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProviderWithValidData")
    void shouldReturnOkRequestWhenCreateOrderRequestIsValid(CreateOrderRequestDto requestDto) throws Exception {
        given(orderService.createOrder(any(CreateOrderRequestDto.class), eq("testuser")))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProviderAsString")
    void shouldReturnBadRequestWhenCreateOrderRequestIsInvalidAsString(String requestDto) throws Exception {
        given(orderService.createOrder(any(CreateOrderRequestDto.class), eq("testuser")))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    //    @ParameterizedTest
    //    @MethodSource("createOrderRequestProviderValidDataAsString")
    //    void shouldReturnOkRequestWhenCreateOrderRequestIsValidAsString(String requestDto) throws Exception {
    //        given(orderService.createOrder(any(CreateOrderRequestDto.class), eq("testuser")))
    //                .willReturn(null);
    //
    //        mockMvc.perform(post("/api/orders")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content(objectMapper.writeValueAsString(requestDto)))
    //                .andExpect(status().is2xxSuccessful());
    //    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with invalid book", createOrderRequestDtoWithInvalidBooks())),
                arguments(
                        named("Order with invalid delivery address", createOrderRequestDtoWithInvalidDeliveryAddress()),
                        arguments(named("Order with no items", createOrderRequestDtoWithNoItems()))));
    }

    static Stream<Arguments> createOrderRequestProviderWithValidData() {
        return Stream.of(arguments(named("Order with valid data", createValidOrderRequestDto())));
    }

    static Stream<Arguments> createOrderRequestProviderAsString() {
        return Stream.of(
                arguments(named("Order with missing phone number", createOrderRequestDtoWithMissingPhoneNumber())),
                arguments(named(
                        "Order with missing phone number and email",
                        createOrderRequestDtoWithMissingPhoneNumberAndEmail())));
    }

    static Stream<Arguments> createOrderRequestProviderValidDataAsString() {
        return Stream.of(arguments(named("Order with correct data", createOrderRequestDtoWithCorrectData())));
    }
}
