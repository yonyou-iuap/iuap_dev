package com.yonyou.iuap.example.utils;

import com.yonyou.iuap.example.entity.meta.EnumVo;
import com.yonyou.iuap.persistence.bs.jdbc.meta.model.MDEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengxs on 2016/12/19.
 */
public class EnumUtils {

    /**
     * 枚举获取前台转换类
     * 
     * @param enumArray
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends MDEnum> Map<String, List<EnumVo>> loadEnum(Class<T>... enumArray) throws Exception {
        Map<String, List<EnumVo>> valueName = new HashMap<String, List<EnumVo>>();

        for (Class t : enumArray) {
            MDEnum[] enums = MDEnum.values(t);
            List<EnumVo> result = new ArrayList<EnumVo>();
            for (MDEnum mdEnum : enums) {
                EnumVo vo = new EnumVo();
                vo.setName(mdEnum.getName());
                vo.setValue(mdEnum.value());
                result.add(vo);
            }
            valueName.put(t.getSimpleName().toLowerCase(), result);
        }
        return valueName;
    }
}
