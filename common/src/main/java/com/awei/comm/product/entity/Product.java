package com.awei.comm.product.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author shizuwei
 * @since 2021-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tbl_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String pname;

    private BigDecimal pprice;

    private Integer storecount;

    private String description;


}
