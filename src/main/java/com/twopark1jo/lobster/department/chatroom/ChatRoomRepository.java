package com.twopark1jo.lobster.department.chatroom;

import com.twopark1jo.lobster.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<Member, String> {

}
