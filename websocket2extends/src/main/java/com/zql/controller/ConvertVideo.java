package com.zql.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@Slf4j
public class ConvertVideo {
    //ffmpeg地址
    String FFPATH= "C:\\Users\\38213\\Desktop\\ffmpeg-4.1-win64-static\\bin\\ffmpeg.exe";
    //文件地址
    String PATH = "C:\\Users\\38213\\Desktop\\convert\\";
    //文件列表
    File[] files={};
    //转码命令
    List<String> convertCommond=new ArrayList<String>();

    /**
     * 转成mp4
     */
    public  void convert2Mp4() throws InterruptedException {
        String type   ;
        String oldName  ;
        String realName ;
        if (!isExistFile())
            log.info("该文件夹为空");
        List<String> absolutelyPathList = absolutelyPathNameNameList();
        for (String absolutelyPathName : absolutelyPathList) {
            realName = absolutelyPathName.substring(absolutelyPathName.lastIndexOf("\\")+1);
            oldName = realName.split("\\.")[0];
            type = realName.split("\\.")[1];
            if (!checkContentType(type)){
                log.error("该格式不支持转码");
                continue;
            }
            List<String> command = parseCommand(absolutelyPathName,oldName,"mp4");
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
     * @param  absolutelyPathName 转码文件绝对路径
     * @param type 文件类型
     * @param type 转码后文件名
     * ffmpeg.exe -i path\01.flv -vcodec copy -f mp4  path\01.mp4
     */
    public List<String> parseCommand(String absolutelyPathName, String oldName,String type){
        //每个元素代表一个参数
        convertCommond.add(FFPATH);
        convertCommond.add("-i");
        convertCommond.add(absolutelyPathName);
        convertCommond.add("-vcodec");
        convertCommond.add("copy");
        convertCommond.add("-f");
        convertCommond.add(type);
        convertCommond.add(PATH+oldName+"."+type);
        return convertCommond;
    }

    /**
     * 调用本地线程转码
     * @param command
     */
    public boolean convertVideo(List<String> command) throws InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            processBuilder.command(command);
            Process process = processBuilder.start();
            //等待子进程完成之后再返回
            process.waitFor();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //ffmpeg -i  C:\Users\38213\Desktop\ffmpeg-4.1-win64-static\01.flv -vcodec copy -f mp4 C:\Users\38213\Desktop\ffmpeg-4.1-win64-static\01.mp4
    /**
     * 判断是否可以转码
     * @param type 文件类型
     * @return
     */
    public boolean checkContentType(String type){
        String regex="asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv";
        if (!type.matches(regex))
            return false;
        return true;
    }




}
