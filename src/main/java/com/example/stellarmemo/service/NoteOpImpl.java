package com.example.stellarmemo.service;

import com.example.stellarmemo.dao.NoteDao;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.Note;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteOpImpl implements NoteOp{
    @Autowired
    NoteDao notedao;
    public NoteOpImpl() {
        super();
    }

    @Override
    public WebResult createNote(String user_id,String content,String noteid) {
        WebResult webResult=new WebResult<>();
        try {
            noteid= IDSet.getShortUuid();
            notedao.createNote(user_id,content,noteid);
            webResult.success("创建笔记成功");

        }catch (Exception e){
            webResult.error("创建笔记失败");
            System.out.println(e.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult modifyNote(String noteid,String content) {
        WebResult webResult=new WebResult<>();
        try{
            notedao.modifyNote(noteid,content);
            webResult.success("更改笔记成功");

        }catch (Exception e){
            webResult.error("更改笔记失败");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult deleteNote(String noteid) {
        WebResult webResult=new WebResult();
        try{
            notedao.deleteNote(noteid);
            webResult.success("删除笔记成功");
            System.out.println("删除笔记成功");

        }catch (Exception e){
            webResult.error("删除笔记错误");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }
    @Override
    public List<Note> searchByKey(String key, int startIndex, int pageSize) {
        try {

            System.out.println(notedao.searchByKey(key, startIndex, pageSize));
        } catch (Exception e) {
            System.out.println("查询失败");
            System.out.println(e);
        }
        return notedao.searchByKey(key, startIndex, pageSize);
    }

    @Override
    public List<Note> searchByTag(String tag1, String tag2, String tag3) {
        try{if (tag2 == "" && tag3 == "" && tag1!="") {
            System.out.println("根据一个tag查询");
            return notedao.searchByTag1(tag1);
        } else if (tag3 == "" && tag1!="" && tag2!="") {
            System.out.println("根据两个tag查询");
            return notedao.searchByTag2(tag1, tag2);
        } else if (tag1!="" && tag2!="" && tag3!=""){
            System.out.println("根据三个tag查询");
            return notedao.searchByTag3(tag1, tag2, tag3);
        }else return null;}
        catch (Exception e){
            System.out.println("查询错误");
            System.out.println(e);
            return null;
        }


    }
}
