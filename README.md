# shoppingCart
NotOnTheHighStreet shopping cart 

# Package structure 
model package : 
    1. Product : Pojo class to represent the product details.
    2. PromotionRule : Common Pojo class which holds the promotion details.
    3. FinalOfferPromotion : Pojo class which holds details specific to final cost.
    4. ProductBasedPromotion : Pojo class which contain details of product related offers.

service package :
    1. Checkout : Interface that contains methods to scan and calculate total.
    2. CheckoutImpl : Implementation class for Checkout interface.


# Assumptions made:
1. Only one FinalOfferPromotion is valid for a specific period of time.
2. Only one ProductBasedPromotion is valid for a specific period of time for specific product.
3. All valid product based promotions are applied (no restriction on promotion if another promotion is present). 

