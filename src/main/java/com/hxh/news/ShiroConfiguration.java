package com.hxh.news;

import com.hxh.news.realm.NewsRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShiroConfiguration {

    //创建realm
    @Bean
    public NewsRealm getRealm(){return new NewsRealm();}

    //创建安全管理器
    @Bean
    public SecurityManager securityManager(NewsRealm realm){
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager(realm);
        //将自定义reaLm交给安全管理器统一调度管理
        return  webSecurityManager;
    }

    //配置shiro过滤工厂
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //通用配置
        shiroFilterFactoryBean.setLoginUrl("/admin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin");
        /*
        * key:请求路径
        * value:请求类型
        * */
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/admin/login","anon");
        filterMap.put("/admin/news","perms[user-news]");
        filterMap.put("/admin/types","perms[user-types]");
        filterMap.put("/admin/tags","perms[user-tags]");
        //显示该路径下所有url都需要认证
        filterMap.put("/admin/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;

    }

    //开启shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
