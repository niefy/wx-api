package com.github.niefy.modules.wx.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface WxAssetsService {
    /**
     *  获取素材总数
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialCountResult materialCount() throws WxErrorException;

    /**
     * 根据类别分页获取非图文素材列表
     * @param type
     * @param page
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int page) throws WxErrorException;

    /**
     * 分页获取图文素材列表
     * @param page
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int page) throws WxErrorException;

    /**
     * 添加图文永久素材
     * @param mpMaterialNewsArticle
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews.WxMpMaterialNewsArticle mpMaterialNewsArticle)throws WxErrorException;

    /**
     * 添加多媒体永久素材
     * @param mediaType
     * @param fileName
     * @param file
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialUploadResult materialFileUpload(String mediaType, String fileName, MultipartFile file) throws WxErrorException, IOException;

    /**
     * 删除素材
     * @param mediaId
     * @return
     * @throws WxErrorException
     */
    boolean materialDelete(String mediaId)throws WxErrorException;
}
