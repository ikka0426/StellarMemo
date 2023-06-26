package com.example.stellarmemo.service;

import com.example.stellarmemo.dao.NoteDao;
import com.example.stellarmemo.pojo.IDSet;
import com.example.stellarmemo.pojo.Note;
import com.example.stellarmemo.pojo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NoteOpImpl implements NoteOp{
    @Autowired
    NoteDao noteDao;
    public NoteOpImpl() {
        super();
    }

    //没有上传图片
    @Override
    public WebResult createNote(String user_id,String content,String note_id, String imageSrc) {
        WebResult webResult=new WebResult<>();
        try {
            if(!imageSrc.equals("null")){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
                String imageName = formatter.format(date);
                File target = new File("D:/IDEA/StellarMemo/src/main/resources/image/" + imageName + ".jpg");
                if (!target.exists()) {
                        target.createNewFile();
                }
                File src = new File(imageSrc);

                FileInputStream fis = new FileInputStream(src);
                FileOutputStream fos = new FileOutputStream(target);

                int len = 0;
                byte[] data = new byte[20];
                while ((len = fis.read(data)) != -1) {
                    fos.write(data, 0, len);
                }

                fis.close();
                fos.close();
            }
            note_id= IDSet.getShortUuid();
            noteDao.createNote(user_id,content,note_id, imageSrc);
            webResult.success("创建笔记成功");

        }catch (Exception e){
            webResult.error("创建笔记失败");
            System.out.println(e.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult modifyNote(String note_id,String content) {
        WebResult webResult=new WebResult<>();
        try{
            noteDao.modifyNote(note_id,content);
            webResult.success("更改笔记成功");

        }catch (Exception e){
            webResult.error("更改笔记失败");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }

    @Override
    public WebResult deleteNote(String note_id) {
        WebResult webResult=new WebResult();
        try{
            noteDao.deleteNote(note_id);
            webResult.success("删除笔记成功");
            System.out.println("删除笔记成功");

        }catch (Exception e){
            webResult.error("删除笔记错误");
            System.out.println(webResult.getMessage());
        }
        return webResult;
    }
}
