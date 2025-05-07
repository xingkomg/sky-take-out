package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //购物车中存在，数量+1
        if(list!=null&&list.size()>0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1);
            shoppingCartMapper.updateNumerById(cart);
        }
        else {
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId!=null){
                //添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName((dish.getName()));
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else {
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName((setmeal.getName()));
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);


        }

    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        //获取用户id
        Long userid = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userid)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void cleanShoppingCart() {
        Long userid = BaseContext.getCurrentId();
        shoppingCartMapper.delete(userid);
    }

    /**
     * 减去购物车商品
     * @param shoppingCartDTO
     */
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        //获取当前微信用户ID
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if(list != null && list.size()>0){
            ShoppingCart cart = list.get(0);
            if(cart.getNumber() > 1) {
                cart.setNumber(cart.getNumber() - 1);
            }else {
                //本次操作减去购物车的是菜品
                Long dishId = shoppingCartDTO.getDishId();
                if(dishId != null) {
                    shoppingCartMapper.deleteByDishId(userId, dishId);
                }else {
                    //本次操作减去购物车的是套餐
                    Long setmealId = shoppingCartDTO.getSetmealId();
                    shoppingCartMapper.deleteBySetmealId(userId, setmealId);
                }
            }
            shoppingCartMapper.updateNumerById(cart);
        }
    }

}
