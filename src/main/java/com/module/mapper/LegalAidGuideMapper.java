package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.LegalAid;
import com.module.entity.LegalAidGuide;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Mapper
 */
@Repository
public interface LegalAidGuideMapper extends BaseMapper<LegalAidGuide> {

    List<LegalAidGuide> selectByStatus(@Param("status") int status);
}
