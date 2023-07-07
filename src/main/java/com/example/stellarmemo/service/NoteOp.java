package com.example.stellarmemo.service;

import com.example.stellarmemo.pojo.Note;
import com.example.stellarmemo.pojo.WebResult;

public interface NoteOp {
    public WebResult createNote(String user_id,String content,String noteid);
    public WebResult modifyNote(String noteid,String content);
    public WebResult deleteNote(String noteid);
    public List<Note> searchByKey(String key,int startIndex,int pageSize);
    public List<Note> searchByTag(String tag1,String tag2,String tag3);
}
