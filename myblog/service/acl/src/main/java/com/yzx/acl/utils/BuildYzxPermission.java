package com.yzx.acl.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yzx.acl.entity.YzxPermission;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzx
 * @create 2022-12-29 13:16
 */
public class BuildYzxPermission {
    public static List<JSONObject> bulid(List<YzxPermission> treeNodes) throws JSONException {
        List<JSONObject> meuns = new ArrayList<>();
        for (YzxPermission two : treeNodes) {
            JSONObject twoMeun = new JSONObject();
            twoMeun.put("path", two.getPath());
            twoMeun.put("component", two.getComponent());
            twoMeun.put("name", "name_" + two.getId());
            twoMeun.put("hidden", false);

            JSONObject twoMeta = new JSONObject();
            twoMeta.put("title", two.getName());
            twoMeun.put("meta", twoMeta);

            List<YzxPermission> threeMeunList = two.getChild();
            List<JSONObject> children = new ArrayList<>();
            for (YzxPermission three : threeMeunList) {
                if (StringUtils.isEmpty(three.getPath())) continue;

                JSONObject threeMeun = new JSONObject();
                threeMeun.put("path", three.getPath());
                threeMeun.put("component", three.getComponent());
                threeMeun.put("name", "name_" + three.getId());
                threeMeun.put("hidden", false);

                JSONObject threeMeta = new JSONObject();
                threeMeta.put("title", three.getName());
                threeMeun.put("meta", threeMeta);

                children.add(threeMeun);
            }
            twoMeun.put("children", children);
            meuns.add(twoMeun);
        }

        return meuns;
    }
}
