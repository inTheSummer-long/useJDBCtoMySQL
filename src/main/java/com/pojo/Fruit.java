package com.pojo;

import lombok.*;

/**
 * @author long
 * @date 2022/6/15 14:58
 * @Description: 与数据库中的t_fruit表一一对应的水果类
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class Fruit {
    private Integer fid;
    private String fname;
    private Integer price;
    private Integer fcount;
    private String remark;

    public Fruit(String fname, Integer price, Integer fcount, String remark) {
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }
}
