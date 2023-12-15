package cn.edu.xmu.javaee.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huang zhong
 * @date 2023-dgn-free-001
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.SOURCE)
public @interface CopyFrom {
    Class<?>[] value();
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Exclude {
        Class<?>[] value();
    }
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Of {
        Class<?>[] value();
    }

}

