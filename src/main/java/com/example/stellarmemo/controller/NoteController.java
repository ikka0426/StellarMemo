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
        return noteOpImpl.createNote(map.get("user_id"),map.get("title"),map.get("content"),map.get("publicValue"));
    }
    @RequestMapping(value = "/modify")
    public WebResult modifyNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.modifyNote(map.get("note_id"),map.get("content"));
    }
    @RequestMapping(value = "/delete")
    public WebResult deleteNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.deleteNote(map.get("note_id"));
    }
    //审核笔记
    @RequestMapping(value = "/examine")
    public WebResult examineNote(@RequestBody HashMap<String, String> map) {
        return noteOpImpl.examineNote(map.get("note_id"), map.get("state"));
    }

    //返回全部笔记（不分页）
    @RequestMapping(value = "/searchAll")
    public WebResult searchAllNote() {
        return noteOpImpl.searchAllNote();
    }

    @RequestMapping(value = "/count")
    public WebResult countNote() {
        return noteOpImpl.countNote();
    }

    //返回笔记（分页）
    @RequestMapping(value = "/searchByPage")
    public WebResult searchNoteByPage(@RequestBody HashMap<String, Integer> map) {
        return noteOpImpl.searchNoteByPage(map.get("pageSize"), map.get("offset"));
    }

    //根据note_id查询单个note内容
    @RequestMapping(value = "/searchByID")
    public WebResult SearchNoteByID(@RequestBody HashMap<String, String> map) {
        return noteOpImpl.searchNoteByID(map.get("note_id"));
    }
}
