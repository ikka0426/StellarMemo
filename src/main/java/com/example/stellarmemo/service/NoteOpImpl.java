package com.example.stellarmemo.service;

import com.example.stellarmemo.dao.NoteDao;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.Note;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class NoteOpImpl implements NoteOp{
    @Autowired
    NoteDao noteDao;
    public NoteOpImpl() {
        super();
    }

    @Override
    public WebResult createNote(String user_id,String content,String note_id,String state) {
        WebResult webResult=new WebResult<>();
        try {
            String modifiedContent = ContentManager.modifiedContent(content);
            note_id= IDSet.getShortUuid();
            noteDao.createNote(user_id,modifiedContent,note_id,state);
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

    @Override
    public WebResult searchAllNote() {
        WebResult webResult = new WebResult();
        try {
            webResult.setData(noteDao.searchAllNote());
            webResult.success("查询成功");
            System.out.println("查询成功");
        } catch (Exception e) {
            webResult.error("查询笔记出错");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult countNote() {
        WebResult webResult = new WebResult();
        try {
            webResult.setData(noteDao.countNote());
            webResult.success("查询笔记数成功");
            System.out.println("查询笔记数成功");
        } catch (Exception e) {
            webResult.error("查询笔记数出错");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult searchNoteByPage(int pagesize, int offset) {
        WebResult webResult = new WebResult();
        try {
            List<Note> notes = noteDao.searchNoteByPage(pagesize, offset);

            for(Note note : notes) {
                note.setContent(ContentManager.stripHtmlTags(note.getContent()));
            }

            webResult.setData(notes);
            webResult.success("查询成功");
            System.out.println("查询成功");
        } catch (Exception e) {
            webResult.error("查询笔记出错");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }
}
