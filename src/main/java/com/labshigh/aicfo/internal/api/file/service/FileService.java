package com.labshigh.aicfo.internal.api.file.service;

import com.labshigh.aicfo.internal.api.common.utils.FileUploadUtils;
import com.labshigh.aicfo.internal.api.file.model.request.FileUploadRequestModel;
import com.labshigh.aicfo.internal.api.file.model.response.FileUploadResponseModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
public class FileService {

  private final FileUploadUtils fileUploadUtils;

  public FileService(FileUploadUtils fileUploadUtils) {
    this.fileUploadUtils = fileUploadUtils;
  }

  public List<FileUploadResponseModel> upload(FileUploadRequestModel requestModel)
      throws IOException {

    List<FileUploadResponseModel> result = new ArrayList<>();

    for (MultipartFile file : requestModel.getFile()) {
      FileUploadResponseModel responseModel = FileUploadResponseModel.builder()
          .fileUri(fileUploadUtils.uploadPhoto(file,
              requestModel.getFileType(), requestModel.getCommonCodeUid()))
          .fileName(file.getOriginalFilename())
          .build();

      result.add(responseModel);

    }

    return result;

//    return FileUploadResponseModel.builder()
//        .fileUri(fileUploadUtils.uploadPhoto(requestModel.getFile(),
//            requestModel.getFileType(), requestModel.getCommonCodeUid()))
//        .fileName(requestModel.getFile().getName())
//        .build();
  }
}
