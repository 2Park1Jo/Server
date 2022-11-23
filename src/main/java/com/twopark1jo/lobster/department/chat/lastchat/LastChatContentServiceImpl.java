package com.twopark1jo.lobster.department.chat.lastchat;

import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberRepository;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LastChatContentServiceImpl implements LastChatContentService{

    private final LastChatContentRepository lastChatContentRepository;
    private final DepartmentMemberRepository departmentMemberRepository;

    //회원의 마지막 채팅 내역 저장
    @Override
    public boolean create(LastChatContent lastChatContent) {
        boolean isExistingMemberInDepartment = departmentMemberRepository
                .existsByDepartmentIdAndEmail(lastChatContent.getDepartmentId(), lastChatContent.getEmail());

        if(isExistingMemberInDepartment){   //부서에 속한 회원인지 확인 후 저장
            lastChatContentRepository.save(lastChatContent);
            return Constants.IS_DATA_SAVED_SUCCESSFULLY;
        }

        return !Constants.IS_DATA_SAVED_SUCCESSFULLY;
    }

    @Override
    public List<LastChatContent> getLastChatContentByWorkspace(String email, String workspace) {
        return lastChatContentRepository.findAllByEmailAndWorkspaceId(email, workspace);
    }

}
