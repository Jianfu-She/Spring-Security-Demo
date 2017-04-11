package com.sjf.web;

import com.sjf.entity.SysUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 * Created by SJF on 2017/2/14.
 */
@RestController
@RequestMapping("/users")
public class RESTController {

    private static Map<Long, SysUser> users = Collections.synchronizedMap(new HashMap<Long, SysUser>());

    @ApiOperation(value = "获取用户列表", notes = "")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<SysUser> getUserList() {
        return new ArrayList<>(users.values());
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute SysUser user) {
        users.put(user.getId(), user);
        return "post success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SysUser getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "delete success";
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute SysUser user) {
        SysUser u = users.get(id);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        users.put(id, u);
        return "put success";
    }
}