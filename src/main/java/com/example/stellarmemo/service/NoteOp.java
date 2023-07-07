package com.example.stellarmemo.service;

import com.example.stellarmemo.pojo.Note;
import com.example.stellarmemo.pojo.WebResult;

public interface NoteOp {
    public WebResult createNote(String user_id,String title,String content,String state);
    public WebResult modifyNote(String note_id,String content);
    public WebResult deleteNote(String note_id);
    public WebResult examineNote(String note_id);
    public WebResult searchAllNote();
    public WebResult countNote();
    public WebResult searchNoteByPage(int pageSize, int offset);
    public WebResult searchNoteByID(String note_id);
}
