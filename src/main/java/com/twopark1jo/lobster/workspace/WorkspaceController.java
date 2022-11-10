package com.twopark1jo.lobster.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceRepository workspaceRepository;

    @GetMapping("/workspace/{workspaceId}/detail")
    public ResponseEntity<Workspace> getWorkspaceDetail(@PathVariable String workspaceId){
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);

        if(isWorkspace){
            return new ResponseEntity(workspaceRepository.findById(workspaceId), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/workspaces")
    public ResponseEntity<List<Workspace>> getWorkspaces(){
        List<Workspace> workspaceList = workspaceRepository.findAll();

        return new ResponseEntity<>(workspaceList, HttpStatus.OK);
    }

}
