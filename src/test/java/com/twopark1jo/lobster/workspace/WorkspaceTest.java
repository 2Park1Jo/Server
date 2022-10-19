package com.twopark1jo.lobster.workspace;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkspaceTest {

    @Autowired
    WorkspaceController workspaceController;

    @Test
    public void workspaceMemberTest(){
        workspaceController.getMember();
    }
}
