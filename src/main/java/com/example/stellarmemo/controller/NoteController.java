package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.NoteOpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value="/note")
public class NoteController {
    @Autowired
    NoteOpImpl noteOpImpl;
    @RequestMapping(value = "/create")
    public WebResult createNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.createNote(map.get("user_id"),map.get("content"),map.get("note_id"),map.get("state"));
    }
    @RequestMapping(value = "/modify")
    public WebResult modifyNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.modifyNote(map.get("note_id"),map.get("content"));
    }
    @RequestMapping(value = "/delete")
    public WebResult deleteNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.deleteNote(map.get("note_id"));
    }

    //返回全部笔记（不分页）
    @RequestMapping(value = "/searchAll")
    public WebResult searchAllNote() {
        return noteOpImpl.searchAllNote();
    }

    @RequestMapping(value = "count")
    public WebResult countNote() {
        return noteOpImpl.countNote();
    }

    //返回笔记（分页）
    @RequestMapping(value = "searchByPage")
    public WebResult searchNoteByPage(@RequestBody HashMap<String, Integer> map) {
        return noteOpImpl.searchNoteByPage(map.get("pagesize"), map.get("offset"));
    }
}
