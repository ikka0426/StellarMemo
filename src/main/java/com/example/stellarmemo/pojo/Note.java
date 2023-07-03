package com.example.stellarmemo.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor


public class Note{//日常笔记
    @NonNull
    private String note_id;//笔记标识id
    private String user_id;//所属用户
    private String content;//内容
    private String state;
    //private ArrayList<String> tag;//笔记tag
    //private int like;//点赞数
    //private ArrayList<String> comments;//评论


    public Note(@NonNull String note_id, String user_id, String content, String state) {
        this.note_id = note_id;
        this.user_id = user_id;
        this.content = content;
        this.state = state;
    }

    @NonNull
    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(@NonNull String note_id) {
        this.note_id = note_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
