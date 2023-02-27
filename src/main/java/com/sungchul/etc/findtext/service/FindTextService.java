package com.sungchul.etc.findtext.service;


import com.sungchul.etc.common.util.DateService;
import com.sungchul.etc.config.jwt.util.JwtTokenUtil;
import com.sungchul.etc.findtext.mapper.FindTextMapper;
import com.sungchul.etc.findtext.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Slf4j
@Service("FindTextService")
public class FindTextService {

    @Value("${spring.servlet.multipart.location}")
    private String fileUploadPath;

    @Autowired
    DateService dateService;

    @Autowired
    FindTextMapper findTextMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * fildUpload , 파일 업로드
     * @param file
     * @return String
     * */
    public String fileUpload(MultipartFile file){
//        log.info("file org name = {}", file.getOriginalFilename());
//        log.info("file content type = {}", file.getContentType());
        String retunrMessage ="";
        String saveFileName="";
        String fileName = file.getOriginalFilename();
        String userId = jwtTokenUtil.getUsernameFromToken();
        if(!extCheck(fileName)){
            retunrMessage = "파일은 txt 확장자만 업로드 가능합니다.";
            return retunrMessage;
        }

        //파일 저장
        try{
            file.transferTo(new File(fileName));
        }catch (IOException e){
            retunrMessage =  "파일 업로드 실패";
            return retunrMessage;
        }
        //파일명 변경
        saveFileName = renameFile(file.getOriginalFilename());
        retunrMessage =  "파일 업로드 성공";


        //파일내용 DB에 추가
        FileVO fileVO = FileVO.builder()
                .fileName(fileName)
                .saveFileName(saveFileName)
                .fileUploadDate(dateService.getDate())
                .fileUploadTime(dateService.getTime())
                .userId(userId)
                .build();

        findTextMapper.insertFileInfo(fileVO);
        return retunrMessage;
    }

    /**
     * makeFileName , 파일명앞에 날짜를 붙여서 리턴
     * @param fileName
     * @return String
     * */
    public String makeFileName(String fileName){

        String newFileName = dateService.getDate()+"_"+dateService.getTime()+"_"+fileName;

        System.out.println("### newFileName : " + newFileName);
        return newFileName;
    }


    /**
     * renameFile , 파일명 변경
     * @param fileName
     * @return String
     * */
    public String renameFile(String fileName){
        //파일명 변경
        String saveFileName = makeFileName(fileName);
        try{
            File uploadFile =  new File(fileUploadPath , fileName);
            File saveFile = new File(fileUploadPath , saveFileName);
            uploadFile.renameTo(saveFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return saveFileName;
    }

    /**
     * extCheck , 파일의 확장자 체크
     * @param fileName
     * @return boolean
     * */
    public boolean extCheck(String fileName){

        String ext = fileName.substring(fileName.lastIndexOf("."));
        if("txt".equals(ext) || "TXT".equals(ext)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * getFileList , 파일목록 리턴
     * @param
     * @return HashMap<String,Object>
     * */
    public HashMap<String,Object> getFileList(){
        HashMap<String,Object> resultMap = new HashMap<>();
        String userId = jwtTokenUtil.getUsernameFromToken();
        jwtTokenUtil.getUsernameFromToken();
        resultMap.put("fileList" , findTextMapper.getFileList(userId));
        return resultMap;
    }

    /**
     * fileAccessCheck , 해당 파일이 로그인한 사용자의 것인지 검증
     * @param saveFileName
     * @return boolean
     * */
    public boolean fileAccessCheck(String saveFileName){
        //해당파일이 로그인한 사용자의것인지 검증
        FileVO fileVO = FileVO.builder()
                .saveFileName(saveFileName)
                .userId(jwtTokenUtil.getUsernameFromToken())
                .build();
        return findTextMapper.findTextFile(fileVO);
    }


    /**
     * getFindText , 선택한 텍스트 파일을 분석하여 정보를 리턴함
     * @param saveFileName
     * @return HashMap<String,Object>
     * */
    public HashMap<String,Object> getFindText(String saveFileName){
        HashMap<String,Object> resultMap = new HashMap<>();


        if(!fileAccessCheck(saveFileName)){
            return resultMap;
        }



        File file = new File(fileUploadPath , saveFileName);
        BufferedReader br = null;
        int count = 0;
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        String killUser=null;
        //채팅에 참여한 사용자 목록
        String memberName;
        ArrayList<String> memberList = new ArrayList<String>();
        //사용자명,채팅횟수 기록
        HashMap<String,Integer> talkCountMap = new HashMap<String, Integer>();
        HashMap<String,Integer> killCountMap = new HashMap<String, Integer>();
        HashMap<String,String> killUserListMap = new HashMap<String, String>();
        List<Map.Entry<String, Integer>> tokeList =null;
        List<Map.Entry<String, Integer>> killList =null;
        int totalLine=0;

        int memberCount=0;
        try {
            //사용자 목록 추출
            br = new BufferedReader(new FileReader(file));
            while((line=br.readLine()) != null) {
                int t=line.indexOf("]");
                if(t>0){
                    memberName = line.substring(0,t+1);

                    for(String name : memberList){
                        if(name.equals(memberName)){
                            memberCount++;
                        }
                    }
                    if(memberCount==0){
                        memberList.add(line.substring(0,t+1));
                    }
                    memberCount=0;
                }
            }

            //사용자세팅
            for(int i=0;i<memberList.size();i++){
                talkCountMap.put(memberList.get(i),0);
                killCountMap.put(memberList.get(i),0);
                killUserListMap.put(memberList.get(i),"");
            }
            br = new BufferedReader(new FileReader(file));


            //대화횟수저장
            while((line=br.readLine()) != null) {

                if(line.indexOf("나갔습니다")>=0){
                    killCountMap.put(killUser,killCountMap.get(killUser)+1);
                    int a=line.indexOf("님이");
                    if(killUserListMap.get(killUser).length()>0){
                        killUserListMap.put(killUser, killUserListMap.get(killUser) +" ,"  +  line.substring(0,a+1));
                    }else{
                        killUserListMap.put(killUser,killUserListMap.get(killUser) + line.substring(0,a+1));
                    }

                }

                for(int i=0;i<memberList.size();i++){
                    if(line.indexOf(memberList.get(i))>=0){
                        talkCountMap.put(memberList.get(i),talkCountMap.get(memberList.get(i))+1);
                        totalLine++;
                        killUser = memberList.get(i);
                    }
                }
            }
            //가나다순으로 memberList 정렬
            memberList.sort(Comparator.naturalOrder());

            //대화내용 순으로 talkCount 정렬
            tokeList = new LinkedList<>(talkCountMap.entrySet());
            tokeList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            //사용자를 쫓아낸 순으로 killCount 정렬
            killList = new LinkedList<>(killCountMap.entrySet());
            killList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        resultMap.put("tokeList" , tokeList);
        resultMap.put("killList" , killList);
        resultMap.put("memberList" , memberList);
        resultMap.put("memberCount" , memberList.size());
        resultMap.put("totalLine" , totalLine);


        return resultMap;
    }


    public double getPercent(double totalLine , double talkCount){
        return Math.round(talkCount/totalLine*1000000)/10000.0;
    }


}
