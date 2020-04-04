package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.common.exception.RRException;
import com.github.niefy.modules.wx.service.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class FileTransferServiceImpl implements FileTransferService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${server.uploadBaseDir}")
    String uploadBaseDir;

    /**
     * 上传文件转存
     *
     * @return 返回图片在web服务器中的相对路径
     */
    @Override
    public String saveFile(MultipartFile file, String moduleName) {
        if (StringUtils.isEmpty(moduleName)) {
            moduleName = "common";
        }
        String relativePath = "/upload/" + moduleName + "/";
        File dir = new File(uploadBaseDir + relativePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String orionFilename = file.getOriginalFilename();
        String prefix = orionFilename.substring(orionFilename.lastIndexOf("."));
        String newFilename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
            + new Random().nextInt(999999) + prefix;
        try {
            file.transferTo(new File(uploadBaseDir + relativePath + newFilename));
        } catch (Exception e) {
            logger.error("文件保存出错", e);
            throw new RRException("文件保存出错");
        }
        return relativePath + newFilename;
    }
}
