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
public class NoteController {
    @Autowired
    NoteOpImpl noteOpImpl;
    @RequestMapping(value = "/create")
    public WebResult createNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.createNote(map.get("user_id"),map.get("content"),map.get("note_id"));
    }
    @RequestMapping(value = "/modify")
    public WebResult modifyNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.modifyNote(map.get("note_id"),map.get("content"));
    }
    @RequestMapping(value = "/delete")
    public WebResult deleteNote(@RequestBody HashMap<String, String> map){
        return noteOpImpl.deleteNote(map.get("note_id"));
    }
}
