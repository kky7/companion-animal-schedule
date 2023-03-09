package org.backend.security.user;

import org.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.backend.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member == null) return null;
        else{
            UserDetailImpl userDetails = new UserDetailImpl();
            userDetails.setMember(member);
            return userDetails;
        }
    }
}
