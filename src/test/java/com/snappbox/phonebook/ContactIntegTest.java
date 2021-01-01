package com.snappbox.phonebook;

import com.snappbox.phonebook.utility.constant.UrlMappings;
import com.snappbox.phonebook.dto.ContactDto;
import com.snappbox.phonebook.repository.ContactRepository;
import com.snappbox.phonebook.utility.TestDataUtil;
import com.snappbox.phonebook.utility.constant.ContactConst;
import com.snappbox.phonebook.utility.search.SearchCriteria;
import com.snappbox.phonebook.utility.search.SearchDto;
import com.snappbox.phonebook.utility.search.SearchOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ContactIntegTest extends BaseIntegTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestDataUtil testDataUtil;

    @Before
    public void clearDatabase() {
        contactRepository.deleteAll();
    }

    @Test
    public void success_add_contact() {
        ContactDto contactDto = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    public void fail_add_contact_when_name_is_null() {
        ContactDto contactDto = createContactDto(null, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
        ;

    }

    @Test
    public void success_add_contact_when_email_is_null() {
        ContactDto contactDto = createContactDto(ContactConst.ROGAHYEH_NAME, null
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
        ;

    }

    @Test
    public void success_add_contact_phone_is_null() {
        ContactDto contactDto = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , null, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
        ;

    }

    @Test
    public void success_add_contact_organization_is_null() {
        ContactDto contactDto = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, null, ContactConst.ROGAHYEH_GITHUB);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
        ;

    }

    @Test
    public void success_add_contact_when_github_is_null() {
        ContactDto contactDto = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, null);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", Matchers.equalTo("Bad Request"))
                .body("status", Matchers.equalTo(400))
        ;
    }

    @Test
    public void success_search_contact_when_criteria_is_not_exists() {
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        ContactDto mina = createContactDto(ContactConst.MINA_NAME, ContactConst.MINA_EMAIL
                , ContactConst.MINA_PHONE, ContactConst.MINA_ORGANIZATION, ContactConst.MINA_GITHUB);
        testDataUtil.createContact(ali);
        testDataUtil.createContact(mina);

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.containsInAnyOrder(ContactConst.ALI_GITHUB, ContactConst.MINA_GITHUB))
                .body("content.phoneNumber", Matchers.containsInAnyOrder(ContactConst.ALI_PHONE, ContactConst.MINA_PHONE))
                .body("content.repositories", Matchers.containsInAnyOrder(Matchers.empty(),Matchers.empty()))
                .body("content.organization", Matchers.containsInAnyOrder(ContactConst.ALI_ORGANIZATION, ContactConst.MINA_ORGANIZATION))
                .body("content.name", Matchers.containsInAnyOrder(ContactConst.ALI_NAME, ContactConst.MINA_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.containsInAnyOrder(ContactConst.ALI_EMAIL, ContactConst.MINA_EMAIL))
        ;

    }



    @Test
    public void success_search_contact_when_criteria_is_name() {
        ContactDto roghayeh = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        testDataUtil.createContact(roghayeh);
        testDataUtil.createContact(ali);

        ContactDto contactDto = createContactDto(ContactConst.ALI_NAME, null, null, null, null);
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.contains(ContactConst.ALI_GITHUB))
                .body("content.phoneNumber", Matchers.contains(ContactConst.ALI_PHONE))
                .body("content.repositories", Matchers.containsInAnyOrder(Matchers.empty()))
                .body("content.organization", Matchers.contains(ContactConst.ALI_ORGANIZATION))
                .body("content.name", Matchers.contains(ContactConst.ALI_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.contains(ContactConst.ALI_EMAIL))
        ;
    }

    @Test
    public void success_search_contact_when_criteria_is_phoneNumber() {
        ContactDto roghayeh = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        testDataUtil.createContact(roghayeh);
        testDataUtil.createContact(ali);

        ContactDto contactDto = createContactDto(null, null, ContactConst.ALI_PHONE, null, null);
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.containsInAnyOrder(ContactConst.ALI_GITHUB))
                .body("content.phoneNumber", Matchers.containsInAnyOrder(ContactConst.ALI_PHONE))
                .body("content.repositories", Matchers.containsInAnyOrder(Matchers.empty()))
                .body("content.organization", Matchers.containsInAnyOrder(ContactConst.ALI_ORGANIZATION))
                .body("content.name", Matchers.containsInAnyOrder(ContactConst.ALI_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.containsInAnyOrder(ContactConst.ALI_EMAIL))
        ;
    }

    @Test
    public void success_search_contact_when_criteria_is_email() {
        ContactDto roghayeh = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        testDataUtil.createContact(roghayeh);
        testDataUtil.createContact(ali);

        ContactDto contactDto = createContactDto(null, ContactConst.ALI_EMAIL, null, null, null);
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.containsInAnyOrder(ContactConst.ALI_GITHUB))
                .body("content.phoneNumber", Matchers.containsInAnyOrder(ContactConst.ALI_PHONE))
                .body("content.organization", Matchers.containsInAnyOrder(ContactConst.ALI_ORGANIZATION))
                .body("content.name", Matchers.containsInAnyOrder(ContactConst.ALI_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.containsInAnyOrder(ContactConst.ALI_EMAIL))
        ;
    }

    @Test
    public void success_search_contact_when_criteria_is_organization() {
        ContactDto roghayeh = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        testDataUtil.createContact(roghayeh);
        testDataUtil.createContact(ali);

        ContactDto contactDto = createContactDto(null, null, null, ContactConst.ROGAHYEH_ORGANIZATION, null);
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.containsInAnyOrder(ContactConst.ROGAHYEH_GITHUB))
                .body("content.phoneNumber", Matchers.containsInAnyOrder(ContactConst.ROGAHYEH_PHONE))
                .body("content.organization", Matchers.containsInAnyOrder(ContactConst.ROGAHYEH_ORGANIZATION))
                .body("content.name", Matchers.containsInAnyOrder(ContactConst.ROGAHYEH_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.containsInAnyOrder(ContactConst.ROGAHYEH_EMAIL))
        ;

    }

    @Test
    public void success_search_contact_when_criteria_is_github() {
        ContactDto roghayeh = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        testDataUtil.createContact(roghayeh);
        testDataUtil.createContact(ali);

        ContactDto contactDto = createContactDto(null, null, null, null, ContactConst.ALI_GITHUB);
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.containsInAnyOrder(ContactConst.ALI_GITHUB))
                .body("content.phoneNumber", Matchers.containsInAnyOrder(ContactConst.ALI_PHONE))
                .body("content.repositories", Matchers.containsInAnyOrder(Matchers.empty()))
                .body("content.organization", Matchers.containsInAnyOrder(ContactConst.ALI_ORGANIZATION))
                .body("content.name", Matchers.containsInAnyOrder(ContactConst.ALI_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.containsInAnyOrder(ContactConst.ALI_EMAIL))
        ;
    }

    @Test
    public void success_search_contact_when_criteria_is_organization_and_phoneNumber() {
        ContactDto roghayeh = createContactDto(ContactConst.ROGAHYEH_NAME, ContactConst.ROGAHYEH_EMAIL
                , ContactConst.ROGAHYEH_PHONE, ContactConst.ROGAHYEH_ORGANIZATION, ContactConst.ROGAHYEH_GITHUB);
        ContactDto ali = createContactDto(ContactConst.ALI_NAME, ContactConst.ALI_EMAIL
                , ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION, ContactConst.ALI_GITHUB);
        ContactDto mina = createContactDto(ContactConst.MINA_NAME, ContactConst.MINA_EMAIL
                , ContactConst.MINA_PHONE, ContactConst.MINA_ORGANIZATION, ContactConst.MINA_GITHUB);
        testDataUtil.createContact(roghayeh);
        testDataUtil.createContact(ali);
        testDataUtil.createContact(mina);

        ContactDto contactDto = createContactDto(null, null, ContactConst.ALI_PHONE, ContactConst.ALI_ORGANIZATION,null);
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(contactDto)
                .post(UrlMappings.CONTACTS + "/search")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.github", Matchers.containsInAnyOrder(ContactConst.ALI_GITHUB, ContactConst.MINA_GITHUB))
                .body("content.phoneNumber", Matchers.containsInAnyOrder(ContactConst.ALI_PHONE, ContactConst.MINA_PHONE))
                .body("content.repositories", Matchers.containsInAnyOrder(Matchers.empty(),Matchers.empty()))
                .body("content.organization", Matchers.containsInAnyOrder(ContactConst.ALI_ORGANIZATION, ContactConst.MINA_ORGANIZATION))
                .body("content.name", Matchers.containsInAnyOrder(ContactConst.ALI_NAME, ContactConst.MINA_NAME))
                .body("content.id", Matchers.notNullValue())
                .body("content.email", Matchers.containsInAnyOrder(ContactConst.ALI_EMAIL, ContactConst.MINA_EMAIL))
        ;

    }

    private ContactDto createContactDto(String name, String email, String phone
            , String organization, String github) {
        return ContactDto.builder()
                .name(name)
                .email(email)
                .phoneNumber(phone)
                .organization(organization)
                .github(github)
                .build();
    }
}
