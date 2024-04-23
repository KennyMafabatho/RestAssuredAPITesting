package com.testautomationtesting.apitesting.tests;

import com.testautomation.apitesting.utils.FileNameConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PostAPIRequestUsingFile {

       @Test
       public void postAPIRequest() {

              try {
                   String postAPIBody =   FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");

                  RestAssured
                          .given()
                          .contentType(ContentType.JSON)
                          .body(postAPIBody)
                          .baseUri("https://restful-booker.herokuapp.com/booking")
                          .when()
                          .post()
                          .then().assertThat().statusCode(200);
              } catch (IOException e) {
                     e.printStackTrace();
              }

       }
}
