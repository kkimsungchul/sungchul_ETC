package com.sungchul.etc.findtext.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("FileUpload VO")
public class FileVO {

    @ApiModelProperty(name = "seq", value = "순서", notes = "", example = "1")
    @JsonProperty("seq")
    private int seq;

    @ApiModelProperty(name = "file", value = "파일", notes = "")
    @JsonProperty("file")
    private MultipartFile file;

    @ApiModelProperty(name = "file_name", value = "기존 파일명", notes = "", example = "파일.txt")
    @JsonProperty("file_name")
    private String fileName;

    @ApiModelProperty(name = "save_file_name", value = "저장된 파일명", notes = "", example = "20220421_152202_파일.txt")
    @JsonProperty("save_file_name")
    private String saveFileName;

    @ApiModelProperty(name = "user_id", value = "파일 업로드 유저 ID", notes = "", example = "kimsc")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty(name = "file_upload_date", value = "로그날짜", notes = "", example = "20220421")
    @JsonProperty("file_upload_date")
    private String fileUploadDate;

    @ApiModelProperty(name = "file_upload_time", value = "로그 시간", notes = "", example = "152202")
    @JsonProperty("file_upload_time")
    private String fileUploadTime;


}
