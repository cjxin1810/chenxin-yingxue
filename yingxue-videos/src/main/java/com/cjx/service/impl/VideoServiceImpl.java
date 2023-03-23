package com.cjx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjx.entity.Video;
import com.cjx.service.VideoService;
import com.cjx.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author chenxin
* @description 针对表【video(视频)】的数据库操作Service实现
* @createDate 2023-03-23 10:34:59
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

}




