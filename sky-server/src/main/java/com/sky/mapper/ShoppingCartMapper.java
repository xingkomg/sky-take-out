package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    List<ShoppingCart> list(ShoppingCart shoppingCart);
    @Update("update sky_take_out.shopping_cart set number=#{number} where id=#{id}")
    void updateNumerById(ShoppingCart shoppingCart);

    @Insert("insert into sky_take_out.shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "values (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from sky_take_out.shopping_cart where user_id=#{userId}")
    void delete(Long userid);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据用户ID及菜品ID删除购物车数据
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId} and dish_id = #{dishId}")
    void deleteByDishId(Long userId, Long dishId);
    /**
     * 根据用户ID及套餐ID删除购物车数据
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId} and setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long userId, Long setmealId);

    void insertBatch(List<ShoppingCart> shoppingCartlist);
}
