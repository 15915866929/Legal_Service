package com.core.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体类属性值校验器
 *
 * @author Yuko
 */
public final class ValidatorUtils {
    /**
     * 单例
     */
    private ValidatorUtils() {
    }
    
    /**
     * 获取是否验证通过
     *
     * @param obj 要验证的对象
     * @return true表示验证通过，false表示验证不通过
     */
    public static <T> boolean validateSuccess(T obj) {
        if (obj == null) {
            return true;
        }
        // 为空就验证成功了
        return ValidatorUtils.validate(obj).isEmpty();
    }
    
    /**
     * 验证，取得最详细的信息
     *
     * @param obj 要验证的对象
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T obj) {
        if (obj == null) {
            return new HashSet<>();
        }
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(obj);
    }
    
    /**
     * 读取验证失败的信息
     *
     * @param obj 要验证的对象
     * @return
     */
    public static <T> Set<String> getValidateMessgae(T obj) {
        if (obj == null) {
            return new HashSet<>();
        }
        Set<String> set = new HashSet<>();
        for (ConstraintViolation<T> violation : ValidatorUtils.validate(obj)) {
            set.add(violation.getMessage());
        }
        return set;
    }
}
