package com.module.returnModel;

import com.module.entity.LegalMechanism;
import lombok.Data;

import java.util.List;

/**
 * @author hsj
 * @date 2017/11/06
 */

@Data
public class ReturnLegalMechanism extends LegalMechanism{

    /**
     * 镇街名字
     */
    private String streetName;
}
