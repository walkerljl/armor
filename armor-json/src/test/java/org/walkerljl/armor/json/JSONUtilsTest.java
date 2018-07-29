package org.walkerljl.armor.json;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author lijunlin
 */
public class JSONUtilsTest {

    @Test
    public void parseMap() {

        String jsonString = "{\"id\":\"walkerljl\",\"name\":\"行寻\"}";

        Map<String, String> map = JSONUtils.parseMap(jsonString);
        Assert.assertEquals("walkerljl", map.get("id"));
        Assert.assertEquals("行寻", map.get("name"));
    }

    @Test
    public void toJSONString() {

        System.out.println(JSONUtils.toJSONString(new ArrayList<String>()));
        //Assert.assertEquals("[]", JSONUtils.toJSONString(new ArrayList<String>()));
    }
}