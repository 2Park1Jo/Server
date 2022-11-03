package com.twopark1jo.lobster.department.chatroom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workspace/{workspaceId}/chat")
public class ChatRoomController {

    @GetMapping("/department/{departmentId}")
    public Model chatRoomList(Model model, @PathVariable String workspaceId,
                                      @PathVariable String departmentId){
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("departmentId", departmentId);

        System.out.println("model = " + model);
        System.out.println("workspaceId = " + workspaceId);
        System.out.println("departmentId = " + departmentId);

        return model;
    }


}
