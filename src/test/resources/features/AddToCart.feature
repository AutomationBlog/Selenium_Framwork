Feature: Amazon Add to Cart Functionality

  @smoke
  Scenario: User adds a product to cart from Amazon website
    Given User is on Amazon home page
    When User searches for "laptop"
    Then Search results should be displayed
    When User clicks on first product
    And User adds product to cart
    Then Product should be added to cart

  @regression
  Scenario: User adds multiple products to cart
    Given User is on Amazon home page
    When User searches for "mobile phone"
    Then Search results should be displayed
    When User clicks on first product
    And User adds product to cart
    Then Product should be added to cart
