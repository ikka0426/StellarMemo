package com.example.stellarmemo.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Note{//日常笔记
    @NonNull
    private String note_id;//笔记标识id
    private String user_id;//所属用户
    private String content;//内容
    //private ArrayList<String> tag;//笔记tag
    //private int like;//点赞数
    //private ArrayList<String> comments;//评论
}
