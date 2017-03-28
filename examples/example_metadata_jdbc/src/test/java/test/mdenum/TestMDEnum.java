package test.mdenum;

import org.junit.Before;
import org.junit.Test;

import com.yonyou.Gender;

/**
 * Created by zengxs on 2016/3/28.
 */
public class TestMDEnum {

    @Test
    public void testEnum() {
        System.out.println(Gender.女.getName());
        System.out.println(Gender.女.getReturnType());
        System.out.println(Gender.男.getName());
        System.out.println(Gender.男.getReturnType());
    }

}
