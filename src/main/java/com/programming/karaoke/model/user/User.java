package com.programming.karaoke.model.user;

import com.programming.karaoke.model.token.Token;
import com.programming.karaoke.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Document(value = "User")
//@NoArgsConstructor sẽ tự động tạo ra một
// constructor không tham số cho lớp đối tượng. Ví dụ:
@NoArgsConstructor
//@AllArgsConstructor sẽ tự động tạo ra một constructor
//  chứa tất cả các thuộc tính của lớp đối tượng.
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;
    //    private String firstName;
//    private String lastName;
    private String fullName;
    private String emailAddress;
    private String telephoneNumber;
    private String passWord;
    private List<Token> tokens;
    @Field(targetType = FieldType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(role.name()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }


    //todo    Indicates whether the user's account has expired.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //   todo: Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //   todo: Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//    private Set<String> subscribedToUser;
//    private Set<String> subscribers;
//    private List<String> videoHistory;
//    private Set<String> likedVideos;
//    private Set<String> dislikeEdVideo;
}
