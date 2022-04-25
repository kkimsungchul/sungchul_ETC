package com.sungchul.etc.findtext.mapper;


import com.sungchul.etc.findtext.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
@Mapper
public interface FindTextMapper {
    int insertFileInfo(FileVO fileVO);
    List<FileVO> getFileList(String userId);

    boolean findTextFile(FileVO fileVO);
}
