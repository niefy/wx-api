package com.github.niefy.modules.wx.manage;

import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.dto.RegexConstant;
import com.github.niefy.modules.wx.service.FileTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    FileTransferService fileTransferService;

    @RequestMapping("/uploadImg")
    public R uploadImg(MultipartFile file, String savePath) {
        String filename = file.getOriginalFilename();
        if (!Pattern.matches(RegexConstant.IMAGE_FILE_NAME, filename)) {
            return R.error("文件" + filename + "格式不支持");
        }
        if (file.getSize() > 1024 * 1024) {
            return R.error("只可上传1以下的文件");
        }
        String path = fileTransferService.saveFile(file, savePath);
        return R.ok().put(path);
    }
}
