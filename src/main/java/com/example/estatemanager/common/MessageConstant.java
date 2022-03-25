package com.example.estatemanager.common;

/**
 * @Auth: DJC
 * @Desc: 返回结果消息提示常量类
 */
public class MessageConstant {
    //---------------------------小区（Community）操作消息提示信息---------------------------------------
    public static final String COMMUNITY_SEARCH_SUCCESS="查询小区列表信息成功！";
    public static final String COMMUNITY_ADD_SUCCESS="新增小区信息成功！";
    public static final String COMMUNITY_UPDATE_SUCCESS="修改小区信息成功！";
    public static final String COMMUNITY_DELETE_SUCCESS="删除小区信息成功！";
    public static final String COMMUNITY_PIC_UPLOAD_SUCCESS="小区缩略图上传成功！";
    public static final String COMMUNITY_PIC_DEL_SUCCESS = "小区缩略图删除成功！";
    public static final String COMMUNITY_FIND_BY_ID_SUCCESS = "根据主键获取小区对象成功！";
    public static final String COMMUNITY_UPDATE_STATUS_SUCCESS = "小区状态信息更新成功！";
    //---------------------------楼栋（Building）操作消息提示信息---------------------------------------
    public static final String BUILDING_ADD_SUCCESS="添加楼栋操作成功执行";
    public static final String BUILDING_UPDATE_SUCCESS="修改楼栋信息成功";
    public static final String BUILDING_DELETE_SUCCESS="删除楼栋操作已执行";
    public static final String BUILDING_SEARCH_SUCCESS="查询成功";
    //public static final
    //public static final
    //public static final
    //---------------------------系统提示信息----------------------------------------------------------
    public static final String SYSTEM_BUSY = "系统繁忙，请求稍后重试！";
    //---------------------------文件上传提示信息-------------------------------------------------------
    public static final String NO_FILE_SELECTED = "未选择上传的文件,请求选择后上传!";
    public static final String NO_WRITE_PERMISSION = "上传目录没有写权限!";
    public static final String INCORRECT_DIRECTORY_NAME = "目录名不正确!";
    public static final String SIZE_EXCEEDS__LIMIT = "上传文件大小超过限制!";
    public static final String FILE_TYPE_ERROR = "文件类型错误，只允许上传JPG/PNG/JPEG/GIF等图片类型的文件!";

}
