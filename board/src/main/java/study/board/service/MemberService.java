package study.board.service;

import study.board.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    public void register(Member member);
    public List<Member> getAllMembers();
    public Optional<Member> getMemberById(Long id);
    public Optional<Member> getMemberByUsername(String username);
    public Member updateMember(Long id, Member updateParams);
    public void deleteMember(Long id);
}
