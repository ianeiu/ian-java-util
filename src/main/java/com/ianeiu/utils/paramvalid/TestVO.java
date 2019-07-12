package com.ianeiu.utils.paramvalid;

public class TestVO {
    @ParamValid(refField = "status", desc = "标题", validStrLength = true, maxLength = 5)
    private String title;

    @ParamValid(refField = "status", desc = "正文")
    private String content;

    @ParamValid(notNull = true, desc = "状态")
    private String status;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
