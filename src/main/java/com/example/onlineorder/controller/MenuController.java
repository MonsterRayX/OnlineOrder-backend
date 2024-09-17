package com.example.onlineorder.controller;

import com.example.onlineorder.entity.MenuItemEntity;
import com.example.onlineorder.model.RestaurantDto;
import com.example.onlineorder.service.MenuItemService;
import com.example.onlineorder.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RestController
public class MenuController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    public MenuController(RestaurantService restaurantService, MenuItemService menuItemService) {
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public List<MenuItemEntity> getMenuByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }

    @GetMapping("/restaurants/menu")
    public List<RestaurantDto> getMenuForAllRestaurants(){
        return restaurantService.getRestaurants();
    }
}
