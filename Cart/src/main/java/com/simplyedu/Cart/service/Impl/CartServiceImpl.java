package com.simplyedu.Cart.service.Impl;

import com.simplyedu.Cart.config.Jwt.AuthUser;
import com.simplyedu.Cart.entities.response.*;
import com.simplyedu.Cart.exceptions.CartNotFoundException;
import com.simplyedu.Cart.exceptions.CourseNotFoundException;
import com.simplyedu.Cart.exceptions.CourseNotInCartException;
import com.simplyedu.Cart.exceptions.FailedPurchaseException;
import com.simplyedu.Cart.http.PurchaseService;
import com.simplyedu.Cart.http.entities.request.PurchaseRequest;
import com.simplyedu.Cart.entities.Cart;
import com.simplyedu.Cart.http.Course;
import com.simplyedu.Cart.http.CourseService;
import com.simplyedu.Cart.http.entities.response.PurchaseResponse;
import com.simplyedu.Cart.repository.CartRepository;
import com.simplyedu.Cart.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    
    private final CartRepository cartRepository;
    private final CourseService courseService;
    private final PurchaseService purchaseService;

    public CartServiceImpl(@Qualifier("cartRepository") 
                           CartRepository cartRepository,
                           CourseService courseService,
                           PurchaseService purchaseService){
        this.cartRepository = cartRepository;
        this.courseService = courseService;
        this.purchaseService = purchaseService;
    }

    @Override
    public RemoveCartItemResponse removeCartItem(Long courseId) {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Cart> cart = cartRepository.findByUserId(user.getId());

        Course course = courseService.getCourseById(courseId);

        if(cart.isEmpty()){
            throw new CartNotFoundException("Cart not found");
        }

        if(course != null){

            if(cart.get().getCourseIds() == null || !cart.get().getCourseIds().contains(courseId)){
                throw new CourseNotInCartException("Nothing to remove");
            }

            cart.get().getCourseIds().remove(courseId);
            cartRepository.save(cart.get());
            return new RemoveCartItemResponse("Course removed from cart");
        }
        throw new CourseNotFoundException("Course not found");

    }

    @Override
    public AddCartItemResponse addItemToCart(Long courseId) {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        
        if(cart.isEmpty()) {
            cart = Optional.of(new Cart());
            cart.get().setUserId(user.getId());
            cart.get().setCourseIds(new HashSet<>());
        }
        
        Course course = courseService.getCourseById(courseId);

        if(course == null){
            throw new CourseNotFoundException("Course not found");
        }
        
        if(cart.get().getCourseIds().contains(courseId)){
            throw new CourseNotInCartException("Course already in cart");
        }

        cart.get().getCourseIds().add(courseId);
        
        cartRepository.save(cart.get());
        
        return new AddCartItemResponse("Course added to cart");
    }
    
    @Override
    public PurchaseResponse purchaseByUserId() {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        
        if(cart.isEmpty()) {
            throw new CartNotFoundException("Cart not found");
        }
        
        PurchaseRequest request = PurchaseRequest.builder()
                .userId(user.getId())
                .courseIds(cart.get().getCourseIds())
                .build();

        PurchaseResponse response = purchaseService.purchaseCourses(request);

        if(response == null){
            throw new FailedPurchaseException("Purchase failed");
        }

        cartRepository.delete(cart.get());
        return response;

    }

    private GetCartByUserIdResponse getActualCartObject(Cart cart){ //calling courseService so im leaving it in the logic class
        return new GetCartByUserIdResponse(
                cart.getId(),
                cart.getUserId(),
                cart.getCourseIds().stream()
                        .map(courseService::getCourseById)
                        .toList()
        );
    }


    @Override   
    public GetCartByUserIdResponse getCartByUserId() {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Cart> cart = cartRepository.findByUserId(user.getId());

        if(cart.isEmpty()) {
            cart = Optional.of(new Cart());
            cart.get().setUserId(user.getId());
            cart.get().setCourseIds(new HashSet<>());
        }

        return getActualCartObject(cart.get());         
    }

}
