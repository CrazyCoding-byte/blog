package com.yzx.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@MapperScan("com.yzx.acl.mapper")
@ComponentScan("com.yzx")
public class AclApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AclApplication.class, args);
        int[] number = new int[]{1, 3, 552, 5151, 51515, 52};
        for (int i = 0; i < number.length - 1; i++) {
            for (int j = i + 1; j < number.length; j++) {
                if (number[j] < number[i]) {
                    int temp = number[i];
                    number[i] = number[j];
                    number[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(number));
    }

}
