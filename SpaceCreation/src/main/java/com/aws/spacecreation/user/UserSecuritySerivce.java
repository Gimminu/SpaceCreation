package com.aws.spacecreation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecuritySerivce implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteuser = this.userRepository.findByUsername(username);
        if(_siteuser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        SiteUser siteUser = _siteuser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }

//    public SiteUser getAuthen(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String username = userDetails.getUsername();
//        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
//        return siteUser.get();
//    }



    public SiteUser getAuthen() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        return siteUser.orElse(null);   //없으면 null로 반환해줘야함 그래야 로그인안해도 게시물 볼 수 있음
    }

}
