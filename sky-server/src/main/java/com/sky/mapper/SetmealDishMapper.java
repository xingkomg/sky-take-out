package com.sky.mapper;

import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SetmealDishMapper {
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
   List<SetmealDish> getBySetmealId(Long setmealId);



    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);


 /**
  * 根据套餐id删除套餐和菜品的关联关系
  * @param setmealId
  */
 @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
 void deleteBySetmealId(Long setmealId);

 /**
  * 批量保存套餐和菜品的关联关系
  * @param setmealDishes
  */
 void insertBatch(List<SetmealDish> setmealDishes);
    /**
     * 根据菜品id查询对应的套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetemalIdDishIds(List<Long> dishIds);


}
