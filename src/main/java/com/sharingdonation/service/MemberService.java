package com.sharingdonation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.entity.Member;
import com.sharingdonation.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional  //로직을 처리하다가 에러가 발생하면 변경된 데이터를 로직을 수행하기 전으로 콜백시켜줌
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
    	Member member = memberRepository.findByEmail(email);
    	
    	if(member == null){
    		throw new UsernameNotFoundException(email);
    	}
    	
    	return User.builder()
    			.username(member.getEmail())
    			.password(member.getPassword())
    			.roles(member.getRole().toString())
    			.build();
    }
    
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }
    
    public Member saveCorp(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    
    private String changePassword(String email, String name, String cellphone) {
    	Member findMember = memberRepository.findByEmailAndNameAndCellphone(email, name, cellphone);
    	
    	return null;
    }
    
     
    
}
