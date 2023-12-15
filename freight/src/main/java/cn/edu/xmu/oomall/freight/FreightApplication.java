/**
 * fixbug
 * @author 张宁坚
 */
package cn.edu.xmu.oomall.freight;

import cn.edu.xmu.javaee.core.jpa.SelectiveUpdateJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.javaee.core", "cn.edu.xmu.oomall.freight"})
@EnableJpaRepositories(value = "cn.edu.xmu.javaee.core.jpa", repositoryBaseClass = SelectiveUpdateJpaRepositoryImpl.class, basePackages = "cn.edu.xmu.oomall.freight.mapper")
public class FreightApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreightApplication.class, args);
    }
}
