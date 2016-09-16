import org.junit.Test;

public class TestVar {

    /**
     * 交换两个变量的值
     */
    @Test
    public void test(){
        int a=  2;
        int b = 5;

        System.out.println((a | b) & b);
        System.out.println((a | b) & a);
    }

}
