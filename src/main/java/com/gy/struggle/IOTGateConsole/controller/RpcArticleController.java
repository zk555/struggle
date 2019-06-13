package com.gy.struggle.IOTGateConsole.controller;

import com.gy.struggle.IOTGateConsole.databridge.ReqWebData;
import com.gy.struggle.IOTGateConsole.databridge.RetData;
import com.gy.struggle.IOTGateConsole.domain.IotGateDB;
import com.gy.struggle.IOTGateConsole.domain.RpcMember;
import com.gy.struggle.IOTGateConsole.service.RpcService;
import com.gy.struggle.common.annotation.Log;
import com.gy.struggle.common.config.Constant;
import com.gy.struggle.common.controller.BaseController;
import com.gy.struggle.common.utils.MD5Utils;
import com.gy.struggle.common.utils.PageUtils;
import com.gy.struggle.common.utils.R;
import com.gy.struggle.system.domain.RoleDO;
import com.gy.struggle.system.domain.UserDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class_name: RpcArticleController
 * package: com.gy.struggle.IOTGateConsole.controller
 * describe: TODO 规约维护
 * creat_user: zhaokai@
 * creat_date: 2019/6/11
 * creat_time: 14:05
 **/
@Controller
@RequestMapping("/rpc/article")
@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")
public class RpcArticleController extends BaseController {

    private String prefix="/rpc/article"  ;

    @Autowired
    RpcService rpcService;

    @GetMapping("")
    @RequiresPermissions("rpc:article:article")
    String rpc() {
        return "rpc/article/list";
    }

    @RequiresPermissions("rpc:article:article")
    @GetMapping("/list")
    @Log("获取rpc节点")
    @ResponseBody
    public PageUtils getAllStrategyAllInfo(){
        RetData  ret=rpcService.getAllStrategyAllInfo();
        List<IotGateDB> iotGateDBS = new ArrayList<>();
        List<Object> datas = ret.getData();
        if (datas.size()>0 ){
            List o = (List) datas.get(0);
            for (Object obj :o) {
                iotGateDBS.add((IotGateDB) obj);
            }
        }
        PageUtils pageUtil = new PageUtils(iotGateDBS, iotGateDBS.size());
        return pageUtil;
    }

    @RequiresPermissions("rpc:article:remove")
    @PostMapping("/remove")
    @Log("删除rpc节点")
    @ResponseBody
    public R remove(String pId){
        RetData  ret=rpcService.delOneStrategyByPID(pId);
        if (ret.getRetSig() == 200) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("rpc:article:add")
    @Log("增加rpc节点")
    @GetMapping("/add")
    public String add(ReqWebData args){
        return prefix + "/add";
    }

    @RequiresPermissions("rpc:article:add")
    @Log("保存rpc节点信息")
    @PostMapping("/save")
    @ResponseBody
    R save(IotGateDB iotgate) {
        RetData  ret=rpcService.addOneStrategy(iotgate);
        if (ret.getRetSig() == 200 ) {
            return R.ok();
        }
        return R.error();
    }
}
