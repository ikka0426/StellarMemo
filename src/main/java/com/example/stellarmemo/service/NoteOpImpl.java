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
}
