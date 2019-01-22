package com.zql.controller;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by 张启磊 on 2019-1-22.
 * 视频转换类
 */
@Slf4j
public class ConvertVideo {
    //文件地址
    String PATH = "";
    //文件列表
    File[] files={};
    //转码命令
    List<String> convertCommond=new ArrayList<String>();

    /**
     * 转成mp4
     */
    public void convert2Mp4(){
        isExistFile();
        List<String> absolutelyPathList = absolutelyPathNameNameList();
        String type = "";
        String oldName="";
        for (String absolutelyPathName : absolutelyPathList) {
            type = absolutelyPathName.split(".")[1];
         //   type = absolutelyPathName.subs(absolutelyPathName.lastIndexOf("/"))
            oldName = absolutelyPathName.split(".")[0];
            if (checkContentType(type)){
                log.error("该格式不支持转码");
                continue;
            }
            String command = parseCommand(absolutelyPathName, type, oldName);
            if (!convertVideo(command))
                log.error("{}文件转码失败",oldName);

        }


    }

    /**
     * 判断是否存在文件
     *
     */
    public  boolean isExistFile(){
        File file = new File(PATH);
        files = file.listFiles();
        if (!Optional.ofNullable(files).isPresent())
            return false;
        return true;
    }

    /**
     * 获取所有文件全限定名
     * @return
     */
    public  List<String> absolutelyPathNameNameList(){
        List<String> absolutelyPathList= Arrays.asList(files).stream()
                .filter(File::isFile)
                .map(e->PATH+e.getName())
                .collect(Collectors.toList());
        return  absolutelyPathList;
    }

    /**
     * 拼接ffmpeg命令
     * @param absolutelyPath 转码文件绝对路径
     * @param type 文件类型
     * @param newName 转码后文件名
     */
    public String parseCommand(String  absolutelyPath,String type,String newName){
        convertCommond.add("ffmpeg -i");
        convertCommond.add(absolutelyPath);
        convertCommond.add("-vcodec copy -f ");
        convertCommond.add(type);
        convertCommond.add(PATH);
        convertCommond.add(newName);
        convertCommond.add(type);
        return convertCommond.toString();
    }

    /**
     * 调用本地线程转码
     * @param command
     */
    public boolean convertVideo(String command){
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command(command);
            processBuilder.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
    }
    /**
     * 判断是否可以转码
     * @param type 文件类型
     * @return
     */
    public boolean checkContentType(String type){
        String regex="asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv";
        if (type.matches(regex))
            return true;
        return false;
    }




}
