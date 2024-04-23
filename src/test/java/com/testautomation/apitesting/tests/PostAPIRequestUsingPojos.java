package com.testautomation.apitesting.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import org.testng.annotations.Test;

public class PostAPIRequestUsingPojos {

    @Test
    public void PostAPIRequest(){

        try {
            BookingDates bookingDates = new BookingDates("2024-04-20", "2024-04-25");
            Booking booking = new Booking("api test", "Practise","breakfast",2000,true, bookingDates);


            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);

            System.out.println(requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
