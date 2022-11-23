package com.twopark1jo.lobster.department.chat.lastchat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LastChatContentController {

    private final LastChatContentServiceImpl lastChatContentService;

    //회원의 마지막 채팅 내역 저장
    @PostMapping("/workspace/last-chat/create")
    public ResponseEntity create(@RequestBody LastChatContent lastChatContent){

        if(lastChatContentService.create(lastChatContent)){
            return new ResponseEntity(HttpStatus.CREATED);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{email}/workspace/{workspaceId}/last-chat")
    public ResponseEntity<List<LastChatContent>> getLastChatContentByWorkspace(
            @PathVariable String email, @PathVariable String workspaceId){
        List<LastChatContent> lastChatContentList;

        lastChatContentList = lastChatContentService.getLastChatContentByWorkspace(email, workspaceId);

        return new ResponseEntity<>(lastChatContentList, HttpStatus.OK);
    }
}
