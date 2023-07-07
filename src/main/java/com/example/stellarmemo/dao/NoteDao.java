package com.example.stellarmemo.dao;

import com.example.stellarmemo.pojo.Note;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import  org.apache.ibatis.annotations.Param;

@Mapper
@Repository
public interface NoteDao {
    public void createNote(@Param("user_id")String user_id,@Param("content") String content,@Param("noteid") String noteid);//创建笔记
    public void modifyNote(@Param("noteid") String noteid,@Param("content") String content);//修改笔记
    public void deleteNote(@Param("noteid") String noteid);//删除笔记
    public List<Note> searchByKey(@Param("key") String key,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);
    public List<Note> searchByTag1(@Param("tag1") String tag1);
    public List<Note> searchByTag2(@Param("tag1") String tag1,@Param("tag2") String tag2);
    public List<Note> searchByTag3(@Param("tag1") String tag1,@Param("tag2") String tag2,@Param("tag3") String tag3);
}
