package com.quensty.serviceAcl.controller;

import com.quensty.commonutils.CommonResult;
import com.quensty.serviceAcl.entity.Permission;
import com.quensty.serviceAcl.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public CommonResult indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenu();
        return CommonResult.success().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public CommonResult remove(@PathVariable String id) {
        permissionService.removeChildById(id);
        return CommonResult.success();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public CommonResult doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return CommonResult.success();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public CommonResult toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return CommonResult.success().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public CommonResult save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public CommonResult updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return CommonResult.success();
    }

}

