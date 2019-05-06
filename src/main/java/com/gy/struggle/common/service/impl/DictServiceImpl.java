package com.gy.struggle.common.service.impl;
import com.gy.struggle.common.domain.DictDO;
import com.gy.struggle.common.mapper.DictMapper;
import com.gy.struggle.common.service.DictService;
import com.gy.struggle.common.utils.StringUtils;
import com.gy.struggle.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Override
    public DictDO get(Long id) {
        return dictMapper.get(id);
    }

    @Override
    public List<DictDO> list(Map<String, Object> map) {
        return dictMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dictMapper.count(map);
    }

    @Override
    public int save(DictDO dict) {
        return dictMapper.save(dict);
    }

    @Override
    public int update(DictDO dict) {
        return dictMapper.update(dict);
    }

    @Override
    public int remove(Long id) {
        return dictMapper.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return dictMapper.batchRemove(ids);
    }

    @Override

    public List<DictDO> listType() {
        return dictMapper.listType();
    }

    @Override
    public String getName(String type, String value) {
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("type", type);
        param.put("value", value);
        String rString = dictMapper.list(param).get(0).getName();
        return rString;
    }

    @Override
    public List<DictDO> getHobbyList(UserDO userDO) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "hobby");
        List<DictDO> hobbyList = dictMapper.list(param);

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<DictDO> getSexList() {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "sex");
        return dictMapper.list(param);
    }

    @Override
    public List<DictDO> listByType(String type) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        return dictMapper.list(param);
    }

}
