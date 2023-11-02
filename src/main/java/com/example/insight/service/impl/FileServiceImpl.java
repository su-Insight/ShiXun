package com.example.insight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.insight.entity.File;
import com.example.insight.mapper.FileMapper;
import com.example.insight.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/11/1 上午11:19
 * @Version: 1.0
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
}
