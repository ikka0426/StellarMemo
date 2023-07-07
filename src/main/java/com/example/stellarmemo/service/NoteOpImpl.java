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
    public WebResult createNote(String user_id,String title, String content, String state) {
        WebResult webResult=new WebResult<>();
        try {
            content = "<head><title>" + title + "</head></title>" + "<body>" + content + "</body>";
            String note_id= IDSet.getShortUuid();
            String time = Timer.getInstanceTime();
            noteDao.createNote(user_id,content,note_id,state,time);
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
    public List<Note> searchByKey(String key, int startIndex, int pageSize) {
        try {

            System.out.println(noteDao.searchByKey(key, startIndex, pageSize));
        } catch (Exception e) {
            System.out.println("查询失败");
            System.out.println(e);
        }
        return noteDao.searchByKey(key, startIndex, pageSize);
    }

    @Override
    public List<Note> searchByTag(String tag1, String tag2, String tag3) {
        try{if (tag2 == "" && tag3 == "" && tag1!="") {
            System.out.println("根据一个tag查询");
            return noteDao.searchByTag1(tag1);
        } else if (tag3 == "" && tag1!="" && tag2!="") {
            System.out.println("根据两个tag查询");
            return noteDao.searchByTag2(tag1, tag2);
        } else if (tag1!="" && tag2!="" && tag3!=""){
            System.out.println("根据三个tag查询");
            return noteDao.searchByTag3(tag1, tag2, tag3);
        }else return null;}
        catch (Exception e){
            System.out.println("查询错误");
            System.out.println(e);
            return null;
        }


    }

    @Override
    public WebResult examineNote(String note_id, String state) {
        WebResult webResult=new WebResult();
        try{
            noteDao.examineNote(note_id, state);
            webResult.success("审核成功");
            System.out.println("审核成功");

        }catch (Exception e){
            webResult.error("审核失败");
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

    @Override
    public WebResult searchNoteByID(String note_id) {
        WebResult webResult = new WebResult();
        try {
            webResult.setData(noteDao.searchNoteByID(note_id));
            webResult.success("查询成功");
            System.out.println("查询成功");
        } catch (Exception e) {
            webResult.error("查询笔记出错");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }
}
