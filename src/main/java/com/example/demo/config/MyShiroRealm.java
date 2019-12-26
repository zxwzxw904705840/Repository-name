package com.example.demo.config;

import com.example.demo.Entity.UserEntity;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MyShiroRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    //如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值
    @Autowired
    private UserService userService;


    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String  userid = token.getUsername();
        String passwd = String.valueOf(token.getPassword());
        Map<String,Object> param = new HashMap<>();
        param.put("userid",userid);
        param.put("passwd",passwd);
        System.out.println(param);
        // 从数据库获取对应用户名密码的用户
        UserEntity userList = userService.getUserById(userid);//.getObject(userid);
        if (userList != null) {
            // 用户为禁用状态

            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            System.out.println(userList.getUserId()+userList.getUserName()+userList.getPassword());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    userList, //用户
                    userList.getPassword(), //密码
                    userList.getUserName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // List<String> permissions = new ArrayList<String>();

        if (principal instanceof UserEntity) {
            UserEntity userLogin = (UserEntity) principal;
            UserEntity user = userService.getUserById(userLogin.getUserId());//.getObject(userLogin.getUserId());
            Set<String> role = new HashSet<>();
            Set<String> permissions = new HashSet<>();
            System.out.println("role="+user.getCharacters());
            System.out.println("role number="+user.getCharacters().ordinal());
            if (user.getCharacters().ordinal()==0) {
                //Administration
                role.add("Administration");
                //  permissions.add("user:create");//用户的创建
                //  permissions.add("items:add");//商品添加权限

            } else if (user.getCharacters().ordinal()==1) {
                //teacher
                role.add("teacher");
            } else if (user.getCharacters().ordinal()==2) {
                //student
                role.add("student");
            }
            authorizationInfo.addRoles(role);
            System.out.println("role----------start-------------------");
            System.out.println(authorizationInfo.getRoles().toString());
            logger.info(authorizationInfo.getRoles().toString());
            //  Set<String> permissions = userService.findPermissionsByUserId(userLogin.getId());
            //   authorizationInfo.addStringPermissions(permissions);
        }
        //   logger.info("---- 获取到以下权限 ----");
        // logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");

        return authorizationInfo;
    }


}