package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /*
    新增套餐，同时保存套餐和菜品的关系
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐和套餐关联的菜品
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
