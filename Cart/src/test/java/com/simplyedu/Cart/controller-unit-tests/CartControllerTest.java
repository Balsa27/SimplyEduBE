package com.simplyedu.Cart.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.simplyedu.Cart.entities.response.AddCartItemResponse;
import com.simplyedu.Cart.entities.response.GetCartByUserIdResponse;
import com.simplyedu.Cart.entities.response.RemoveCartItemResponse;
import com.simplyedu.Cart.http.entities.response.PurchaseResponse;
import com.simplyedu.Cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    /**
     * Method under test: {@link CartController#removeCartItem(Long)}
     */
    @Test
    void testRemoveCartItem() throws Exception {
        //Act
        when(cartService.removeCartItem(any()))
                .thenReturn(new RemoveCartItemResponse("hehe"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/remove/{courseId}", 123L);
        
        //Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"hehe\"}"));
    }

    /**
     * Method under test: {@link CartController#addItemToCart(Long)}
     */
    @Test
    void testAddItemToCart() throws Exception {
        //Act
        when(cartService.addItemToCart(any())).thenReturn(new AddCartItemResponse("hehe"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add/{courseId}", 123L);
        
        //Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"hehe\"}"));
    }

    /**
     * Method under test: {@link CartController#getCartByUserId()}
     */
    @Test
    void testGetCartByUserId() throws Exception {
        //Act
        when(cartService.getCartByUserId()).thenReturn(new GetCartByUserIdResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        
        //Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"userId\":null,\"courses\":null}"));
    }

    /**
     * Method under test: {@link CartController#checkout()}
     */
    @Test
    void testCheckout() throws Exception {
        //Act
        when(cartService.purchaseByUserId()).thenReturn(new PurchaseResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/checkout");
        
        //Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"coursePurchaseResponse\":null}"));
    }

}

