package com.example.onlineorder.service;

import com.example.onlineorder.entity.MenuItemEntity;
import com.example.onlineorder.entity.RestaurantEntity;
import com.example.onlineorder.model.MenuItemDto;
import com.example.onlineorder.model.RestaurantDto;
import com.example.onlineorder.repository.MenuItemRepository;
import com.example.onlineorder.repository.RestaurantRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository RestaurantRepository;

    public RestaurantService(
            RestaurantRepository restaurantRespository,
            MenuItemRepository menuItemRepository
    ){
        this.RestaurantRepository = restaurantRespository;
        this.menuItemRepository = menuItemRepository;
    }

    @Cacheable("restaurants")
    public List<RestaurantDto> getRestaurants(){
        List<RestaurantEntity> restaurantEntities = RestaurantRepository.findAll();
        List< MenuItemEntity> menuItemEntities = menuItemRepository.findAll();
        Map<Long, List<MenuItemDto>> groupedMenuItems = new HashMap<>();
        for(MenuItemEntity menuItemEntity : menuItemEntities){
            List<MenuItemDto> group = groupedMenuItems.computeIfAbsent(menuItemEntity.restaurantId(), k -> new ArrayList<>());
            MenuItemDto menuItemDto = new MenuItemDto(menuItemEntity);
            group.add(menuItemDto);
        }
        List<RestaurantDto> results = new ArrayList<>();
        for (RestaurantEntity restaurantEntity : restaurantEntities) {
            RestaurantDto restaurantDto = new RestaurantDto(restaurantEntity, groupedMenuItems.get(restaurantEntity.id()));
            results.add(restaurantDto);
        }
        return results;
    }
}
