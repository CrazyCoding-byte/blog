package com.yzx.acl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AclApplicationTests {

    @Test
    void contextLoads() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        boolean contains = ids.containsAll(objects);
        System.out.println(contains);
    }

}
