package com.example.stellarmemo.dao;

import com.example.stellarmemo.pojo.Note;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import  org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Repository
public interface NoteDao {

    public void createNote(@Param("user_id")String user_id,
                           @Param("content") String content,
                           @Param("note_id") String note_id,
                           @Param("state") String state,
                           @Param("time") String time);//创建笔记

    public void modifyNote(@Param("note_id") String note_id,
                           @Param("content") String content);//修改笔记

    public void deleteNote(@Param("note_id") String note_id);//删除笔记

    public void examineNote(@Param("note_id") String note_id,
                            @Param("state") String state);//删除笔记

    List<Note> searchAllNote();

    int countNote();

    List<Note> searchNoteByPage(@Param("pageSize") int pageSize,
                                @Param("offset") int offset);

    Note searchNoteByID(@Param("note_id") String note_id);
    public List<Note> searchByKey(@Param("key") String key,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);
    public List<Note> searchByTag1(@Param("tag1") String tag1);
    public List<Note> searchByTag2(@Param("tag1") String tag1,@Param("tag2") String tag2);
    public List<Note> searchByTag3(@Param("tag1") String tag1,@Param("tag2") String tag2,@Param("tag3") String tag3);
}
