package com.core.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * BaseModel帮助类，提供自动插入createtime与updatetime
 *
 * @author 何健锋
 */
@Getter
@Setter
public abstract class AutoBaseModel extends BaseModel {
    private static final long serialVersionUID = 7619635418660993201L;
    
    /**
     * 创建时间
     */
    @TableField("createtime")
    private LocalDateTime createtime = LocalDateTime.now();
    
    /**
     * 更新时间
     */
    @TableField("updatetime")
    private LocalDateTime updatetime = LocalDateTime.now();
}
