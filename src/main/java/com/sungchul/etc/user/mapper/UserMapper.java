package com.sungchul.etc.user.mapper;

import com.sungchul.etc.user.vo.UserVO;

public interface UserMapper {

     UserVO getUser(String userID);

     int insertUser(UserVO userVO);
}
