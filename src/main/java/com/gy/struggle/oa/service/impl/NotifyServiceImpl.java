package com.gy.struggle.oa.service.impl;

import com.gy.struggle.common.service.DictService;
import com.gy.struggle.common.utils.DateUtils;
import com.gy.struggle.common.utils.PageUtils;
import com.gy.struggle.oa.domain.NotifyDO;
import com.gy.struggle.oa.domain.NotifyDTO;
import com.gy.struggle.oa.domain.NotifyRecordDO;
import com.gy.struggle.oa.mapper.NotifyMapper;
import com.gy.struggle.oa.mapper.NotifyRecordMapper;
import com.gy.struggle.oa.service.NotifyService;
import com.gy.struggle.system.domain.UserDO;
import com.gy.struggle.system.mapper.UserMapper;
import com.gy.struggle.system.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DictService dictService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private NotifyRecordMapper notifyRecordMapper;
    @Override
    public NotifyDO get(Long id) {
        NotifyDO rDO = notifyMapper.get(id);
        rDO.setType(dictService.getName("oa_notify_type", rDO.getType()));
        return rDO;
    }

    @Override
    public List<NotifyDO> list(Map<String, Object> map) {
        List<NotifyDO> notifys = notifyMapper.list(map);
        for (NotifyDO notifyDO : notifys) {
            notifyDO.setType(dictService.getName("oa_notify_type", notifyDO.getType()));
        }
        return notifys;
    }

    @Override
    public int count(Map<String, Object> map) {
        return notifyMapper.count(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(NotifyDO notify) {
        notify.setUpdateDate(new Date());
        int r = notifyMapper.save(notify);
        // 保存到接受者列表中
        Long[] userIds = notify.getUserIds();
        Long notifyId = notify.getId();
        List<NotifyRecordDO> records = new ArrayList<>();
        for (Long userId : userIds) {
            NotifyRecordDO record = new NotifyRecordDO();
            record.setNotifyId(notifyId);
            record.setUserId(userId);
            record.setIsRead(0);
            records.add(record);
        }
        notifyRecordMapper.batchSave(records);
        //给在线用户发送通知(WebSocket)
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (UserDO userDO : sessionService.listOnlineUser()) {
                    for (Long userId : userIds) {
                        if (userId.equals(userDO.getUserId())) {
                            template.convertAndSendToUser(userDO.toString(), "/queue/notifications", "新消息：" + notify.getTitle());
                        }
                    }
                }
            }
        });
        executor.shutdown();
        return r;
    }

    @Transactional
    @Override
    public int remove(Long id) {
        notifyRecordMapper.removeByNotifbyId(id);
        return notifyMapper.remove(id);
    }

    @Transactional
    @Override
    public int batchRemove(Long[] ids) {
        notifyRecordMapper.batchRemoveByNotifbyId(ids);
        return notifyMapper.batchRemove(ids);
    }

    @Override
    public int update(NotifyDO notify) {
        return notifyMapper.update(notify);
    }

    @Override
    public PageUtils selfList(Map<String, Object> map) {
        List<NotifyDTO> rows = notifyMapper.listDTO(map);
        for (NotifyDTO notifyDTO : rows) {
            notifyDTO.setBefore(DateUtils.getTimeBefore(notifyDTO.getUpdateDate()));
            notifyDTO.setSender(userMapper.get(notifyDTO.getCreateBy()).getName());
        }
        PageUtils page = new PageUtils(rows, notifyMapper.countDTO(map));
        return page;
    }
}
