package com.gy.struggle.IOTGateConsole.controller;

import com.gy.struggle.IOTGateConsole.databridge.ReqWebData;
import com.gy.struggle.IOTGateConsole.databridge.RetData;
import com.gy.struggle.IOTGateConsole.domain.RpcMember;
import com.gy.struggle.IOTGateConsole.service.RpcService;
import com.gy.struggle.common.annotation.Log;
import com.gy.struggle.common.config.Constant;
import com.gy.struggle.common.controller.BaseController;
import com.gy.struggle.common.utils.PageUtils;
import com.gy.struggle.common.utils.Query;
import com.gy.struggle.common.utils.R;
import com.gy.struggle.system.domain.RoleDO;
import com.gy.struggle.system.domain.UserDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class_name: RpcMemberController
 * package: com.gy.struggle.IOTGateConsole.controller
 * describe: TODO 网关配置
 * creat_user: zhaokai@
 * creat_date: 2019/6/11
 * creat_time: 14:06
 **/
@Controller
@RequestMapping("/rpc/member")
@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")
public class RpcMemberController  extends BaseController {

    private String prefix="/rpc/member"  ;

    @Autowired
    RpcService rpcService;

    @GetMapping("")
    @RequiresPermissions("rpc:member:member")
    String rpc() {
        return prefix+"/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params){
        RetData  ret=rpcService.getAllGateInfo();
        PageUtils pageUtil = new PageUtils(ret.getData(), ret.getData().size());
        return pageUtil;
    }

    @GetMapping("/getAllStrategeFromDB")
    @ResponseBody
    public RetData getAllStrategeFromDB(){
        RetData  ret=rpcService.getAllStrategeFromDB();
        return ret;
    }

    @GetMapping("/strategy")
    @Log("获取规约列表")
    @ResponseBody()
    List<RpcMember> list() {
        RetData  ret=rpcService.getAllStrategeFromDB();
        List<RpcMember> rpcMembers = new ArrayList<>();
        List<Object> datas = ret.getData();
        if (datas.size()>0 ){
            for (Object obj :datas) {
                rpcMembers.add((RpcMember) obj);
            }
        }
        return rpcMembers;
    }

    @RequiresPermissions("rpc:member:edit")
    @PostMapping("/update")
    @Log("更新规约")
    @ResponseBody
    public R updateStrategy2Node(ReqWebData args){
        RetData  ret=rpcService.updateStrategy2Node(args);
        if (ret.getRetSig() == 200) {
            return R.ok();
        }
        return R.error();
    }

}
