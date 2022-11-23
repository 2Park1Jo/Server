package com.twopark1jo.lobster.department.chat.lastchat;

import java.util.List;

public interface LastChatContentService {

    boolean create(LastChatContent lastChatContent);

    List<LastChatContent> getLastChatContentByWorkspace(String email, String workspace);

}