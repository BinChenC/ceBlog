package com.cb.myblog.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OperationTest {

    @Test
    public void test(){

        Double result = Operation(1.0, 0.0, "division");
        System.out.println(result);
    }

    public Double Operation(Double one,Double two,String method){
        Double operation = null;
        switch (method) {
            case "addition":
                operation = one + two;
                System.out.println(one+"+"+two+"="+(one+two));
                break;
            case "subtraction":
                operation = one - two;
                System.out.println(one+"-"+two+"="+(one-two));
                break;
            case "multiplication":
                operation = one * two;
                System.out.println(one+"*"+two+"="+(one*two));
                break;
            case "division":
                System.out.println(two==0.0);
                if (two == 0.0) {
                    new RuntimeException("除数不能为0。");
                }
                operation = one / two;
                System.out.println(one+"/"+two+"="+(one/two));
                break;
            default:
                System.out.println("想来你的输入有点问题，重试下。");
        }

        return operation;
    }

    @Test
    public void test33(){
        String cc = "CebbzPVwibWlaSB0ZWF1233zPVwibWlaSB0ZWF";
        String[] flag = cc.split("zPVwibWlaSB0ZWF");
        System.out.println(flag.length);

    }


}
