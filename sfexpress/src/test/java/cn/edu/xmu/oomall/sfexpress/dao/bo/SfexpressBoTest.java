package cn.edu.xmu.oomall.sfexpress.dao.bo;


import cn.edu.xmu.oomall.sfexpress.SfExpressApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = SfExpressApplication.class)
@AutoConfigureMockMvc
//@Transactional
public class SfexpressBoTest {
    @Test
    void routeGeneratorTest() {
        List<Route> routes = RouteResps.generateRandomRouteList(3);
        System.out.println(Arrays.toString(routes.toArray()));
    }
}
