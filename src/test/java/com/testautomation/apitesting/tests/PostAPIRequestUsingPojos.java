package com.testautomation.apitesting.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PostAPIRequestUsingPojos {

    @Test
    public void PostAPIRequest(){

        //Serialization java object to json object
        try {
            BookingDates bookingDates = new BookingDates("2024-04-20", "2024-04-25");
            Booking booking = new Booking("api test", "Practise","breakfast",2000,true, bookingDates);


            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);

            System.out.println(requestBody);

            //De-Serialization json object to java object

          Booking bookingDetails =  objectMapper.readValue(requestBody, Booking.class);
          System.out.println(bookingDetails.getFirstname());
          System.out.println(bookingDetails.getLastname());
          System.out.println(bookingDetails.getTotalprice());


            System.out.println(bookingDetails.getBookingdates().getCheckin());
            System.out.println(bookingDetails.getBookingdates().getCheckout());


            Response response =
            RestAssured
                    .given().contentType(ContentType.JSON).body(requestBody).baseUri("https://restful-booker.herokuapp.com/booking")
                    .when().post()
                    .then().assertThat().statusCode(200)
                    .extract().response();


            //Get extract ID and check response body
            int bookingId = response.path("bookingid");

            RestAssured
                    .given().contentType(ContentType.JSON).baseUri("https://restful-booker.herokuapp.com/booking")
                    .when().get("/{bookinId}", bookingId)
                    .then().assertThat().statusCode(200);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
