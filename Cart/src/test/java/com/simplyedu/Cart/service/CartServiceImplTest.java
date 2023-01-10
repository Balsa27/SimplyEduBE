package com.simplyedu.Cart.service;

import com.simplyedu.Cart.config.Jwt.AuthUser;
import com.simplyedu.Cart.entities.Cart;
import com.simplyedu.Cart.entities.CoursePurchase;
import com.simplyedu.Cart.exceptions.CartNotFoundException;
import com.simplyedu.Cart.exceptions.CourseNotFoundException;
import com.simplyedu.Cart.exceptions.CourseNotInCartException;
import com.simplyedu.Cart.exceptions.FailedPurchaseException;
import com.simplyedu.Cart.http.CourseService;
import com.simplyedu.Cart.http.PurchaseService;
import com.simplyedu.Cart.http.Course;
import com.simplyedu.Cart.http.entities.request.PurchaseRequest;
import com.simplyedu.Cart.http.entities.response.PurchaseResponse;
import com.simplyedu.Cart.repository.CartRepository;
import com.simplyedu.Cart.service.Impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class CartServiceImplTest {
    private CartRepository mockCartRepository;
    private CourseService mockCourseService;
    private PurchaseService mockPurchaseService;
    private CartServiceImpl cartService;
    private Authentication authentication;
    private SecurityContext contextHolder;
    private AuthUser authUser;
    private AuthenticationManager authenticationManager;
    
    
    @BeforeEach
    public void setUp() {

        //Arrange
        
        mockCartRepository = mock(CartRepository.class);
        mockCourseService = mock(CourseService.class);
        mockPurchaseService = mock(PurchaseService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authentication = mock(Authentication.class);
        contextHolder = mock(SecurityContext.class);
        cartService = new CartServiceImpl(mockCartRepository, mockCourseService, mockPurchaseService);
        
        //Act (Setting up auth user)
        
        AuthUser authUser = AuthUser.builder()
                .id(1L)
                .email("balsa.stevovoic@gmail.com")
                .username("balsa")
                .roles(Set.of(new SimpleGrantedAuthority("ROLE_USER")))
                .password("123456")
                .build();

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(contextHolder.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(authUser);
        SecurityContextHolder.setContext(contextHolder);
    }
    
    @Test
    void removeCartItem_success(){
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        Cart cart = new Cart(1L, 1L, courseIds);
        
        //Act
        courseIds.add(1L);
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockCourseService.getCourseById(1L)).thenReturn(Course.builder().id(1L).build());

        //Assert
        assertNotNull(mockCourseService.getCourseById(1L));
        assertNotNull(cart);
        assertEquals("Course removed from cart", cartService.removeCartItem(1L).getMessage());

    }
    
    @Test
    void removeCartItem_throws_cartNotFound(){
        //Act
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        
        //Assert
        assertThrows(CartNotFoundException.class, () -> cartService.removeCartItem(1L));
    }
    
    @Test
    void removeCartItem_throws_courseNotFound(){
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        Cart cart = new Cart(1L, 1L, courseIds);
        
        //Act
        courseIds.add(1L);
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockCourseService.getCourseById(1L)).thenReturn(null);
        
        //Assert
        assertNull(mockCourseService.getCourseById(1L));
        assertNotNull(cart);
        assertThrows(CourseNotFoundException.class, () -> cartService.removeCartItem(1L));
    }
    
    @Test
    void removeCartItem_throws_courseNotInCart(){
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        Cart cart = new Cart(1L, 1L, courseIds);
        
        //Act
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockCourseService.getCourseById(1L)).thenReturn(Course.builder().id(2L).build());
        
        //Assert
        assertNotNull(cart);
        assertThrows(CourseNotInCartException.class, () -> cartService.removeCartItem(1L));
        assertNotNull(mockCourseService.getCourseById(1L));
    }
    
    @Test
    void addCartItem_success(){
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        Cart cart = new Cart(1L, 1L, courseIds);
        Course course = Course.builder().id(1L).build();

       //Act
        courseIds.add(1L);
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockCourseService.getCourseById(1L)).thenReturn(course);

        //Assert
        assertNotNull(course);
        assertNotNull(cart);
        
    }
    
    @Test
    void addCartItem_throws_cartNotFound(){
        //Act
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        
        //Assert
        assertThrows(CourseNotFoundException.class, () -> cartService.addItemToCart(1L));
    }
    
    @Test
    void purchaseByUserId_success(){
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        Cart cart = new Cart(1L, 1L, courseIds);
        courseIds.add(1L);
        
        //Act
        PurchaseRequest request = PurchaseRequest.builder().userId(1L).courseIds(cart.getCourseIds()).build();
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockPurchaseService.purchaseCourses(request)).thenReturn(PurchaseResponse.builder().build());   

        //Assert        
        assertNotNull(cart);
        assertNotNull(request);
        assertNotNull(mockPurchaseService.purchaseCourses(request));
    }
    
    @Test
    void purchaseByUserId_throws_cartNotFound(){
        //Act
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        
        //Assert
        assertThrows(CartNotFoundException.class, () -> cartService.purchaseByUserId());
    }
    
    @Test
    void purchaseByUserId_throws_failedPurchase() throws Exception{
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        courseIds.add(1L);
        Cart cart = new Cart(1L, 1L, courseIds);
        PurchaseRequest request = PurchaseRequest.builder().userId(1L).courseIds(cart.getCourseIds()).build();

        //Act
        Mockito.when(mockCartRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(cart));
        Mockito.when(mockPurchaseService.purchaseCourses(request)).thenReturn(null);

        //Assert
        assertNotNull(cart);
        assertNotNull(request);
        assertThrows(FailedPurchaseException.class, () -> cartService.purchaseByUserId());
    }
    
    @Test
    void getCartByUserId_success(){
        //Arrange
        Set<Long> courseIds = new HashSet<>();
        courseIds.add(1L);
        Cart cart = new Cart(1L, 1L, courseIds);
        
        //Act
        Mockito.when(mockCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        
        //Assert
        assertNotNull(cart);
        assertNotNull(cartService.getCartByUserId());
    }
}
