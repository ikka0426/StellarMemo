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
    NoteDao noteDao;
    public NoteOpImpl() {
        super();
    }

    @Override
    public WebResult createNote(String user_id,String content,String note_id) {
        WebResult webResult=new WebResult<>();
        try {
            String modifiedContent = ContentManager.modifiedContent(content);
            note_id= IDSet.getShortUuid();
            noteDao.createNote(user_id,modifiedContent,note_id);
            webResult.success("创建笔记成功");

        }catch (Exception e){
            webResult.error("创建笔记失败");
            System.out.println(e.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult modifyNote(String note_id,String content) {
        WebResult webResult=new WebResult<>();
        try{
            noteDao.modifyNote(note_id,content);
            webResult.success("更改笔记成功");

        }catch (Exception e){
            webResult.error("更改笔记失败");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult deleteNote(String note_id) {
        WebResult webResult=new WebResult();
        try{
            noteDao.deleteNote(note_id);
            webResult.success("删除笔记成功");
            System.out.println("删除笔记成功");

        }catch (Exception e){
            webResult.error("删除笔记错误");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }
}
