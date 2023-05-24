package com.labshigh.aicfo.internal.api.abusing.service;

import com.labshigh.aicfo.core.models.ResponseListModel;
import com.labshigh.aicfo.internal.api.abusing.dao.AbusingDao;
import com.labshigh.aicfo.internal.api.abusing.mapper.AbusingMapper;
import com.labshigh.aicfo.internal.api.abusing.model.request.AbusingRestRequestModel;
import com.labshigh.aicfo.internal.api.abusing.model.request.AbusingRequestModel;
import com.labshigh.aicfo.internal.api.abusing.model.response.AbusingResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AbusingService {
    private final AbusingMapper abusingMapper;

    public AbusingService(AbusingMapper abusingMapper) {
        this.abusingMapper = abusingMapper;
    }

    public ResponseListModel list(AbusingRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();
        try {
            int totalCount = abusingMapper.count(requestModel);

            result.setCurrentPage(requestModel.getPage());
            result.setTotalCount(totalCount);
            result.setPageSize(requestModel.getSize());

            if (totalCount < 1) {
//                result.setList(Collections.emptyList());
                return result;
            }

            List<AbusingResponseModel> abusingDaoList = abusingMapper.list(requestModel).stream()
                    .map(this::convertAbusingResponseModel).collect(Collectors.toList());
            result.setList(abusingDaoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

//    public ResponseListModel abusingMemoList(AbusingRequestModel requestModel) {
//        ResponseListModel result = new ResponseListModel();
//        try {
//            List<AbusingResponseModel> abusingDaoList = abusingMapper.abusingMemoList(requestModel).stream()
//                    .map(this::convertAbusingResponseModel).collect(Collectors.toList());
//            result.setList(abusingDaoList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    public ResponseListModel abusingMemoList(long uid) {
        ResponseListModel result = new ResponseListModel();
        try {
            List<AbusingResponseModel> abusingDaoList = abusingMapper.abusingMemoList(uid).stream()
                    .map(this::convertAbusingResponseModel).collect(Collectors.toList());
            result.setList(abusingDaoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private AbusingResponseModel convertAbusingResponseModel(AbusingDao dao) {
        return AbusingResponseModel.builder()
                .uid(dao.getUid())
                .updatedAt(dao.getUpdatedAt())
                .createdAt(dao.getCreatedAt())
                .email(dao.getEmail())
                .nickname(dao.getNickname())
                .abusingCnt(dao.getAbusingCnt())
                .walletAddress(dao.getWalletAddress())
                .usedFlag(dao.isUsedFlag())
                .memo(dao.getMemo())
                .memberUid(dao.getMemberUid())
                .adminName(dao.getAdminName())
                .build();
    }

    public ResponseListModel detailMember(AbusingRequestModel requestModel) {
        ResponseListModel result = new ResponseListModel();
        try {
            int totalCount = abusingMapper.detailMemberCount(requestModel);

            result.setCurrentPage(requestModel.getPage());
            result.setTotalCount(totalCount);
            result.setPageSize(requestModel.getSize());

            if (totalCount < 1) {
                return result;
            }

            List<AbusingResponseModel> abusingDaoList = abusingMapper.detailMember(requestModel).stream()
                    .map(this::convertAbusingResponseModel).collect(Collectors.toList());
            result.setList(abusingDaoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    @Transactional
    public void rest(AbusingRestRequestModel abusingRestRequestModel) {
        try {
            // 어뷰저 등록
            abusingMapper.insert(abusingRestRequestModel);
            // 멤버 상태 변경
            abusingMapper.updateMember(abusingRestRequestModel );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Transactional
//    public ResponseModel insert(NoticeInsertRequestModel requestModel) {
//        abusingMapper.insert(requestModel);
//        ResponseModel result = new ResponseModel();
//        result.setData(requestModel.getUid());
//        return result;
//    }
//
//    @Transactional
//    public ResponseModel update(NoticeUpdateRequestModel requestModel) {
//        int num = abusingMapper.update(requestModel);
//        ResponseModel result = new ResponseModel();
//        result.setData(requestModel.getUid());
//        return result;
//    }
}
