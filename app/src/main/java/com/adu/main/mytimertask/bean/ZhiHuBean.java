package com.adu.main.mytimertask.bean;

/**
 * Created by dell on 2016/6/3.
 */
public class ZhiHuBean
{
    private String url;
    private String title;
    private String type;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ZhiHuBean [url=" + url + ", title=" + title + ", type=" + type
                + "]";
    }
}
