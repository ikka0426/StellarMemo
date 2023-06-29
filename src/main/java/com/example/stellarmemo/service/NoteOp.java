package com.example.stellarmemo.service;

import com.example.stellarmemo.pojo.Note;
import com.example.stellarmemo.pojo.WebResult;

public interface NoteOp {
    public WebResult createNote(String user_id,String content,String note_id);
    public WebResult modifyNote(String note_id,String content);
    public WebResult deleteNote(String note_id);
}
