package com.dongbao.test_demo3.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : wangjunyue
 * date: 2025/11/5 10:41
 * Description: com.dongbao.test_demo3.ulits
 * project: test_demo3
 */

@Configuration
/**
 * MybatisPlus  分页配置处理类
 */
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        //paginationInnerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 100 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(100L);
        // 设置数据库类型
//        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
