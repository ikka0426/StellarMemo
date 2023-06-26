package com.example.stellarmemo.dao;

import com.example.stellarmemo.pojo.Note;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import  org.apache.ibatis.annotations.Param;

@Mapper
@Repository
public interface NoteDao {
    public void createNote(@Param("user_id")String user_id,
                           @Param("content") String content,
                           @Param("note_id") String note_id,
                           @Param("imageSrc") String imageSrc);//创建笔记

    public void modifyNote(@Param("note_id") String note_id,
                           @Param("content") String content);//修改笔记

    public void deleteNote(@Param("note_id") String note_id);//删除笔记
}
