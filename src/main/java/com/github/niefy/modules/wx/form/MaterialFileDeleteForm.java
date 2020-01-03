package com.github.niefy.modules.wx.form;

import com.github.niefy.common.utils.Json;

public class MaterialFileDeleteForm {
    String mediaId;
    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
