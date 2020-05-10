package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.modules.wx.dto.PageSizeConstant;
import com.github.niefy.modules.wx.service.WxAssetsService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@CacheConfig(cacheNames = {"wxAssetsServiceCache"})
public class WxAssetsServiceImpl implements WxAssetsService {
    @Autowired
    WxMpService wxMpService;

    @Override
    @Cacheable(key="methodName")
    public WxMpMaterialCountResult materialCount() throws WxErrorException {
        return wxMpService.getMaterialService().materialCount();
    }

    @Override
    @Cacheable(key="methodName + #type + #page")
    public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int page) throws WxErrorException {
        final int pageSize = PageSizeConstant.PAGE_SIZE_SMALL;
        int offset=(page-1)* pageSize;
        return wxMpService.getMaterialService().materialFileBatchGet(type,offset, pageSize);
    }

    @Cacheable(key="methodName + #page")
    @Override
    public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int page) throws WxErrorException {
        final int pageSize = PageSizeConstant.PAGE_SIZE_SMALL;
        int offset=(page-1)*pageSize;
        return wxMpService.getMaterialService().materialNewsBatchGet(offset, pageSize);
    }

    @Override
    @CacheEvict(allEntries = true)
    public WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews.WxMpMaterialNewsArticle mpMaterialNewsArticle) throws WxErrorException {
        WxMpMaterialNews wxMpMaterialNewsSingle = new WxMpMaterialNews();
        mpMaterialNewsArticle.setShowCoverPic(true);
        wxMpMaterialNewsSingle.addArticle(mpMaterialNewsArticle);
        return wxMpService.getMaterialService().materialNewsUpload(wxMpMaterialNewsSingle);
    }

    @Override
    @CacheEvict(allEntries = true)
    public WxMpMaterialUploadResult materialFileUpload(String mediaType, String fileName, MultipartFile file) throws WxErrorException, IOException {
        String originalFilename=file.getOriginalFilename();
        File tempFile = File.createTempFile(fileName+"--", originalFilename.substring(originalFilename.lastIndexOf(".")));
        file.transferTo(tempFile);
        WxMpMaterial wxMaterial = new WxMpMaterial();
        wxMaterial.setFile(tempFile);
        wxMaterial.setName(fileName);
        if(WxConsts.MediaFileType.VIDEO.equals(mediaType)){
            wxMaterial.setVideoTitle(fileName);
        }
        WxMpMaterialUploadResult res = wxMpService.getMaterialService().materialFileUpload(mediaType,wxMaterial);
        tempFile.deleteOnExit();
        return null;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean materialDelete(String mediaId) throws WxErrorException {
        return wxMpService.getMaterialService().materialDelete(mediaId);
    }
}
