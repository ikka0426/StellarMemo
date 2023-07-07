package com.example.stellarmemo.controller;

import com.example.stellarmemo.pojo.WebResult;
import com.example.stellarmemo.service.NoteOpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value="/note")
public class NoteContoller {
    @Autowired
    NoteOpImpl noteOpImpl;
    @RequestMapping(value = "/create")
    public WebResult createNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.createNote(map.get("user_id"),map.get("content"),map.get("noteid"));
    }
    @RequestMapping(value = "/modify")
    public WebResult modifyNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.modifyNote(map.get("noteid"),map.get("content"));
    }
    @RequestMapping(value = "/delete")
    public WebResult deleteNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.deleteNote(map.get("noteid"));
    }
    @RequestMapping(value ="/searchByKey")
    public List<Note> searchByList(@RequestBody HashMap<String,String> map){
       return noteOpImpl.searchByKey("%"+map.get("key")+"%",Integer.parseInt(map.get("startIndex")),Integer.parseInt(map.get("pageSize")));
    }
    
    @RequestMapping(value = "/searchByTag")
    public List<Note> searchByTag(@RequestBody HashMap<String,String> map){
        return noteOpImpl.searchByTag(map.get("tag1"),map.get("tag2"),map.get("tag3"));
    }
}
