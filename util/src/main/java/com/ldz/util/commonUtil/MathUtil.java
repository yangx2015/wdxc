package com.ldz.util.commonUtil;

import java.math.BigDecimal;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2017/11/21
 */
public class MathUtil {

    public static float retatin1(float r){
        return retatin(r,1,4);
    }
    public static float retatin(float r,int num){
        return retatin(r,num,4);
    }
    public static float retatin(float r,int num,int roundingMode){
        BigDecimal bd  =   new  BigDecimal((double)r);
        bd   =  bd.setScale(2,roundingMode);
        return bd.floatValue();
    }
}
