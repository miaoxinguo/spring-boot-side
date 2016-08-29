package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试 jackson api 的使用
 *
 * Created by miaoxinguo on 2016/8/29.
 */
public class TestJackson {
    private static ObjectMapper mapper;
    private static Parent parent;
    private static Child child1;
    private static Child child2;

    @BeforeClass
    public static void init() {
        System.out.println(">>>>>>>>> 初始化测试数据");
        mapper = new ObjectMapper();

        parent = new Parent();
        parent.setPid(1);
        parent.setParentName("parent_name");

        child1 = new Child();
        child1.setId(12);
        child1.setChildName("child_name_1");

        child2 = new Child();
        child2.setId(12);
        child2.setChildName("child_name_2");
    }

    @Before
    public void initEachTest() {
        System.out.println(">>>>>>>>> 重置测试数据");
        parent.getChildren().removeAll(parent.getChildren());
        child1.setParent(null);
        child2.setParent(null);
    }

    /**
     * 普通对象转json
     */
    @Test
    public void testToNormalJsonString() throws JsonProcessingException {
        System.out.println(mapper.writeValueAsString(parent));
    }

    /**
     * 嵌套对象转json
     */
    @Test
    public void testToEmbJsonString() throws JsonProcessingException {
        parent.addChild(child1);
        parent.addChild(child2);
        System.out.println(mapper.writeValueAsString(parent));
    }

    /**
     * 循环引用
     */
    @Test(expected = JsonMappingException.class)
    public void testToLoopJsonString() throws JsonProcessingException {
        child1.setParent(parent);
        parent.addChild(child1);
        System.out.println(mapper.writeValueAsString(parent));
    }

    @Test
    public void testToObject() throws IOException {
        String jsonStr = "{\"pid\":1,\"parentName\":\"parent_name\",\"children\":[]}";
        Parent parent = mapper.readValue(jsonStr, Parent.class);
        System.out.println(parent.toString());
    }
}
