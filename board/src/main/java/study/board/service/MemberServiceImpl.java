package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(Member member) {
        member.setUsername(member.getUsername());
        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> getMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public Member updateMember(Long id, Member updateParams) {
        Member findMember = memberRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
        findMember.setUsername(updateParams.getUsername());

        return memberRepository.save(findMember);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
