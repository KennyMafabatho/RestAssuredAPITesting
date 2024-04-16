package com.testautomationtesting.apitesting.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PostAPIRequest {

    @Test
    public void createBooking(){
        //prepare post request body

        JSONObject booking = new JSONObject();
        JSONObject bookingDates = new JSONObject();

        booking.put("firstname", "api testing");
        booking.put("lastname", "tutorial");
        booking.put("totalprice", 1000);
        booking.put("depositpaid", true);
        booking.put("additionalneeds", "breakfast");
        booking.put("bookingdates", bookingDates);

        bookingDates.put("checkin","2023-03-26");
        bookingDates.put("checkout","2023-03-30");


        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(booking.toString())
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                //.log().all()
                .when()
                    .post()
                .then()
                    .assertThat()
                .log().headers()
                    .statusCode(200)
                .body("booking.firstname", Matchers.equalTo("api testing"))
                .body("booking.totalprice", Matchers.equalTo(1000))
                .body("booking.bookingdates.checkin", Matchers.equalTo("2023-03-26"));


    }
}
