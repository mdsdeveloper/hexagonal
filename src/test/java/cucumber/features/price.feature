Feature: Get prices for brand and date
  As a User
  I want to ask for some product price
  So I will get the product price based on parameters

  Scenario Outline: Price retrieved
    Given a customer with the applicationDate "<applicationDate>", the product id "<productId>" and brand id "<brandId>"
    When the customer does the request GET a "/api/getPrice" with the specific params
    Then the server should be response with 200
    And return the next message "<message>"

    Examples:
      | applicationDate     | productId | brandId | message |
      | 2020-06-14T10:00:00 | 35455     | 1       | 35.5    |
      | 2020-06-14T16:00:00 | 35455     | 1       | 25.45   |
      | 2020-06-14T21:00:00 | 35455     | 1       | 35.5    |
      | 2020-06-15T10:00:00 | 35455     | 1       | 30.50   |
      | 2020-06-16T21:00:00 | 35455     | 1       | 38.95   |
