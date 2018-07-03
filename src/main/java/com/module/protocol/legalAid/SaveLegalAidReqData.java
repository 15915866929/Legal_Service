package com.module.protocol.legalAid;

import lombok.Data;

/**
 * @author chc
 * @create 2017-11-18 11:33
 **/
@Data
public class SaveLegalAidReqData {

    private String applicant_name;
    private String phone;
    private String address;
    private String domicile;
    private String appeal;
    private String reason;
    private String verificationCode;
}
