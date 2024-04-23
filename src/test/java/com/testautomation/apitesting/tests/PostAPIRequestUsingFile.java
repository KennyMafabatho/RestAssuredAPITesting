package com.testautomation.apitesting.tests;

import com.jayway.jsonpath.JsonPath;
import com.testautomation.apitesting.utils.FileNameConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PostAPIRequestUsingFile {

       @Test
       public void postAPIRequest() {

              try {
                   String postAPIBody =   FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
                Response response =
                  RestAssured
                          .given()
                          .contentType(ContentType.JSON)
                          .body(postAPIBody)
                          .baseUri("https://restful-booker.herokuapp.com/booking")
                          .when().post()
                          .then().assertThat().statusCode(200)
                          .extract().response();

                //Fetching values from API response and validate expected values with actual values

                 JSONArray jsonArray = JsonPath.read(response.body().asString(), "$.booking..firstname");
                 String firstname = (String) jsonArray.get(0);

                  Assert.assertEquals(firstname,"api testing");

                  JSONArray jsonArrayLastname = JsonPath.read(response.body().asString(), "$.booking..lastname");
                  String lastname = (String) jsonArrayLastname.get(0);

                  Assert.assertEquals(lastname,"tutorial");

                  JSONArray jsonArrayCheckin = JsonPath.read(response.body().asString(), "$.booking.bookingdates..checkin");
                  String checkin = (String) jsonArrayCheckin.get(0);

                  Assert.assertEquals(checkin, "2023-03-26");

                  int bookingId = JsonPath.read(response.body().asString(), "$.bookingid");

                  RestAssured
                          .given().contentType(ContentType.JSON)
                          .baseUri("https://restful-booker.herokuapp.com/booking")
                          .when().get("/{bookingId}",bookingId)
                          .then().assertThat().statusCode(200);


              } catch (IOException e) {
                     e.printStackTrace();
              }

       }
}
