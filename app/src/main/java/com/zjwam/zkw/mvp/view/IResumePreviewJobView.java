package com.zjwam.zkw.mvp.view;

import com.zjwam.zkw.entity.ResumePreviewJobBean;

public interface IResumePreviewJobView {
    void showMsg(String msg);
    void setPreview(ResumePreviewJobBean data);
}
