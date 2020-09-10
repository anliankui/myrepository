package cn.smbms.dao.role;

import cn.smbms.pojo.Role;

import java.util.List;

/**
 * @program: mybatis
 * @description:
 * @author: alk
 * @create: 2020-08-21 18:36:47
 **/

public interface RoleMapper {
    List<Role> getRoleList();
}
